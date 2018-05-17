package MVC.controller;

import MVC.model.Notification;
import MVC.model.Show_tb;
import MVC.view.RegularUserView;
import bridge.createuser.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by plesha on 16-May-18.
 */

public class PremiumUserController extends RegularUserController {
   // private User model;
    //private PremiumUserView view;

    public PremiumUserController(User model, RegularUserView view){

        super(model, view );
        super.view.addAllPremiumUserListeners( new RecommendShowButtonListener() ,
                                                new ViewNotificationsButtonListener(),
                                                new AddInterestedShowButtonListener(),
                                                new ViewInterestedShowsButtonListener()
        );
    }

    //------------------------------------------------------ premium user button listeners-------------------------------------------

    //------------------------------------------------------ recommend show button------------------------------------------
    class RecommendShowButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if( !(view.tfFriendID.getText().equals("")) && !(view.tfShowID.getText().equals("")) ) {
                    try {

                        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

                        int idShow = Integer.parseInt(view.tfShowID.getText());
                        int idFriend = Integer.parseInt(view.tfFriendID.getText());

                        Socket socket = new Socket("localhost", port);
                        PrintStream p = new PrintStream(socket.getOutputStream());

                        p.println("p0" + view.tfShowID.getText() + " " + view.tfFriendID.getText() + " " + model.getUsername());
                        p.println(userType);

                        view.notifications = Notification.getNrOfNotifications( Integer.parseInt(view.tfFriendID.getText()) );
                      //  view.notifications++;
                        view.updateNotifications();

                        Show_tb show = Show_tb.findById(Integer.parseInt(view.tfShowID.getText()));
                        view.showDialog("You recommended the following show: " + show.getName() + " to your friend !");
                    } catch (NumberFormatException ex) {
                      //  ex.printStackTrace();
                        view.showDialog("You need to enter a number !");
                    }
                }
                else{
                    view.showDialog("You need to enter a Friend ID and a Show ID");
                }

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }//end inner class GiveCommentButtonListener

    //------------------------------------------------------ view notifications ------------------------------------------
    class ViewNotificationsButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            view.setNotifications( 0 );

            try {

                java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

                Socket socket = new Socket("localhost", port);
                PrintStream p = new PrintStream(socket.getOutputStream());

                p.println("p1" + " " + model.getIdUser());
                p.println(userType);

                ObjectInputStream inObj = new ObjectInputStream( socket.getInputStream() );

                try{
                    @SuppressWarnings("unchecked")
                    List<Notification> list = (List<Notification>)inObj.readObject();
                    if( list != null ) {
                        String notifData = "";
                        System.out.println("lista = " + list.toString());
                        int id = 0;
                        for (Notification notification : list) {
                            //String[] rowData ={ Integer.toString(.getIdShow()) , s.getName() };

                            notifData += ("Notification number: " + (id + 1) + "\n" +
                                    "Received from user: " + notification.getFromUser() + "\n" +
                                    "Recommend for you the following show: " + notification.getShowName() + "\n\n");
                            id++;
                        }
                            view.showDialog(notifData);

                    }else{
                        view.showDialog(" You don't have new notifications !");
                    }
                }catch (ClassNotFoundException ex ){
                    ex.printStackTrace();
                }

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }//end inner class ViewNotificationsButtonListener

    //------------------------------------------------------ add interested show ------------------------------------------
    class AddInterestedShowButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if( !(view.tfShowName.getText().equals("")) ) {

                    String showName = view.tfShowName.getText();

                    Socket socket = new Socket("localhost", port);
                    PrintStream p = new PrintStream(socket.getOutputStream());

                    p.println("p2"+"SEPARATOR" + showName + "SEPARATOR" + model.getIdUser());
                    p.println(userType);

                    view.showDialog("You entered the following show: " + showName + " into your interested list!\n\n" +
                            "We will notify you with an e-mail when the show is uploaded on the application !\n");
                }
                else{
                    view.showDialog("You need to enter a show !");
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }//end inner class GiveRatingButtonListener

    //------------------------------------------------------ view interested shows ------------------------------------------
    class ViewInterestedShowsButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            try {
                Socket socket = new Socket("localhost", port);
                java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

                PrintStream p = new PrintStream(socket.getOutputStream());

                p.println("p3" + " " + model.getIdUser());
                p.println(userType);

                ObjectInputStream inObj = new ObjectInputStream( socket.getInputStream() );

                try{
                    @SuppressWarnings("unchecked")
                    List list = (List)inObj.readObject();
                    if( list != null ) {

                        //System.out.println("lista = " + list.toString());

                        String shows = "";
                        for (int i=0 ; i<list.size() ; i++ ){
                            //String[] rowData ={ Integer.toString(.getIdShow()) , s.getName() };

                            shows += ("Show Number: " + (i + 1) + "\n" +
                                    "Show Name: " + list.get(i) + "\n\n"  );
                        }
                        view.showDialog("Your interested show list is the following:\n "+ shows );

                    }else{
                        view.showDialog(" You don't have new notifications !");
                    }
                }catch (ClassNotFoundException ex ){
                    ex.printStackTrace();
                }

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }//end inner class ViewNotificationsButtonListener
}
