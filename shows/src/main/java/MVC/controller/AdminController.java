package MVC.controller;

import MVC.model.Show_tb;
import MVC.view.AdminView;
import MVC.view.LoginView;
import bridge.createuser.User;

import javax.persistence.criteria.CriteriaBuilder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Date;
import java.util.List;

/**
 * Created by plesha on 16-May-18.
 */
public class AdminController {
    private User model;
    private AdminView view;

    private final String userType = "admin";
    private final int port = 8081;

    public AdminController( User model, AdminView view ){
        this.model = model;
        this.view = view;

        ActionListener[] listeners = { new LogoutButtonListener(),new ViewAllShowsButtonListener(), new ViewAllUsersButtonListener(), new CreateShowButtonListener(),
                        new ReadShowButtonListener(), new UpdateShowButtonListener(), new DeleteShowButtonListener(), new CreateUserButtonListener(), new ReadUserButtonListener(),
                        new UpdateUserButtonListener(), new DeleteUserButtonListener()
        };
        this.view.addAllAdminButtonListeners( listeners );
    }

    //------------------------------------------------------ view all shows ------------------------------------------
    class ViewAllShowsButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {

                Socket socket = new Socket("localhost", port);

                PrintStream p = new PrintStream( socket.getOutputStream());

                p.println("0");
                p.println(userType);

                ObjectInputStream inObj = new ObjectInputStream( socket.getInputStream() );
                try{
                    @SuppressWarnings("unchecked")
                    List<Show_tb> test  = (List<Show_tb>)inObj.readObject();

                    view.cleanModel1();

                    for( Show_tb s : test ){
                        String[] rowData ={ Integer.toString(s.getIdShow()) , s.getName() , s.getDescription(),
                                s.getReleaseDate().toString(), Float.toString(s.getImdbRating()) };
                        view.addRowToModel1(rowData);
                    }
                }catch (ClassNotFoundException ex ){
                    ex.printStackTrace();
                }

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    //------------------------------------------------------ SHOWS CRUD ------------------------------------------

    //------------------------------------------------------ create show ------------------------------------------
    class CreateShowButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {

