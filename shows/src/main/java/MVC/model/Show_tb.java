package MVC.model;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import session.factory.MySessionFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created by plesha on 14-May-18.
 */
@Entity
@Table(name="show_tb")
public class Show_tb implements Serializable  {
    private static final long serialVersionUID = 6128016096756071380L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idShow")
    private int idShow;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "ReleaseDate")
    private Date releaseDate;

    @Column(name = "IMDBRating")
    private float imdbRating;


    public Show_tb(){
    }

    public Show_tb( int id ){
        this.idShow = id;
    }

    public Show_tb( int idShow, String name, String description, Date releaseDate, float imdbRating ){
        this.idShow = idShow;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.imdbRating = imdbRating;
    }

    public Show_tb( String name, String description, Date releaseDate, float imdbRating ){

        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.imdbRating = imdbRating;

    }


    public int getIdShow() {
        return idShow;
    }

    public void setIdShow(int idShow) {
        this.idShow = idShow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }




    @Override
    public String toString() {
        return "Show{" +
                "idShow=" + idShow +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", imdbRating=" + imdbRating +
                '}';
    }


    // -----------------------------------------------------------------------------------------------------------------

    public static Integer create(Show_tb show) {

        Session session = MySessionFactory.getSessionFactory().openSession();

        session.beginTransaction();
        session.save( show );
        session.getTransaction().commit();

        session.close();

        System.out.println("Successfully created " + show.toString());
        return show.getIdShow();
    }

    public static Show_tb findById(Integer id){

        Session session = MySessionFactory.getSessionFactory().openSession();
        Show_tb show = (Show_tb) session.load(Show_tb.class, id);

        try {
            System.out.println(show.toString());
        }catch (ObjectNotFoundException ex){
            return new Show_tb(-1);
        }
        session.close();

        return show;

    }
    public static List<Show_tb> read() {
        Session session = MySessionFactory.getSessionFactory().openSession();

        @SuppressWarnings("unchecked")
        List<Show_tb> shows = session.createQuery("FROM Show_tb").list();

        session.close();

        System.out.println("Found " + shows.size() + " Shows");

        return shows;
    }

    public static void update(Show_tb show) {
        Session session = MySessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        Show_tb updateShow = (Show_tb) session.load(Show_tb.class, show.getIdShow());
        updateShow.setName(show.getName());
        updateShow.setDescription(show.getDescription());
        updateShow.setReleaseDate(show.getReleaseDate());
        updateShow.setImdbRating(show.getImdbRating());

        session.getTransaction().commit();
        session.close();

        System.out.println("Successfully updated " + show.toString());
    }

    public static void delete(Integer id) {

        Show_tb e = findById(id);

        Session session = MySessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(e);

        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully deleted " + e.toString());
    }


}
