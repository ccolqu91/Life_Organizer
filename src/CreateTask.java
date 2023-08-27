import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;

class CreateTask implements ActionListener {

    //creation of objects
    private JFrame createFrame;
    private JPanel createPanel;
    private JTextField taskNameField;
    private JTextArea taskDetailsArea;
    private SpinnerModel startHourModel;
    private SpinnerModel startMinuteModel;
    private SpinnerModel endHourModel;
    private SpinnerModel endMinuteModel;
    private JButton setTask;
    private LocalDate date;

    public CreateTask(LocalDate date) {

        this.date = date;
        createFrame = new JFrame();
        createPanel = new JPanel();

        createPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        createPanel.setLayout(null);

        JLabel taskName = new JLabel("Task Name:");
        taskName.setBounds(100,100,100,30);

        JLabel startLabel = new JLabel("Start Time:");
        startLabel.setBounds(100, 200, 100, 30);

        JLabel endLabel = new JLabel("End Time:");
        endLabel.setBounds(100, 300, 100, 30);

        JLabel taskDetails = new JLabel("Task Details:");
        taskDetails.setBounds(100,400,100,30);

        taskNameField = new JTextField();
        taskNameField.setBounds(200,100,400,30);

        taskDetailsArea = new JTextArea();
        taskDetailsArea.setLineWrap(true);
        taskDetailsArea.setWrapStyleWord(true);
        JScrollPane taskDetailsScrollPane = new JScrollPane(taskDetailsArea);
        taskDetailsScrollPane.setBounds(200, 400, 400, 100);

        setTask = new JButton("Set Task");
        setTask.setBounds(300,515,200,35);
        setTask.setActionCommand("set");
        setTask.addActionListener(this);

        startHourModel = new SpinnerNumberModel(0, -1, 24, 1);
        startMinuteModel = new SpinnerNumberModel(0, -1, 60, 1);
        endHourModel = new SpinnerNumberModel(0, -1, 24, 1);
        endMinuteModel = new SpinnerNumberModel(0, -1, 60, 1);

        JSpinner startHourSpinner = new JSpinner(startHourModel);
        JSpinner.NumberEditor startHourEditor = new JSpinner.NumberEditor(startHourSpinner, "00");
        startHourSpinner.setEditor(startHourEditor);

        JSpinner startMinuteSpinner = new JSpinner(startMinuteModel);
        JSpinner.NumberEditor startMinuteEditor = new JSpinner.NumberEditor(startMinuteSpinner, "00");
        startMinuteSpinner.setEditor(startMinuteEditor);

        JSpinner endHourSpinner = new JSpinner(endHourModel);
        JSpinner.NumberEditor endHourEditor = new JSpinner.NumberEditor(endHourSpinner,"00");
        endHourSpinner.setEditor(endHourEditor);

        JSpinner endMinuteSpinner = new JSpinner(endMinuteModel);
        JSpinner.NumberEditor endMinuteEditor = new JSpinner.NumberEditor(endMinuteSpinner,"00");
        endMinuteSpinner.setEditor(endMinuteEditor);

        DecimalFormat decimalFormat = new DecimalFormat("00");
        JSpinner[] allSpinners = {startHourSpinner, startMinuteSpinner, endHourSpinner, endMinuteSpinner};

        for (JSpinner spinner : allSpinners) {
            spinner.setValue(0);
            JFormattedTextField txt = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
            ((NumberFormatter) txt.getFormatter()).setFormat(decimalFormat);
            spinner.setValue(spinner.getValue());
        }

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
        createPanel.add(setTask);

        createFrame.add(createPanel);
        createFrame.setSize(800, 600);
        createFrame.setTitle("Life Organizer - Create View");
        createFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("set".equals(e.getActionCommand())){

            int startHourValue = (Integer) startHourModel.getValue();
            int startMinuteValue = (Integer) startMinuteModel.getValue();
            int endHourValue = (Integer) endHourModel.getValue();
            int endMinuteValue = (Integer) endMinuteModel.getValue();


            String formattedStartHour = String.format("%02d",startHourValue);
            String formattedStartMinute = String.format("%02d", startMinuteValue);
            String formattedEndHour = String.format("%02d", endHourValue);
            String formattedEndMinute = String.format("%02d", endMinuteValue);

            String taskName = taskNameField.getText();
            String startTime = formattedStartHour + ":" + formattedStartMinute;
            String endTime = formattedEndHour + ":" + formattedEndMinute;
            String taskDate = date.toString();

            Task newTask = new Task(taskName, startTime, endTime, taskDate);
            TaskDataModel.getInstance().addTask(newTask);
        }

    }
}
