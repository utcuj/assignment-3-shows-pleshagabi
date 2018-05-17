package zmain.clientserver;

import MVC.model.*;
import bridge.createuser.*;
import observer.Subject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * Created by plesha on 09-May-18.
 */
public class Server implements Serializable {

    private static final long serialVersionUID = 6128016096756071380L;


    // ------------------------------------------------ process login ------------------------------------------------

    private static void processLogin( Socket socket ) throws IOException{
        List<User> shows = User.read();

        ObjectOutputStream oos = new ObjectOutputStream( socket.getOutputStream() );
        oos.writeObject( shows );
    }

    // ------------------------------------------------ process regular user ------------------------------------------------

    private static void processRegularUser ( Socket socket, String data ) throws IOException{

        if( data.charAt(0) == '0' ) { // 0 search by ID
            System.out.println("Da e zero " + data.charAt(0));

            String idStr = "";
            for (int i = 1; i < data.length(); i++) {
                idStr += data.charAt(i);
            }

            System.out.println("Id de gasit=" + idStr);


            Show_tb foundShow = Show_tb.findById(Integer.parseInt(idStr));


            System.out.println("Showul gasit="+foundShow.toString());

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(foundShow);

            // / PrintStream p = new PrintStream(socket.getOutputStream(),true);
            //p.println(foundShow);

        }
        else if( data.charAt(0) == '1' ){ // 1 find all shows

            List<Show_tb> shows = Show_tb.read();
            /*for( Show_tb s : shows ){
                System.out.println(s.toString());
            }*/

            ObjectOutputStream oos = new ObjectOutputStream( socket.getOutputStream() );

            oos.writeObject( shows );
        }
        else if( data.charAt(0) == '2' ){

            String idStr = "";
            for (int i = 1; i < data.length(); i++) {
                idStr += data.charAt(i);
            }

            List list = History.getShowsHistory( Integer.parseInt(idStr) );

            ObjectOutputStream oos = new ObjectOutputStream( socket.getOutputStream() );

            oos.writeObject( list );
        }
        else if( data.charAt(0) == '3' ){

            String rating = "";

            String[] tokens = data.split("\\s+");
           // System.out.println(" Tokens: "+tokens[0] + " " + tokens[1] + " " + tokens[2]);

            String idStr = "";
            for (int i = 1; i < tokens[0].length(); i++) {
                idStr += tokens[0].charAt(i);
            }
            /*System.out.println("id Show = " + idStr);
            System.out.println("rating = " + tokens[1] );
            System.out.println("user id = " + tokens[2] );
            */
            Rating rat = new Rating( Integer.parseInt(tokens[1]), Integer.parseInt(idStr), Integer.parseInt(tokens[2]) );

            Rating.create( rat );
        }
        else if( data.charAt(0) == '4' ){
            String[] tokens = data.split("SEPARA");

            String idStr = "";
            for (int i = 1; i < tokens[0].length(); i++) {
                idStr += tokens[0].charAt(i);
            }
          /*  System.out.println("id Show = " + idStr);
            System.out.println("rating = " + tokens[1] );
            System.out.println("user id = " + tokens[2] );
         */
            Comment comment = new Comment(tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(idStr) );

            Comment.create( comment );

        }

    }

    // ------------------------------------------------ process premium user ------------------------------------------------

    private static void processPremiumUser( Socket socket, String data ) throws IOException {
        try {
            java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

            if (data.charAt(0) == 'p' && data.charAt(1) == '0') { // p0 recommend a show
                String[] tokens = data.split("\\s+");

                String idStr = "";
                for (int i = 2; i < tokens[0].length(); i++) {
                    idStr += tokens[0].charAt(i);
                }

                System.out.println("Tok0: " + tokens[0]);
                System.out.println("Tok1: " + tokens[1]);
                System.out.println("Tok2: " + tokens[2]);

                int idShow = Integer.parseInt(idStr);
                int idFriend = Integer.parseInt(tokens[1]);
                // username
                String notiFrom = tokens[2];

                Show_tb show = Show_tb.findById(idShow);

                Notification noti = new Notification(show.getName(), idFriend, notiFrom);

                Notification.create(noti);
            }
            else if( data.charAt(0) == 'p' && data.charAt(1) == '1') {// p1 view notifications
                String[] tokens = data.split("\\s+");

                List<Notification> list = Notification.getNotifications( Integer.parseInt(tokens[1]) );
               // System.out.println( " In server = " + list.get(0) );
                ObjectOutputStream oos;

                if( list.size() != 0 ) {
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(list);
                }else{
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(null);
                }
            }
            else if (data.charAt(0) == 'p' && data.charAt(1) == '2') { // p2 add intereted show
                String[] tokens = data.split("SEPARATOR");
                InterestedShow show = new InterestedShow(Integer.parseInt(tokens[2]),tokens[1]);
                InterestedShow.create( show );
            }
            else if (data.charAt(0) == 'p' && data.charAt(1) == '3') {// p3 view interested showsl ist
                String[] tokens = data.split("\\s+");

                List list = InterestedShow.getInterestedShows( Integer.parseInt(tokens[1]) );
                // System.out.println( " In server = " + list.get(0) );
                ObjectOutputStream oos;

                if( list.size()== 0 ) {
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(null);
                }else{
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject( list );
                }
            }
        }
        catch ( StringIndexOutOfBoundsException ex ){
            System.out.println(" Ex processPremiumUser");
        }
    }

    // ------------------------------------------------ process admin ------------------------------------------------

