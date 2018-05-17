package MVC.model;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import session.factory.MySessionFactory;

import javax.persistence.*;
import java.util.List;

/**
 * Created by plesha on 09-May-18.
 */
@Entity
@Table(name="rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idRating")
    private int idRating;

    @Column(name="rating")
    private int rating;

    @Column(name="show_tb_idShow")
    private int show_tb_idShow;

    @Column(name="user_idUser")
    private int user_idUser;

    public Rating(){

    }

    public Rating(int rating, int show_tb_idShow, int user_idUser) {
        this.rating = rating;
        this.show_tb_idShow = show_tb_idShow;
        this.user_idUser = user_idUser;
    }

    public int getIdRating() {
        return idRating;
    }

    public void setIdRating(int idRating) {
        this.idRating = idRating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getShow_tb_idShow() {
        return show_tb_idShow;
    }

    public void setShow_tb_idShow(int show_tb_idShow) {
        this.show_tb_idShow = show_tb_idShow;
    }

    public int getUser_idUser() {
        return user_idUser;
    }

    public void setUser_idUser(int user_idUser) {
        this.user_idUser = user_idUser;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Rating{" +
                "idRating=" + idRating +
                ", rating=" + rating +
                ", show_tb_idShow=" + show_tb_idShow +
                ", user_idUser=" + user_idUser +
                '}';
    }


    // -----------------------------------------------------------------------------------------------------------------


    public static Integer create(Rating rating) {

        Session session = MySessionFactory.getSessionFactory().openSession();

        session.beginTransaction();
        session.save( rating );
        session.getTransaction().commit();

        session.close();

        System.out.println("Successfully created " + rating.toString());
        return rating.getIdRating();
    }

    public static double getAvgRating( int showId ){

        Session session = MySessionFactory.getSessionFactory().openSession();

        String sql = "SELECT avg(rating) FROM rating WHERE show_tb_idShow ="+showId;

        SQLQuery query = session.createSQLQuery(sql);

        List list = query.list();

        double result = Double.parseDouble( list.get(0).toString() );

        session.close();

        return  result;

    }

}
