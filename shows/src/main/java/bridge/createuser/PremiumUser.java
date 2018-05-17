package bridge.createuser;


import observer.Observer;
import observer.Subject;
import send.mail.Email;

/**
 * Created by plesha on 14-May-18.
 */
public class PremiumUser extends Observer implements PersonAPI {

    public PremiumUser(){

    }

    public PremiumUser(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    public void createUser( String firstName, String lastName, int age, String username, String password, String email){
        User user = new User( firstName,lastName,age,username,password,email, "premium" );
        User.createUserInDatabase( user );
    }


    @Override
    public void update() {

        Email.sendEmail( subject.getRecieverName() , subject.getEmailTo(), subject.getShowName() );
        System.out.println( "Reciever Name: " +subject.getRecieverName() + " Email: " + subject.getEmailTo() + " Show name: " + subject.getShowName() );
    }
}
