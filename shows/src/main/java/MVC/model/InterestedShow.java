package MVC.model;

import bridge.createuser.User;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import session.factory.MySessionFactory;

import javax.persistence.*;
import java.util.List;

/**
 * Created by plesha on 16-May-18.
 */
@Entity
@Table(name = "interest")
public class InterestedShow {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "idinterest")
    private int idInterest;

    @Column(name = "user_idUser")
    private int user_idUser;

    @Column(name = "showName")
    private String showName;


    public InterestedShow(){

    }

    public InterestedShow(int user_idUser, String showName) {
        this.user_idUser = user_idUser;
        this.showName = showName;
    }

    public int getIdinterested() {
        return idInterest;
    }

    public void setIdinterested(int idinterested) {
        this.idInterest = idinterested;
    }

    public int getUser_idUser() {
        return user_idUser;
    }

    public void setUser_idUser(int user_idUser) {
        this.user_idUser = user_idUser;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public static List<InterestedShow> read() {
        Session session = MySessionFactory.getSessionFactory().openSession();

        @SuppressWarnings("unchecked")
        List<InterestedShow> shows = session.createQuery("FROM InterestedShow").list();

        session.close();

        System.out.println("Found " + shows.size() + " interested shows");

        return shows;
    }


    public static Integer create(InterestedShow show) {

        Session session = MySessionFactory.getSessionFactory().openSession();

        session.beginTransaction();
        session.save( show );
        session.getTransaction().commit();

        session.close();

        System.out.println("Successfully created " + show.toString());
        return show.getIdinterested();

    }

    public static InterestedShow findByID(Integer id) {

        Session session = MySessionFactory.getSessionFactory().openSession();

        InterestedShow show = (InterestedShow) session.load(InterestedShow.class, id);

        session.close();

        return show;
    }

    public static void delete(Integer id) {
        Session session = MySessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        InterestedShow show = findByID(id);
        session.delete( show );

        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully deleted interested show: " + show.toString());
    }

    public static List getInterestedShows(int idUser ) {

        Session session = MySessionFactory.getSessionFactory().openSession();

        String sql = "SELECT showName FROM interest WHERE user_idUser = "+idUser+";";

        SQLQuery query = session.createSQLQuery(sql);

        List listShows = query.list();
        session.close();

        return  listShows;
    }

}
