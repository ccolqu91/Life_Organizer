import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage implements ActionListener {

    private int count = 0;
    private JFrame frame;
    private JPanel panel;
    private JLabel label;

    public LoginPage(){
        //creation of objects
        frame = new JFrame();
        panel = new JPanel();
        label = new JLabel("Number of clicks:"+ count);
        JButton button = new JButton("click");
        button.addActionListener(this);

        //panel settings
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panel.setLayout(new GridLayout(0,1));
        panel.add(button);
        panel.add(label);

        //frame settings
        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Life Organizer - Login");
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        label.setText(("Number of clicks:"+ count));

    }
}
