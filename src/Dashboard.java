import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class Dashboard implements ActionListener, TaskObserver {

    private JFrame dashboardFrame;
    private JPanel dashboardPanel;
    private LocalDate currentMonth;
    private JLabel monthLabel;
    private JPanel monthViewPanel;
    //private LocalDate dateIterator;
    private JButton logoutButton;
    private JButton prevMonthButton;
    private JButton nextMonthButton;
    private JPanel todoPanel;
    public Dashboard(){
        //Create objects
        dashboardFrame = new JFrame();
        dashboardPanel = new JPanel();

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(420,10,100,25);
        logoutButton.setActionCommand("logout");
        logoutButton.addActionListener(this);

        prevMonthButton = new JButton("<");
        prevMonthButton.setBounds(525,100,50,25);
        prevMonthButton.setActionCommand("prev");
        prevMonthButton.addActionListener(this);

        nextMonthButton = new JButton((">"));
        nextMonthButton.setBounds(700,100,50,25);
        nextMonthButton.setActionCommand("next");
        nextMonthButton.addActionListener(this);

        currentMonth = LocalDate.now().withDayOfMonth(1);


        todoPanel = new JPanel();
        todoPanel.setLayout(new BoxLayout(todoPanel, BoxLayout.Y_AXIS));
        JScrollPane todoScrollPane = new JScrollPane(todoPanel);
        todoScrollPane.setBounds(1125,175,400,480);

        monthViewPanel = new JPanel(new GridLayout(7,7));
        monthViewPanel.setBounds(150,150,950,500);
        fillMonthView(monthViewPanel);

        monthLabel = new JLabel(currentMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + currentMonth.getYear());
        monthLabel.setBounds(600,100,100,25);

        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        dashboardPanel.add(monthLabel);
        dashboardPanel.add(logoutButton);
        dashboardPanel.add(prevMonthButton);
        dashboardPanel.add(nextMonthButton);
        dashboardPanel.add(todoScrollPane);
        dashboardPanel.add(monthViewPanel);
        dashboardPanel.setLayout(null);

        dashboardFrame.add(dashboardPanel);
        dashboardFrame.setSize(3000,3000);
        dashboardFrame.setTitle("Life Organizer - Dashboard");
        dashboardFrame.setVisible(true);
        TaskDataModel.getInstance().addObserver(this);
    }

    private void fillMonthView(JPanel monthViewPanel){
        monthViewPanel.removeAll();

        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String dayName : dayNames) {
            JLabel dayLabel = new JLabel(dayName, SwingConstants.CENTER);
            monthViewPanel.add(dayLabel);
        }

        LocalDate dateIterator = currentMonth;

        int firstDayOfWeek = dateIterator.getDayOfWeek().getValue();

        if(firstDayOfWeek == 7){
            firstDayOfWeek = 0;
        }
        else{
            firstDayOfWeek++;
        }
        for(int i = 1; i < firstDayOfWeek; i++){
            monthViewPanel.add(new JLabel(""));
        }

        int daysInMonth = currentMonth.lengthOfMonth();

        for(int day = 1; day <= daysInMonth; day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setFont(new Font("Ariel", Font.PLAIN, 8));
            dayButton.setMargin(new Insets(1, 1, 1, 1));
            dayButton.setActionCommand("day:" + day);
            dayButton.addActionListener(this);
            monthViewPanel.add(dayButton);
            dateIterator = dateIterator.plusDays(1);
        }

            int occupiedCells = firstDayOfWeek + daysInMonth - 1;
            int remainingDays = 42 - occupiedCells;

            for(int i = 0; i < remainingDays; i++){
                monthViewPanel.add(new JLabel(""));
            }

            monthViewPanel.revalidate();
            monthViewPanel.repaint();
        }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if("logout".equals(command)){
            SwingUtilities.invokeLater(() -> new LoginPage());
        }
        else if("prev".equals(command)){
            currentMonth = currentMonth.minusMonths(1);
            monthLabel.setText(currentMonth.getMonth().getDisplayName((TextStyle.FULL), Locale.ENGLISH) + " " + currentMonth.getYear());
            fillMonthView(monthViewPanel);
        }
        else if("next".equals(command)){
            currentMonth = currentMonth.plusMonths(1);
            monthLabel.setText(currentMonth.getMonth().getDisplayName((TextStyle.FULL), Locale.ENGLISH) + " " + currentMonth.getYear());
            fillMonthView(monthViewPanel);
        }
        else if(command.startsWith("day")){
            String daystr = command.split(":")[1];
            int day = Integer.parseInt(daystr);
            LocalDate selectedDate = currentMonth.withDayOfMonth(day);
            SwingUtilities.invokeLater(() -> new DailyView(selectedDate));

        }

    }

    @Override
    public void taskAdded(Task task) {
        // Clear the existing buttons from the panel
        todoPanel.removeAll();

        // Fetch the sorted list of tasks from the data model
        List<Task> sortedTasks = TaskDataModel.getInstance().getSortedTasks();  // assuming getSortedTasks sorts and returns tasks

        // Add buttons for each sorted task
        for (Task t : sortedTasks) {
            JButton taskButton = new JButton("<html>Task: " + t.name + "<br>Date: " + t.date + "<br>Start Time: " + t.startTime + "<br>End Time: " + t.endTime + "</html>");

            JLabel dummyLabel = new JLabel("<html>Task: " + t.name + "<br>Date: " + t.date + "<br>Start Time: " + t.startTime + "<br>End Time: " + t.endTime + "</html>");
            Dimension preferredSize = dummyLabel.getPreferredSize();
            taskButton.setPreferredSize(preferredSize);
            taskButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));
            taskButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Task button clicked");
                }
            });
            todoPanel.add(taskButton);
        }

        // Revalidate and repaint the panel to reflect the changes
        todoPanel.revalidate();
        todoPanel.repaint();
    }
}
