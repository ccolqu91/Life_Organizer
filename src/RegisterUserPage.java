import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterUserPage implements ActionListener {

    private JFrame registerFrame;
    private JPanel registerPanel;
    private JTextField newUserText;
    private JPasswordField newPasswordText;
    private JLabel successLabel;
    private JButton confirmUserButton;
    private JButton returnToLoginButton;

    public RegisterUserPage(){


        registerFrame = new JFrame();
        registerPanel = new JPanel();


        confirmUserButton = new JButton("Confirm User Details");
        confirmUserButton.setBounds(150,80,200,35);
        confirmUserButton.setActionCommand("details");
        confirmUserButton.addActionListener(this);

        returnToLoginButton = new JButton("Return to Login");
        returnToLoginButton.setBounds(150,115,200,35);
        returnToLoginButton.setActionCommand("login");
        returnToLoginButton.addActionListener(this);

        JLabel userLabel = new JLabel("New Username");
        userLabel.setBounds(50,20,100,25);

        JLabel passwordLabel = new JLabel("New Password");
        passwordLabel.setBounds(50,50,100,25);

        successLabel = new JLabel("");
        successLabel.setBounds(150,150,500,25);


        newUserText = new JTextField(20);
        newUserText.setBounds(200,20,165,25);

        newPasswordText = new JPasswordField();
        newPasswordText.setBounds(200,50,165,25);

        //panel settings
        registerPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        registerPanel.setLayout(null);
        registerPanel.add(userLabel);
        registerPanel.add(passwordLabel);
        registerPanel.add(newUserText);
        registerPanel.add(newPasswordText);
        registerPanel.add(confirmUserButton);
        registerPanel.add(returnToLoginButton);
        registerPanel.add(successLabel);

        //frame settings
        registerFrame.add(registerPanel);
        registerFrame.setSize(500,300);
        registerFrame.add(registerPanel,BorderLayout.CENTER);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setTitle("Life Organizer - Create New Account");
        registerFrame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        Database db = new Database();

        if("details".equals(command)){

            String username = newUserText.getText();
            String password = new String(newPasswordText.getPassword());
            if(db.isUsernameTaken(username) == false){
                db.registerUser(username, password);
                System.out.println("Your details are now in the system");
                successLabel.setText("Your details are now in the system");
            }
            else {
                System.out.println("Username is already taken, please try another");
                successLabel.setText("Username is already taken, please try another");
            }
        }
        else if("login".equals(command)){
            SwingUtilities.invokeLater(() -> new LoginPage());
        }
    }

}

