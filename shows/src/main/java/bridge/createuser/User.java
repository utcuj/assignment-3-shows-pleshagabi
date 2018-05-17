package bridge.createuser;

import MVC.model.Show_tb;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import session.factory.MySessionFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by plesha on 14-May-18.
 */
@Entity
@Table(name = "user")
public class User extends Person implements Serializable{

    private static final long serialVersionUID = 6128016096756071380L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private int idUser;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "age")
    private int age;

    @Column(name = "userType")
    private String userType;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;


    public User(){

    }
    public User( int idUser ){
        this.idUser = idUser;
    }


    public User( int idUser, String firstName, String lastName, int age, String userType, String username, String password, String email){
        this.idUser = idUser;
        this.firstname = firstName;
        this.lastname = lastName;
        this.age = age;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
    }

    public User( String firstName, String lastName, int age, String username, String password, String email, String userType){
        this.firstname = firstName;
        this.lastname = lastName;
        this.age = age;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
    }


    public User( String firstName, String lastName, int age, String username, String password, String email, PersonAPI personAPI){
        super(  personAPI );
        this.firstname = firstName;
        this.lastname = lastName;
        this.age = age;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /*public User(){}

    public User( String fn, String ln, int age, String userType, String username, String password, String email ){
        this.lastname = ln;
        this.firstname = fn;
        this.age = age;
        this.userType = userType;
        this.username = username;
        this.password = password;
        this.email = email;
    }
*/
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", userType=" + userType +
                ", username="+username+
                ", email="+email+
                '}';
    }

    // -----------------------------------------------------------------------------------------------------------------


    public static Integer createUserInDatabase(User user) {

        Session session = MySessionFactory.getSessionFactory().openSession();

        session.beginTransaction();
        session.save( user );
        session.getTransaction().commit();

        session.close();

        System.out.println("Successfully created " + user.toString());
        return user.getIdUser();

    }

    public static List<User> read() {
        Session session = MySessionFactory.getSessionFactory().openSession();

        @SuppressWarnings("unchecked")
        List<User> users = session.createQuery("FROM User").list();

        session.close();

        System.out.println("Found " + users.size() + " Employees");

        return users;
    }

    public static void update(User user) {
        Session session = MySessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        User updateUser = (User) session.load(User.class, user.getIdUser());
        updateUser.setFirstname(user.getFirstname());
        updateUser.setLastname(user.getFirstname());
        updateUser.setAge(user.getAge());
        updateUser.setUserType(user.getUserType());
        updateUser.setUsername(user.getUsername());
        updateUser.setPassword(user.getPassword());
        updateUser.setEmail(user.getEmail());

        session.getTransaction().commit();
        session.close();

        System.out.println("Successfully updated " + user.toString());

    }



    public static User findByID(Integer id) {

        Session session = MySessionFactory.getSessionFactory().openSession();

       User u = (User) session.load(User.class, id);

        try {
            System.out.println(u.toString());
        }catch (ObjectNotFoundException ex){
            return new User(-1);
        }

        session.close();

        return u;
    }

    public static void delete(Integer id) {
        Session session = MySessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        User e = findByID(id);
        session.delete(e);

        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully deleted " + e.toString());
    }

    public static void deleteAll() {
        Session session = MySessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        org.hibernate.Query query = session.createQuery("DELETE FROM User");
        query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        System.out.println("Successfully deleted all employees.");

    }


    public void create(){
        personAPI.createUser( firstname, lastname, age, username, password, email );
    }

}
