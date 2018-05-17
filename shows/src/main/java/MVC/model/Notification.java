package MVC.model;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import session.factory.MySessionFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by plesha on 16-May-18.
 */
@Entity
@Table(name = "notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 6128016096756071380L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnotification")
    private int idNotification;

    @Column(name = "showName")
    private String showName;

    @Column(name = "user_idUser")
    private int user_idUser;

    @Column(name = "fromUser")
    private String fromUser;


    public Notification(){

    }

    public Notification(  String showName, int user_idUser, String fromUser ){
        this.showName = showName;
        this.user_idUser = user_idUser;
        this.fromUser = fromUser;
    }

    public int getIdnotification() {
        return idNotification;
    }

    public void setIdnotification(int idnotification) {
        this.idNotification = idnotification;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public int getUser_idUser() {
        return user_idUser;
    }

    public void setUser_idUser(int user_idUser) {
        this.user_idUser = user_idUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public static Integer create(Notification notify) {

        Session session = MySessionFactory.getSessionFactory().openSession();

        session.beginTransaction();
        session.save( notify );
        session.getTransaction().commit();

        session.close();

        System.out.println("Successfully created " + notify.toString());
        return notify.getIdnotification();

    }

    public static int getNrOfNotifications( int idUser ){

        Session session = MySessionFactory.getSessionFactory().openSession();

        String sql = "SELECT count(*) FROM notification WHERE user_idUser = "+idUser;
        SQLQuery query = session.createSQLQuery(sql);

        List list = query.list();

        int result = Integer.parseInt( list.get(0).toString() );

        session.close();

        return  result;

    }

    public static List<Notification> getNotifications( int idUser ) {

        Session session = MySessionFactory.getSessionFactory().openSession();

        String sql = "SELECT showName FROM notification WHERE user_idUser = "+idUser+";";
        String sql2 = "SELECT fromUser FROM notification WHERE user_idUser = "+idUser+";";

        SQLQuery query = session.createSQLQuery(sql);
        SQLQuery query2 = session.createSQLQuery(sql2);

        List listShows = query.list();
        List listUsers = query2.list();
        session.close();

        List<Notification> nots = new ArrayList<Notification>();

        for( int i = 0 ; i<listShows.size() ; i++ ){
            Notification notification = new Notification(listShows.get(i).toString(),idUser, listUsers.get(i).toString() );
            nots.add( notification );
        }

        return  nots;
    }

}
