package MVC.controller;

import MVC.model.Notification;
import MVC.model.Show_tb;
import MVC.view.LoginView;
import MVC.view.RegularUserView;
import bridge.createuser.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by plesha on 09-May-18.
 */
public class RegularUserController {
    protected User model;
    protected RegularUserView view;
    protected String userType; // = "regular";

    protected static final Integer port = 8081;


    public RegularUserController(){
    }

    public RegularUserController( User model, RegularUserView view ){

        this.model = model;
        this.view = view;

        this.userType = view.getUserType();

        this.view.setNotifications(Notification.getNrOfNotifications(model.getIdUser()));

        this.view.addAllRegularUserListeners( new ViewShowButtonListener(),
                                    new ViewAllShowsButtonListener(),
                                    new ViewShowDetailsButtonListener(),
                                    new ViewHistoryButtonListener(),
                                    new GiveRatingButtonListener(),
                                    new GiveCommentButtonListener(),
                                    new LogoutButtonListener() );

    }

    //------------------------------------------------------ regular user button listeners-------------------------------------------

    //------------------------------------------------------ view show by id------------------------------------------
    class ViewShowButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if( !view.tfShowID.getText().equals("") ) {
                    try {

                        int id = Integer.parseInt(view.tfShowID.getText());

                        Socket socket = new Socket("localhost", port);
                        PrintStream p = new PrintStream( socket.getOutputStream());

                        p.println("0" + view.tfShowID.getText());
                        p.println(userType);

                        // Scanner sc1 = new Scanner(socket.getInputStream());
                        try {
                            ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());
                            Show_tb show = (Show_tb) inObj.readObject();

                            System.out.println(show.toString());

                            if( show.getIdShow() != -1 ) {
                                view.cleanModel();
                                String data[] = {Integer.toString(show.getIdShow()), show.getName()};
                                view.addRow(data);
                            }
                            else{
                                view.showDialog("Show not found !");
                            }
                        } catch (ClassNotFoundException ex) {
                             ex.printStackTrace();
                        }
                    }catch(NumberFormatException ex ){
                        view.showDialog("You didn't entered a number for id!");
                    }
                }else{
                    view.showDialog("You need to enter an id to search a show !");
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }//end inner class ViewShowButtonListener


    //------------------------------------------------------ view all shows -----------------------------------------
    class ViewAllShowsButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {

                Socket socket = new Socket("localhost", port);

                PrintStream p = new PrintStream( socket.getOutputStream());

                p.println("1");
                p.println(userType);
                // Scanner sc1 = new Scanner(socket.getInputStream());
                ObjectInputStream inObj = new ObjectInputStream( socket.getInputStream() );
                try{
                    @SuppressWarnings("unchecked")
                    List<Show_tb> test  = (List<Show_tb>)inObj.readObject();

                    view.cleanModel();

                    for( Show_tb s : test ){
                        String[] rowData ={ Integer.toString(s.getIdShow()) , s.getName() };
                        view.addRow(rowData);
                    }

                    if( test!= null )
                        System.out.println(test.get(0).toString());

                }catch (ClassNotFoundException ex ){
                    ex.printStackTrace();
                }
                //  ObjectOutputStream ou = new ObjectOutputStream( socket.getOutputStream() );
                //   String answer = sc1.nextLine();
                //    System.out.println("Data is = "+inObj.toString());

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }//end inner class ViewShowButtonListener


    //------------------------------------------------------ view show details -----------------------------------------
    class ViewShowDetailsButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {


                if( !view.tfShowID.getText().equals("") ) {
                    try {

                        int ids = Integer.parseInt(view.tfShowID.getText());

                        Socket socket = new Socket("localhost", port);

                        PrintStream p = new PrintStream( socket.getOutputStream());

                        p.println("0" + view.tfShowID.getText());
                        p.println(userType);

                        // Scanner sc1 = new Scanner(socket.getInputStream());
                        try {
                            ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());
                            Show_tb show = (Show_tb) inObj.readObject();

                            System.out.println(show.toString());

                            if( show.getIdShow() != -1 ) {
                             //   view.cleanModel();
                                String data[] = {Integer.toString(show.getIdShow()), show.getName()};
                                view.showDialog(
                                        "Show details \n\n" +
                                        "Show Name: "+ show.getName() + "\n" +
                                        "Show Description: "+ show.getDescription() + "\n" +
                                        "Show Release Date: "+ show.getReleaseDate() + "\n" +
                                         "IMDB Rating: "+ show.getImdbRating() + "\n"
                                     );
                             //   view.addRow(data);
                            }
                            else{
                                view.showDialog("Show not found !");
                            }
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }catch(NumberFormatException ex ){
                        view.showDialog("You didn't entered a number for id!");
                    }
                }else{
                    view.showDialog("You need to enter an id to search a show !");
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }

        }
    }//end inner class ViewShowButtonListener


    //------------------------------------------------------ view show by id------------------------------------------
    class ViewHistoryButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Socket socket = new Socket("localhost", port);

                System.out.println( view.tfShowID.getText() );

                PrintStream p = new PrintStream( socket.getOutputStream());

                p.println("2" + model.getIdUser() );
                p.println(userType);
                try {
                    ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());
                    List list = (List) inObj.readObject();

                    String showData = "";

                    System.out.println(list.toString());
                    for( int i = 0 ; i<list.size() ; i++ ){
                        showData += (i+1) + ". " + list.get(i) + "\n";
                    }

                    view.showDialog( "User "  + model.getUsername() + " Show History \n" + showData );


                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }


            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }//end inner class ViewShowButtonListener

    //------------------------------------------------------ give rating ------------------------------------------
    class GiveRatingButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {

                if (!(view.tfShowID.getText().equals("")) && !(view.tfRating.getText().equals(""))) {
                    try {


                        int id = Integer.parseInt(view.tfShowID.getText());
                        int rating = Integer.parseInt(view.tfRating.getText());


                        if (rating < 0 || rating > 10) {
                            view.showDialog(" You need to enter a number between 1 and 10 for rating");
                            return;
                        }

                        Socket socket = new Socket("localhost", port);
                        PrintStream p = new PrintStream(socket.getOutputStream());

                        p.println("3" + view.tfShowID.getText() + " " + view.tfRating.getText() + " " + model.getIdUser());
                        p.println(userType);

                        Show_tb show = Show_tb.findById(Integer.parseInt(view.tfShowID.getText()));

                        view.showDialog("You rated the following show: " + show.getName() + " \n With " + view.tfRating.getText() + " rating !");
                    } catch (NumberFormatException ex) {
                     //   ex.printStackTrace();
                        view.showDialog("You need to enter a number !");
                    }
                }
                else{
                    view.showDialog("You need to enter a Show ID and a Rating ID !");
                }

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }//end inner class GiveRatingButtonListener


    //------------------------------------------------------ give comment ------------------------------------------
    class GiveCommentButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if( !(view.tfShowID.getText().equals("")) && !(view.tfComment.getText().equals("")) ) {
                    try {


                        int id = Integer.parseInt(view.tfShowID.getText());

                        Socket socket = new Socket("localhost", port);
                        PrintStream p = new PrintStream(socket.getOutputStream());

                        p.println("4" + view.tfShowID.getText() + "SEPARA" + view.tfComment.getText() + "SEPARA" + model.getIdUser());
                        p.println(userType);


                        Show_tb show = Show_tb.findById(Integer.parseInt(view.tfShowID.getText()));
                        view.showDialog("You commented on the following show: " + show.getName() + " \n Message:\n\n" + view.tfComment.getText() + " rating !");
                    } catch (NumberFormatException ex) {
                    //    ex.printStackTrace();
                        view.showDialog("You need to enter a number !");
                    }
                }else{
                    view.showDialog("You need to enter a Show ID and a Comment first !");
                }

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }//end inner class GiveCommentButtonListener

    //------------------------------------------------------ logout button ------------------------------------------
    class LogoutButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                view.closeFrame();
                LoginView view = new LoginView();

            } catch (Exception ex) {
                ex.printStackTrace();
                view.showDialog("Exception found from User LogoutButtonListener !");
            }
        }
    }//end inner class LogoutButtonListener

}