    private static void processAdmin( Socket socket, String data ) throws IOException{
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        //----------------- ----------------- ----------------- CRUD shows
        if( data.charAt(0) == '0' ){
            List<Show_tb> shows = Show_tb.read();

            ObjectOutputStream oos = new ObjectOutputStream( socket.getOutputStream() );

            oos.writeObject( shows );
        }
        else if( data.charAt(0) == '1' ){
            String[] tokens = data.split("#");

            int year = Integer.parseInt(tokens[3]);
            int month = Integer.parseInt(tokens[4]);
            int day = Integer.parseInt(tokens[5]);
            float rating = Float.parseFloat(tokens[6]);

            java.sql.Date date = new Date(year-1900,month,day);

            Show_tb show = new Show_tb(tokens[1],tokens[2],date,rating);

            int showID = Show_tb.create( show );

            // ------------- SEND EMAIL if an interested show has been uploaded on application

            List<InterestedShow> interestedShows = InterestedShow.read();

            for( InterestedShow intShow : interestedShows ){
                if( show.getName().equalsIgnoreCase( intShow.getShowName() ) ){

                    User user = User.findByID( intShow.getUser_idUser() );
                    /// create the observer
                    Subject subject = new Subject();
                    new PremiumUser( subject );
                    subject.setEmailData( user.getUsername(), user.getEmail(), intShow.getShowName() );

                    InterestedShow.delete( intShow.getIdinterested() );

                }
            }



            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(showID);
        }
        else if( data.charAt(0) == '2' ){
            String idStr = "";
            for (int i = 1; i < data.length(); i++) {
                idStr += data.charAt(i);
            }

            Show_tb foundShow = Show_tb.findById(Integer.parseInt(idStr));

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(foundShow);
        }
        else if( data.charAt(0) == '3' ){
            String[] tokens = data.split("#");

            int year = Integer.parseInt(tokens[3]);
            int month = Integer.parseInt(tokens[4]);
            int day = Integer.parseInt(tokens[5]);
            float rating = Float.parseFloat(tokens[6]);
            int idShow = Integer.parseInt(tokens[7]);

            java.sql.Date date = new Date(year-1900,month,day);

            Show_tb show = new Show_tb(idShow,tokens[1],tokens[2],date,rating);

            Show_tb.update( show );

        }
        else if( data.charAt(0) == '4' ){

            String idStr = "";
            for (int i = 1; i < data.length(); i++) {
                idStr += data.charAt(i);
            }

            Show_tb.delete(Integer.parseInt(idStr));

        }    //----------------- ----------------- ----------------- CRUD users
        else if( data.charAt(0) == '5' ){
            List<User> users = User.read();

            ObjectOutputStream oos = new ObjectOutputStream( socket.getOutputStream() );

            oos.writeObject( users );
        }
        else if( data.charAt(0) == '6' ){
            String[] tokens = data.split("#");

            int age = Integer.parseInt(tokens[3]);
            System.out.println("Am intrat aici; ");

            Person user = null;

            if( tokens[4].equals("regular") ){
                user = new User(tokens[1],tokens[2],age,tokens[5],tokens[6],tokens[7],new RegularUser() );
                user.create();
            }
            else if( tokens[4].equals("premium") ){
                user = new User(tokens[1],tokens[2],age,tokens[5],tokens[6],tokens[7],new PremiumUser() );
                user.create();
            }
            else if( tokens[4].equals("admin") ){
                user = new User(tokens[1],tokens[2],age,tokens[5],tokens[6],tokens[7],new AdminUser() );
                user.create();

            }
            /*ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(userId);
            */
        }
        else if( data.charAt(0) == '7' ) {
            String idStr = "";
            for (int i = 1; i < data.length(); i++) {
                idStr += data.charAt(i);
            }

            User foundUser = User.findByID(Integer.parseInt(idStr));

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(foundUser);
        }
        else if( data.charAt(0) == '8' ){

            String[] tokens = data.split("#");

            User user = new User(Integer.parseInt(tokens[8]),tokens[1],tokens[2],Integer.parseInt(tokens[3]),tokens[4],tokens[5],tokens[6],tokens[7]);
            System.out.println(user.toString());

            User.update( user );
        }
        else if( data.charAt(0) == '9' ){

            String idStr = "";
            for (int i = 1; i < data.length(); i++) {
                idStr += data.charAt(i);
            }

            User.delete(Integer.parseInt(idStr));

        }
    }


    // ------------------------------------------------ main ------------------------------------------------
    public static void main(String[] args) throws IOException {
        int number, tmp;
        System.out.println("Server is running...\nWaiting for client...");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        ServerSocket serverSocket = new ServerSocket(8081);

        try {
            while (true) {

                Socket socket = serverSocket.accept();

                try {
                    Scanner sc = new Scanner(socket.getInputStream());

                    String data = sc.nextLine();

                    System.out.println(data);
                    System.out.println("acum In server=" + data );

                    String typeOfOperation = sc.nextLine();
                    System.out.println("acum In server=" + typeOfOperation );

                   // String rating = sc.nextLine();
                  //  data = data + " " + rating;

                    if( typeOfOperation.equalsIgnoreCase("login") ){
                        processLogin(socket);
                    }
                    else if( typeOfOperation.equalsIgnoreCase("regular") ){
                        processRegularUser(socket,data);
                    }
                    else if( typeOfOperation.equalsIgnoreCase("premium") ){
                        processRegularUser(socket,data);
                        processPremiumUser(socket,data);
                    }
                    else if( typeOfOperation.equalsIgnoreCase("admin") ){
                        processAdmin(socket,data);
                    }

                } finally {
                    socket.close();
                }
            }
        }
        finally {
            serverSocket.close();
        }

    }

}