                if( !(view.tfShowName.getText().equals("")) && !(view.tfDescription.getText().equals("")) &&
                        !(view.tfYear.getText().equals("")) && !(view.tfMonth.getText().equals("") && !(view.tfDay.getText().equals(""))) &&
                        !(view.tfRating.getText().equals("")) ) {

                    try {

                        int year = Integer.parseInt( view.tfYear.getText() );
                        int month = Integer.parseInt( view.tfMonth.getText() );
                        int day = Integer.parseInt( view.tfDay.getText() );

                        float rating = Float.parseFloat( view.tfRating.getText() );

                        boolean isGoodData = checkDateAndRating( year, month, day, rating );
                        if( !isGoodData ) return;

                        Socket socket = new Socket("localhost", port);
                        PrintStream p = new PrintStream(socket.getOutputStream());

                        p.println("1#"+view.tfShowName.getText()+ "#" + view.tfDescription.getText()+ "#" + year +"#" +
                                    month + "#" + day + "#" + view.tfRating.getText() );
                        p.println(userType);

                        try {
                            ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());
                            int showId = (Integer) inObj.readObject();

                            java.sql.Date date = new Date(year-1900,month,day);
                            String data[] = { Integer.toString(showId),view.tfShowName.getText(), view.tfDescription.getText(), date.toString() , view.tfRating.getText() };

                            view.addRowToModel1(data);


                        }catch (ClassNotFoundException ex){
                            ex.printStackTrace();
                        }

                    }catch (NumberFormatException ex ){
                        view.showDialog("You need to enter a number !");
                        //System.err.println("Exceptie create !");
                    }
                }
                else{
                    view.showDialog("You need to complete all fields !");
                }

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    //------------------------------------------------------ view show by id------------------------------------------
    class ReadShowButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if( !view.tfShowID.getText().equals("") ) {
                    try {
                        int id = Integer.parseInt(view.tfShowID.getText());

                        Socket socket = new Socket("localhost", port);
                        PrintStream p = new PrintStream( socket.getOutputStream());

                        p.println("2" + view.tfShowID.getText());
                        p.println(userType);

                        try {
                            ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());
                            Show_tb show = (Show_tb) inObj.readObject();

                            if( show.getIdShow() != -1 ) {
                                view.cleanModel1();
                                String data[] = {Integer.toString(show.getIdShow()), show.getName(),show.getDescription(),show.getReleaseDate().toString(),Float.toString(show.getImdbRating())};
                                view.addRowToModel1(data);
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

    //------------------------------------------------------ update show -----------------------------------------
    class UpdateShowButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {

                if( !view.tfShowID.getText().equals("") && !(view.tfShowName.getText().equals("")) && !(view.tfDescription.getText().equals("")) &&
                        !(view.tfYear.getText().equals("")) && !(view.tfMonth.getText().equals("") && !(view.tfDay.getText().equals(""))) &&
                        !(view.tfRating.getText().equals("")) ) {

                    try {

                        int idShow = Integer.parseInt( view.tfShowID.getText() );
                        int year = Integer.parseInt( view.tfYear.getText() );
                        int month = Integer.parseInt( view.tfMonth.getText() );
                        int day = Integer.parseInt( view.tfDay.getText() );

                        float rating = Float.parseFloat( view.tfRating.getText() );

                        if( year<2000 || year>2020 ){
                            view.showDialog("You need to enter a year between 2000 and 2020 !");
                            return;
                        }

                        if( month<1 || month>12 ){
                            view.showDialog("You need to enter a month between 1 and 12 !");
                            return;
                        }

                        if( day<1 || day>31 ){
                            view.showDialog("You need to enter a day between 1 and 31 !");
                            return;
                        }

                        if( rating<0 || rating>10 ){
                            view.showDialog("You need to enter a rating between 0 and 10 !");
                            return;
                        }


                        Socket socket = new Socket("localhost", port);
                        PrintStream p = new PrintStream(socket.getOutputStream());

                        p.println("3#"+view.tfShowName.getText()+ "#" + view.tfDescription.getText()+ "#" + year +"#" +
                                month + "#" + day + "#" + view.tfRating.getText() +"#"+ view.tfShowID.getText() );
                        p.println(userType);

                        view.cleanModel1();
                        view.viewAllShowsButton.doClick();

                    }catch (NumberFormatException ex ){
                        view.showDialog("You need to enter a number !");
                        //System.err.println("Exceptie create !");
                    }
                }
                else{
                    view.showDialog("You need to complete all fields to update a user !");
                }

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    //------------------------------------------------------ delete show by id------------------------------------------
    class DeleteShowButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if( !view.tfShowID.getText().equals("") ) {
                    try {
                        int id = Integer.parseInt(view.tfShowID.getText());

                        Socket socket = new Socket("localhost", port);
                        PrintStream p = new PrintStream( socket.getOutputStream());

                        p.println("4" + view.tfShowID.getText());
                        p.println(userType);

                        view.cleanModel1();
                        view.viewAllShowsButton.doClick();

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
    }


    //------------------------------------------------------ view all users ------------------------------------------
    class ViewAllUsersButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {

                Socket socket = new Socket("localhost", port);

                PrintStream p = new PrintStream( socket.getOutputStream());

                p.println("5");
                p.println(userType);

                ObjectInputStream inObj = new ObjectInputStream( socket.getInputStream() );
                try{
                    @SuppressWarnings("unchecked")
                    List<User> users  = (List<User>)inObj.readObject();

                    view.cleanModel2();

                    for( User s : users ){
                        String[] rowData ={ Integer.toString(s.getIdUser()) , s.getFirstname() , s.getLastname(),
                                Integer.toString(s.getAge()), s.getUserType(), s.getUsername() , s.getPassword(),
                                s.getEmail()};
                        view.addRowToModel2(rowData);
                    }
                }catch (ClassNotFoundException ex ){
                    System.err.println("Exceptie Class not found ");
                   // ex.printStackTrace();
                }

            }catch (IOException ex){
                System.err.println("Exceptie IO ");
                //ex.printStackTrace();
            }
        }
    }

    //------------------------------------------------------ USER CRUD ------------------------------------------

    //------------------------------------------------------ create user ------------------------------------------
    class CreateUserButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {

                if( !(view.tfFirstName.getText().equals("")) && !(view.tfLastname.getText().equals("")) &&
                        !(view.tfAge.getText().equals("")) && !(view.tfUsername.getText().equals("")) &&
                        !(view.tfPassword.getText().equals("")) && !(view.tfPassword.getText().equals("")) && ( view.regularRadioButton.isSelected() ||
                            view.premiumRadioButton.isSelected() || view.administratorRadioButton.isSelected()) ) {

                    try {

                       int age = Integer.parseInt(view.tfAge.getText());

                        if( age<1 || age>100 ){
                            view.showDialog("Invalid age !");
                            return;
                        }

                        String userToCreate = getUserTypeByRadioButton();

                        Socket socket = new Socket("localhost", port);
                        PrintStream p = new PrintStream(socket.getOutputStream());

                        p.println("6#"+view.tfFirstName.getText()+ "#" + view.tfLastname.getText()+ "#" + view.tfAge.getText() +"#" +
                                userToCreate + "#" + view.tfUsername.getText() + "#" + view.tfPassword.getText() + "#" + view.tfEmail.getText() );
                        p.println(userType);

                        view.viewAllUsersButton.doClick();

                        /*try {
                            ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());
                            int showId = (Integer) inObj.readObject();

                            String data[] = { Integer.toString(showId),view.tfFirstName.getText(), view.tfLastname.getText(), view.tfAge.getText(), userToCreate, view.tfUsername.getText(),
                                    view.tfPassword.getText(), view.tfEmail.getText() };

                            view.addRowToModel2(data);


                        }catch (ClassNotFoundException ex){
                            ex.printStackTrace();
                        }*/

                    }catch (NumberFormatException ex ){
                        view.showDialog("You need to enter a number as a age !");
                        //System.err.println("Exceptie create !");
                    }
                }
                else{
                    view.showDialog("You need to complete all fields !");
                }

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    //------------------------------------------------------ read user ------------------------------------------

    class ReadUserButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if( !view.tfUserID.getText().equals("") ) {
                    try {
                        int id = Integer.parseInt(view.tfUserID.getText());

                        Socket socket = new Socket("localhost", port);
                        PrintStream p = new PrintStream( socket.getOutputStream());

                        p.println("7" + view.tfUserID.getText());
                        p.println(userType);

                        try {
                            ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());
                            User user = (User) inObj.readObject();

                            if( user.getIdUser() != -1 ) {
                                view.cleanModel2();
                                String data[] = { Integer.toString(user.getIdUser()), user.getFirstname(), user.getLastname(),
                                        Integer.toString(user.getAge()), user.getUserType(), user.getUsername(), user.getPassword(), user.getEmail() };
                                view.addRowToModel2(data);
                            }
                            else{
                                view.showDialog("User not found !");
                            }
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }catch(NumberFormatException ex ){
                        view.showDialog("You didn't entered a number for id!");
                    }
                }else{
                    view.showDialog("You need to enter an id to search a user !");
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }//end inner class ViewShowButtonListener

    //------------------------------------------------------ update user -----------------------------------------
    class UpdateUserButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {

                if( !(view.tfUserID.getText().equals("")) && !(view.tfFirstName.getText().equals("")) && !(view.tfLastname.getText().equals("")) &&
                        !(view.tfAge.getText().equals("")) && !(view.tfUsername.getText().equals("")) &&
                        !(view.tfPassword.getText().equals("")) && !(view.tfPassword.getText().equals("")) && ( view.regularRadioButton.isSelected() ||
                        view.premiumRadioButton.isSelected() || view.administratorRadioButton.isSelected()) ) {

                    try {

                        int age = Integer.parseInt(view.tfAge.getText());

                        if( age<1 || age>100 ){
                            view.showDialog("Invalid age !");
                            return;
                        }

                        String userToUpdate = getUserTypeByRadioButton();


                        Socket socket = new Socket("localhost", port);
                        PrintStream p = new PrintStream(socket.getOutputStream());

                        p.println("8#"+view.tfFirstName.getText()+ "#" + view.tfLastname.getText()+ "#" + view.tfAge.getText() +"#" +
                                userToUpdate + "#" + view.tfUsername.getText() + "#" + view.tfPassword.getText()+"#"+ view.tfEmail.getText() + "#" +view.tfUserID.getText() );
                        p.println(userType);

                        view.cleanModel2();
                        view.viewAllUsersButton.doClick();

                    }catch (NumberFormatException ex ){
                        view.showDialog("You need to enter a number !");
                        //System.err.println("Exceptie create !");
                    }
                }
                else{
                    view.showDialog("You need to complete all fields to update a user !");
                }

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
    //------------------------------------------------------ delete user by id------------------------------------------
    class DeleteUserButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if( !view.tfUserID.getText().equals("") ) {
                    try {
                        int id = Integer.parseInt(view.tfUserID.getText());

                        Socket socket = new Socket("localhost", port);
                        PrintStream p = new PrintStream( socket.getOutputStream());

                        p.println("9" + view.tfUserID.getText());
                        p.println(userType);

                        view.viewAllUsersButton.doClick();

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
    }

    //------------------------------------------------------ logout button ------------------------------------------
    class LogoutButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                view.closeFrame();
                new LoginView();

            } catch (Exception ex) {
              //  ex.printStackTrace();
                view.showDialog("Exception found from Admin LogoutButtonListener !");
            }
        }
    }//end inner class LogoutButtonListener

    private String getUserTypeByRadioButton(  ){
        String user = "";
        if( view.regularRadioButton.isSelected() ){
            user = "regular";
        } else if( view.premiumRadioButton.isSelected() ){
            user = "premium";
        }else if( view.administratorRadioButton.isSelected() ){
            user = "admin";
        }
        return user;
    }

    private boolean checkDateAndRating( int year, int month, int day, float rating ){
        boolean goodData = true;
        if( year<2000 || year>2020 ){
            view.showDialog("You need to enter a year between 2000 and 2020 !");
            goodData = false;
        }

        if( month<1 || month>12 ){
            view.showDialog("You need to enter a month between 1 and 12 !");
            goodData = false;
        }

        if( day<1 || day>31 ){
            view.showDialog("You need to enter a day between 1 and 31 !");
            goodData = false;
        }

        if( rating<0 || rating>10 ){
            view.showDialog("You need to enter a rating between 0 and 10 !");
            goodData= false;
        }

        return  goodData;
    }

}
