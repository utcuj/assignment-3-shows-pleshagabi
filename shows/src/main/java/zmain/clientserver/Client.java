package zmain.clientserver;

import MVC.controller.AdminController;
import MVC.model.*;
import MVC.view.LoginView;
import bridge.createuser.*;
import MVC.view.AdminView;
import bridge.createuser.User;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by plesha on 09-May-18.
 */
public class Client {

    public static void main(String[] args) throws IOException {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        LoginView view = new LoginView();


       // Socket socket = new Socket("localhost", 8081);

       // Person p1 = new User("Vasile","Pop",55,"vosi","vosi","vosi@gmail.com", new RegularUser() );
       // Person p2 = new User("Alexandra","Stan",24,"ale","ale","stan@gmail.com", new AdminUser() );
       // Person p3 = new User("Pop","David",22,"david","david","pop@gmail.com", new PremiumUser() );


       // String to="plesha.gabi@gmail.com";//change accordingly
       // Email.sendEmail("Plesa",to,"The ballers");

       /* AdminView view = new AdminView("Admin Panel");
        new AdminController((User)p2, view );
        */

    //List list = History.getShowsHistory(1);

     //   System.out.println(list.get(0).toString() +" " + list.get(1).toString());


      /*  p1.create();
        p2.create();
        p3.create();
*/

       // Client.setView( view );
     /*  model.User user = new model.User();
        RegularUserView view1 = new RegularUserView("Premium User Panel","premium");
        PremiumUserController controller = new PremiumUserController(user,view1);
*/

/*
        while( true ) {


            Scanner sc = new Scanner(System.in);

            String data = "salam";

         //   System.out.println("Enter a number ");
          //  number = sc.nextInt();
            PrintStream p = new PrintStream(socket.getOutputStream());
            p.println(data);


            Scanner sc1 = new Scanner(socket.getInputStream());
//            tmp = sc1.nextInt();
//            System.out.println(tmp);

           // String answer = sc1.nextLine();
            System.out.println(" Number: "+data+" Square = ");
           // System.out.println(answer + " Number: "+data+" Square = ");


            // BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
            // String answer = input.readLine();
            // System.out.println(answer + " Number: " + number );
            // JOptionPane.showMessageDialog(null, answer);


        }
        */
    }
}
