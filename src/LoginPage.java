import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage implements ActionListener {

    private JTextField userText;
    private JTextField passwordText;

    private JLabel successLabel;

    public LoginPage(){
        //creation of objects
        JFrame loginFrame = new JFrame();
        JPanel loginPanel = new JPanel();


        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150,80,200,35);
        loginButton.setActionCommand("login");
        loginButton.addActionListener(this);

        JButton newUserButton = new JButton("Create New Account");
        newUserButton.setBounds(150,115,200,35);
        newUserButton.setActionCommand("newUser");
        newUserButton.addActionListener(this);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(50,20,80,25);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50,50,80,25);

        successLabel = new JLabel("");
        successLabel.setBounds(150,150,300,25);


        userText = new JTextField(20);
        userText.setBounds(150,20,165,25);

        passwordText = new JPasswordField();
        passwordText.setBounds(150,50,165,25);

        //panel settings
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        loginPanel.setLayout(null);
        loginPanel.add(userLabel);
        loginPanel.add(passwordLabel);
        loginPanel.add(userText);
        loginPanel.add(passwordText);
        loginPanel.add(loginButton);
        loginPanel.add(newUserButton);
        loginPanel.add(successLabel);

        //frame settings
        loginFrame.add(loginPanel);
        loginFrame.setSize(500,300);
        loginFrame.add(loginPanel,BorderLayout.CENTER);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setTitle("Life Organizer - Login");
        loginFrame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String user = userText.getText();
        String password = passwordText.getText();

        Database db = new Database();

        if("login".equals(command)){
            if(db.authenticateUser(user,password)){
                successLabel.setText("Login Successful");
                SwingUtilities.invokeLater(() -> new Dashboard());
            }
            else{
                successLabel.setText("Incorrect Login");
            }
        }
        else if("newUser".equals(command)){
            System.out.println("New user button clicked");
            SwingUtilities.invokeLater(() -> new RegisterUserPage());
        }
    }
}
