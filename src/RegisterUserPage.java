import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterUserPage implements ActionListener {

    private JTextField newUserText;
    private JPasswordField newPasswordText;

    private JLabel successLabel;

    public RegisterUserPage(){

        //creation of objects
        JFrame registerFrame = new JFrame();
        JPanel registerPanel = new JPanel();


        JButton confirmUserButton = new JButton("Confirm User Details");
        confirmUserButton.setBounds(150,80,200,35);
        confirmUserButton.setActionCommand("details");
        confirmUserButton.addActionListener(this);

        JButton ReturnToLoginButton = new JButton("Return to Login");
        ReturnToLoginButton.setBounds(150,115,200,35);
        ReturnToLoginButton.setActionCommand("login");
        ReturnToLoginButton.addActionListener(this);

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
        registerPanel.add(ReturnToLoginButton);
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

            if (db.registerUser(username, password)) {
                System.out.println("details are now stored in the database, you can now log in");
                successLabel.setText("details are now stored in the database, you can now log in");
            } else {
                System.out.println("Error registering user or user already exists");
                successLabel.setText("Error registering user or user already exists");
            }


            System.out.println("details are now stored in the database, you can now log in");
            successLabel.setText("details are now stored in the database, you can now log in");
        }
        else if("login".equals(command)){
            SwingUtilities.invokeLater(() -> new LoginPage());
        }
    }

}

