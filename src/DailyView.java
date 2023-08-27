import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DailyView implements ActionListener {

    //creation of objects
    JFrame dailyFrame = new JFrame();
    JPanel dailyPanel = new JPanel();
    private DefaultListModel<String> dailyListModel;
    private JList<String> dailyList;
    private JLabel monthLabel;
    private LocalDate currentDay;

    public DailyView(LocalDate selectedDate) {

        currentDay = selectedDate;

        //Daily View list
        dailyListModel = new DefaultListModel<>();
        dailyList = new JList<>(dailyListModel);
        JScrollPane dailyListScrollPane = new JScrollPane(dailyList);
        dailyListScrollPane.setBounds(200,100,400,300);

        monthLabel = new JLabel(formatDateWithSuffix(currentDay));
        monthLabel.setBounds(450,50,100,25);

        JButton prevMonthButton = new JButton("<");
        prevMonthButton.setBounds(300,50,50,25);
        prevMonthButton.setActionCommand("prev");
        prevMonthButton.addActionListener(this);

        JButton nextMonthButton = new JButton((">"));
        nextMonthButton.setBounds(525,50,50,25);
        nextMonthButton.setActionCommand("next");
        nextMonthButton.addActionListener(this);

        JButton createTaskButton = new JButton(("New Task"));
        createTaskButton.setBounds(500,500,100,25);
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
        dailyFrame.setSize(800,600);
        dailyFrame.setTitle("Life Organizer - Daily View");
        dailyFrame.setVisible(true);
    }

    public String formatDateWithSuffix(LocalDate date){

        String suffix = getSuffix(date.getDayOfMonth());

        return date.getDayOfMonth() + suffix + " " +
                date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " +
                date.getYear();
    }

    public String getSuffix(int date){

        if(date == 1 || date == 31 || date == 21){
            return "st";
        }
        else if(date == 2 || date == 22){
            return "nd";
        }
        else if(date == 3 || date == 23){
            return "rd";
        }
        else{
            return "th";
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if("create".equals(command)){
            SwingUtilities.invokeLater(() -> new CreateTask());
        }
        else if("prev".equals(command)){
            currentDay = currentDay.minusDays(1);
        }
        else if("next".equals(command)){
            currentDay = currentDay.plusDays(1);
        }
        monthLabel.setText(formatDateWithSuffix(currentDay));

    }
}
