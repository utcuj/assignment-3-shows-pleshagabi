package bridge.createuser;

/**
 * Created by plesha on 14-May-18.
 */
public class RegularUser implements PersonAPI {

    public void createUser( String firstName, String lastName, int age, String username, String password, String email){
        User user = new User( firstName,lastName,age,username,password,email,"regular");
        User.createUserInDatabase( user );
    }

}
