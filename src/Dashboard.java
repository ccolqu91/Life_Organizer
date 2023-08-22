import javax.swing.*;

public class Dashboard {

    public Dashboard(){

        JFrame dashboardFrame = new JFrame();
        JPanel dashboardPanel = new JPanel();

        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        dashboardPanel.setLayout(null);

        dashboardFrame.add(dashboardPanel);
        dashboardFrame.setSize(500,300);
        dashboardFrame.setTitle("Life Organizer - Dashboard");
        dashboardFrame.setVisible(true);
    }
}
