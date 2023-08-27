import javax.swing.*;

public class TaskView {

    //creation of objects
    JFrame taskFrame = new JFrame();
    JPanel taskPanel = new JPanel();

    public TaskView(){

        //panel settings
        taskPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        taskPanel.setLayout(null);

        //Frame settings
        taskFrame.setTitle("Life Organizer - Daily View");
    }
}

