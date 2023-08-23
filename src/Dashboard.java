import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class Dashboard implements ActionListener {

    private LocalDate currentMonth;
    private DefaultListModel<String> todoListModel;
    private JList<String> todoList;
    private JLabel monthLabel;
    private JPanel monthViewPanel;
    private LocalDate dateIterator;
    public Dashboard(){
        //Create objects
        JFrame dashboardFrame = new JFrame();
        JPanel dashboardPanel = new JPanel();

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(420,10,100,25);
        logoutButton.setActionCommand("logout");
        logoutButton.addActionListener(this);

        JButton prevMonthButton = new JButton("<");
        prevMonthButton.setBounds(525,100,50,25);
        prevMonthButton.setActionCommand("prev");
        prevMonthButton.addActionListener(this);

        JButton nextMonthButton = new JButton((">"));
        nextMonthButton.setBounds(700,100,50,25);
        nextMonthButton.setActionCommand("next");
        nextMonthButton.addActionListener(this);

        currentMonth = LocalDate.now().withDayOfMonth(1);

        //to do list
        todoListModel = new DefaultListModel<>();
        todoList = new JList<>(todoListModel);
        JScrollPane todoScrollPane = new JScrollPane(todoList);
        todoScrollPane.setBounds(1400,100,120,480);

        //Monthly view
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
        //System.out.println("Days in Month: "+daysInMonth);
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
            todoListModel.addElement("Task for "+ selectedDate);

        }

    }
}
