package MVC.view;

import MVC.controller.AdminController;
import MVC.controller.PremiumUserController;
import MVC.controller.RegularUserController;
import bridge.createuser.User;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by plesha on 14-May-18.
 */
public class LoginView {

    private JTextPane passwordTextPane;
    private JButton loginButton;
    private JTextPane usernameTextPane;
    private JTextField tfUsername;
    private JPanel panel;
    private JPasswordField passwordField1;

    private JFrame frame = new JFrame("Shows Management Login Panel");

    public LoginView()  {

        frame.setContentPane(panel);
       // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize( 700,400);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);


        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean userFound = false;

                if( !tfUsername.getText().equals("") && !passwordField1.getText().equals("") ){

                    try {
                        Socket socket = new Socket("localhost", 8081);
                        PrintStream p = new PrintStream(socket.getOutputStream());
                        p.println("data");
                        p.println("login");

                        try {
                            ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());
                            @SuppressWarnings("unchecked")
                            List<User> users = (List<User>) inObj.readObject();

                            for( User u : users ){
                                if( u.getUsername().equals( tfUsername.getText() ) && u.getPassword().equals( passwordField1.getText() ) ){
                                    userFound = true;
                                    if( u.getUserType().equals("regular") ){
                                        RegularUserView view = new RegularUserView("Regular User Panel","regular");
                                        new RegularUserController(u, view);
                                        closeLoginFrame();
                                        socket.close();
                                    }
                                    else if( u.getUserType().equals("premium") ){
                                        RegularUserView view = new RegularUserView("Premium User Panel","Premium");
                                        new PremiumUserController(u, view);
                                        closeLoginFrame();
                                        socket.close();
                                    }
                                    else if( u.getUserType().equals("admin") ){
                                        AdminView view = new AdminView("Admin Panel");
                                        new AdminController(u,view);
                                        closeLoginFrame();
                                        socket.close();
                                    }
                                }
                            }
                        }
                        catch (ClassNotFoundException ex ){
                            ex.printStackTrace();
                            System.out.println("Exception at loginButton");
                        }
                    }
                    catch( IOException ex ){
                        ex.printStackTrace();
                        System.out.println("Exception at loginButton");
                    }
                }
                else
                    showDialog("You need to enter a username and a password");


                if( !userFound )
                    showDialog("Username or Password you entered are incorrect !");
            }
        });
    } // end constructor

    public void closeLoginFrame(){
        frame.setVisible(false);
        frame.dispose();
    }

    public void showDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
