package MVC.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

/**
 * Created by plesha on 09-May-18.
 */
public class RegularUserView  {

    //private static final long serialVersionUID = 6128016096756071380L;

    private JTable table1;
    private JButton searchButton;
    private JTextPane showIDTextPane;
    public JTextField tfShowID;
    private JButton viewDetailsButton;
    private JButton myHistoryButton;
    private JTextPane giveRatingTextPane;
    public JTextField tfRating;
    private JButton submitButton;
    private JTextPane addCommentTextPane;
    private JButton addButton;
    private JPanel panel;
   // public JTextArea textAreaComment;
    private JButton viewAllButton;
    private JTextPane friendIDTextPane;
    public JTextField tfFriendID;
    private JButton recommendButton;
    private JTextPane showNameTextPane;
    public JTextField tfShowName;
    private JButton interestedInButton;
    private JButton notifyButton;
    public JTextField tfComment;
    private JTextPane newNotificationsTextPane;
    private JTextField tfNotify;
    private JButton interestedShowListButton;
    private JButton logoutButton;


    private DefaultTableModel model;
    private JFrame frame; //= new JFrame("Regular User Panel");
    private String userType;

    public int notifications;

    public String getUserType( ){
        return this.userType;
    }
    public void setJFrameName( String name ){
        this.frame = new JFrame( name );
    }

    public void setNotifications( int notifications ){
        this.notifications = notifications;
        updateNotifications();
    }


    public RegularUserView( String frameName, String userType ){

        this.userType = userType;
        this.frame = new JFrame( frameName );

        frame.setContentPane(panel);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        tfNotify.setHorizontalAlignment(JTextField.CENTER);

        tfNotify.setText( Integer.toString(notifications) );

        if( userType.equalsIgnoreCase("regular") ){
            friendIDTextPane.setVisible( false );
            showNameTextPane.setVisible( false );
            tfShowName.setVisible( false );
            tfFriendID.setVisible( false );
            recommendButton.setVisible( false );
            interestedInButton.setVisible( false );
            notifyButton.setVisible( false );
            newNotificationsTextPane.setVisible( false );
            tfNotify.setVisible( false );
            interestedShowListButton.setVisible( false );
        }

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }


    public void addAllRegularUserListeners(ActionListener a1, ActionListener a2, ActionListener a3, ActionListener a4,
                                           ActionListener a5, ActionListener a6, ActionListener a7){
        searchButton.addActionListener( a1 );
        viewAllButton.addActionListener( a2 );
        viewDetailsButton.addActionListener( a3 );
        myHistoryButton.addActionListener( a4 );
        submitButton.addActionListener( a5 );
        addButton.addActionListener( a6 );
        logoutButton.addActionListener( a7 );
    }

    public void addAllPremiumUserListeners( ActionListener a1, ActionListener a2, ActionListener a3, ActionListener a4 ){
        recommendButton.addActionListener( a1 );
        notifyButton.addActionListener( a2 );
        interestedInButton.addActionListener( a3 );
        interestedShowListButton.addActionListener( a4 );
    }


    public void showDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void cleanModel(){
        // remove all rows
        while (table1.getRowCount() > 0) {
            ((DefaultTableModel) table1.getModel()).removeRow(0);
        }
    }

    public void addRow( String[] data ){
        model.addRow(data);
    }

    public void updateNotifications(){
        tfNotify.setText( Integer.toString(notifications) );
    }

    public void closeFrame(){
        frame.setVisible(false);
        frame.dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        String column[] = {"ID Show", "Show Name"};
        model = new DefaultTableModel(column, 0);
        table1 = new JTable(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table1.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
    }

}
