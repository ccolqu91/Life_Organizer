import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DailyView implements ActionListener, TaskObserver {

    JFrame dailyFrame = new JFrame();
    JPanel dailyPanel = new JPanel();
    private DefaultListModel<String> dailyListModel;
    private JList<String> dailyList;
    private JLabel monthLabel;
    private LocalDate currentDay;
    private JPanel dailyListPanel;

    public DailyView(LocalDate selectedDate) {

        currentDay = selectedDate;

        //Daily View list
        //dailyListModel = new DefaultListModel<>();
        //dailyList = new JList<>(dailyListModel);
        //JScrollPane dailyListScrollPane = new JScrollPane(dailyList);
        //dailyListScrollPane.setBounds(200,100,400,300);

        dailyListPanel = new JPanel();
        dailyListPanel.setLayout(new BoxLayout(dailyListPanel, BoxLayout.Y_AXIS));
        JScrollPane dailyListScrollPane = new JScrollPane(dailyListPanel);
        dailyListScrollPane.setBounds(200, 100, 400, 300);

        monthLabel = new JLabel(formatDateWithSuffix(currentDay));
        monthLabel.setBounds(450, 50, 100, 25);

        JButton prevMonthButton = new JButton("<");
        prevMonthButton.setBounds(300, 50, 50, 25);
        prevMonthButton.setActionCommand("prev");
        prevMonthButton.addActionListener(this);

        JButton nextMonthButton = new JButton((">"));
        nextMonthButton.setBounds(525, 50, 50, 25);
        nextMonthButton.setActionCommand("next");
        nextMonthButton.addActionListener(this);

        JButton createTaskButton = new JButton(("New Task"));
        createTaskButton.setBounds(500, 500, 100, 25);
        createTaskButton.setActionCommand("create");
        createTaskButton.addActionListener(this);

        //panel settings
        dailyPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        dailyPanel.setLayout(null);
        dailyPanel.add(dailyListScrollPane);
        dailyPanel.add(monthLabel);
        dailyPanel.add(prevMonthButton);
        dailyPanel.add(nextMonthButton);
        dailyPanel.add(createTaskButton);

        //Frame settings
        dailyFrame.add(dailyPanel);
        dailyFrame.setSize(800, 600);
        dailyFrame.setTitle("Life Organizer - Daily View");
        dailyFrame.setVisible(true);
        TaskDataModel.getInstance().addObserver(this);
    }

    public String formatDateWithSuffix(LocalDate date) {

        String suffix = getSuffix(date.getDayOfMonth());

        return date.getDayOfMonth() + suffix + " " +
                date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " +
                date.getYear();
    }

    public String getSuffix(int date) {

        if (date == 1 || date == 31 || date == 21) {
            return "st";
        } else if (date == 2 || date == 22) {
            return "nd";
        } else if (date == 3 || date == 23) {
            return "rd";
        } else {
            return "th";
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if ("create".equals(command)) {
            SwingUtilities.invokeLater(() -> new CreateTask(currentDay));
        } else if ("prev".equals(command)) {
            currentDay = currentDay.minusDays(1);
        } else if ("next".equals(command)) {
            currentDay = currentDay.plusDays(1);
        }
        monthLabel.setText(formatDateWithSuffix(currentDay));

    }

    @Override
    public void taskAdded(Task task) {
        JButton taskButton = new JButton("<html>Task: " + task.name + "<br>Date: " + task.date + "<br>Start Time: " + task.startTime + "<br>End Time: " + task.endTime + "</html>");

        JLabel dummyLabel = new JLabel("<html>Task: " + task.name + "<br>Date: " + task.date + "<br>Start Time: " + task.startTime + "<br>End Time: " + task.endTime + "</html>");
        Dimension preferredSize = dummyLabel.getPreferredSize();
        taskButton.setPreferredSize(preferredSize);
        taskButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));
        taskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Daily list updated");


            }
        });
        dailyListPanel.add(taskButton);
        dailyListPanel.revalidate();
    }
}
