import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class CreateTask {

    //creation of objects
    JFrame createFrame = new JFrame();
    JPanel createPanel = new JPanel();
    private JTextField taskNameField;
    private JTextArea taskDetailsArea;

    public CreateTask() {
        //panel settings
        createPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        createPanel.setLayout(null);

        // Create Labels
        JLabel taskName = new JLabel("Task Name:");
        JLabel startLabel = new JLabel("Start Time:");
        JLabel endLabel = new JLabel("End Time:");
        JLabel taskDetails = new JLabel("Task Details:");
        taskName.setBounds(100,100,100,30);
        startLabel.setBounds(100, 200, 100, 30);
        endLabel.setBounds(100, 300, 100, 30);
        taskDetails.setBounds(100,400,100,30);

        taskNameField = new JTextField();
        taskNameField.setBounds(200,100,400,30);

        taskDetailsArea = new JTextArea();
        taskDetailsArea.setLineWrap(true); // wraps the text to the next line
        taskDetailsArea.setWrapStyleWord(true); // wraps by word
        JScrollPane taskDetailsScrollPane = new JScrollPane(taskDetailsArea);
        taskDetailsScrollPane.setBounds(200, 400, 400, 100);



        SpinnerModel startHourModel = new SpinnerNumberModel(0, -1, 24, 1);
        SpinnerModel startMinuteModel = new SpinnerNumberModel(0, -1, 60, 1);
        SpinnerModel endHourModel = new SpinnerNumberModel(0, -1, 24, 1);
        SpinnerModel endMinuteModel = new SpinnerNumberModel(0, -1, 60, 1);

        JSpinner startHourSpinner = new JSpinner(startHourModel);
        JSpinner startMinuteSpinner = new JSpinner(startMinuteModel);
        JSpinner endHourSpinner = new JSpinner(endHourModel);
        JSpinner endMinuteSpinner = new JSpinner(endMinuteModel);

        JSpinner[] hourSpinners = {startHourSpinner, endHourSpinner};
        for (JSpinner spinner : hourSpinners) {
            spinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSpinner source = (JSpinner) e.getSource();
                    if ((Integer) source.getValue() == 24) {
                        source.setValue(0);
                    }
                    else if ((Integer) source.getValue() == -1) {
                        source.setValue(23);
                    }
                }
            });
        }
        JSpinner[] minuteSpinners = {startMinuteSpinner, endMinuteSpinner};
        for (JSpinner spinner : minuteSpinners){
            spinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSpinner source = (JSpinner) e.getSource();
                    if ((Integer) source.getValue() == 60) {
                        source.setValue(0);
                    }
                    else if ((Integer) source.getValue() == -1){
                        source.setValue(59);
                    }
                }
            });
        }



        startHourSpinner.setBounds(200, 200, 50, 30);
        startMinuteSpinner.setBounds(260, 200, 50, 30);
        endHourSpinner.setBounds(200, 300, 50, 30);
        endMinuteSpinner.setBounds(260, 300, 50, 30);

        // Add components to panel
        createPanel.add(startLabel);
        createPanel.add(endLabel);
        createPanel.add(taskName);
        createPanel.add(taskDetails);
        createPanel.add(startHourSpinner);
        createPanel.add(startMinuteSpinner);
        createPanel.add(endHourSpinner);
        createPanel.add(endMinuteSpinner);
        createPanel.add(taskNameField);
        createPanel.add(taskDetailsScrollPane);

        // Frame settings
        createFrame.add(createPanel);
        createFrame.setSize(800, 600);
        createFrame.setTitle("Life Organizer - Create View");
        createFrame.setVisible(true);
    }
}
