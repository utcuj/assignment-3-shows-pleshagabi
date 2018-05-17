package MVC.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

/**
 * Created by plesha on 16-May-18.
 */
public class AdminView {
    private JTable table1;
    private JTable table2;
    private JPanel panel;
    private JTextPane showNameTextPane;
    private JTextPane showDescriptionTextPane;
    private JTextPane releaseDateTextPane;
    private JTextPane IMDBRatingTextPane;
    public JTextField tfShowName;
    public JTextField tfDescription;
    public JTextField tfRating;
    private JTextPane yearTextPane;
    private JTextPane monthTextPane;
    private JTextPane dayTextPane;
    public JTextField tfYear;
    public JTextField tfMonth;
    public JTextField tfDay;
    private JTextPane userFirstNameTextPane;
    private JTextPane lastNameTextPane;
    private JTextPane ageTextPane;
    private JTextPane userTypeTextPane;
    private JTextPane usernameTextPane;
    private JTextPane eMailTextPane;
    private JTextPane passwordTextPane;
    public JTextField tfFirstName;
    public JTextField tfLastname;
    public JTextField tfAge;
    public JTextField tfUsername;
    public JTextField tfPassword;
    public JTextField tfEmail;
    private JButton createShowButton;
    private JButton updateShowButton;
    private JButton deleteShowButton;
    private JButton readShowButton;
    private JButton createUserButton;
    private JButton updateUserButton;
    private JButton readUserButton;
    private JButton deleteUserButton;
    private JButton logoutButton;
    public JButton viewAllUsersButton;
    public JButton viewAllShowsButton;
    private JTextPane showIDTextPane;
    public JTextField tfShowID;
    private JTextPane userIDTextPane;
    public JTextField tfUserID;
    public JRadioButton regularRadioButton;
    public JRadioButton premiumRadioButton;
    public JRadioButton administratorRadioButton;

    private DefaultTableModel model1,model2;
    private JFrame frame;

    public AdminView( String frameName ){
        this.frame = new JFrame( frameName );

        ButtonGroup group = new ButtonGroup();
        group.add(regularRadioButton);
        group.add(premiumRadioButton);
        group.add(administratorRadioButton);

        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);


    }


    public void addAllAdminButtonListeners(ActionListener[] listeners ){
        logoutButton.addActionListener( listeners[0] );
        viewAllShowsButton.addActionListener(listeners[1] );
        viewAllUsersButton.addActionListener( listeners[2] );
        createShowButton.addActionListener( listeners[3] );
        readShowButton.addActionListener( listeners[4] );
        updateShowButton.addActionListener( listeners[5] );
        deleteShowButton.addActionListener( listeners[6] );
        createUserButton.addActionListener( listeners[7] );
        readUserButton.addActionListener( listeners[8] );
        updateUserButton.addActionListener( listeners[9] );
        deleteUserButton.addActionListener( listeners[10] );
    }

    public void showDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void cleanModel1(){
        // remove all rows
        while (table1.getRowCount() > 0) {
            ((DefaultTableModel) table1.getModel()).removeRow(0);
        }
    }

    public void cleanModel2(){
        // remove all rows
        while (table2.getRowCount() > 0) {
            ((DefaultTableModel) table2.getModel()).removeRow(0);
        }
    }

    public void addRowToModel1( String[] data ){
        model1.addRow(data);
    }
    public void addRowToModel2( String[] data ){
        model2.addRow(data);
    }

    public void closeFrame(){
        frame.setVisible(false);
        frame.dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        String column[] = {"ID Show", "Show Name", "Description", "Release Date", "IMDB Rating"};
        model1 = new DefaultTableModel(column, 0);
        table1 = new JTable(model1);

        String column2[] = {"ID User", "Firstname", "Lastname", "Age", "User Type", "Username","Password","Email"};
        model2 = new DefaultTableModel(column2, 0);
        table2 = new JTable(model2);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table1.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        table2.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );


    }
}
