package MVC.model;

import org.hibernate.Session;
import session.factory.MySessionFactory;

import javax.persistence.*;

/**
 * Created by plesha on 09-May-18.
 */

@Entity
@Table(name="Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idComment")
    private int idComment;

    @Column(name="comment")
    private String comment;

    @Column(name="user_idUser")
    private int user_idUser;

    @Column(name="show_idShow")
    private int show_idShow;

    public Comment(){

    }

    public Comment(String comment, int user_idUser, int show_idShow) {
        this.comment = comment;
        this.user_idUser = user_idUser;
        this.show_idShow = show_idShow;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUser_idUser() {
        return user_idUser;
    }

    public void setUser_idUser(int user_idUser) {
        this.user_idUser = user_idUser;
    }

    public int getShow_idShow() {
        return show_idShow;
    }

    public void setShow_idShow(int show_idShow) {
        this.show_idShow = show_idShow;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Comment{" +
                "idComment=" + idComment +
                ", comment='" + comment + '\'' +
                ", user_idUser=" + user_idUser +
                ", show_idShow=" + show_idShow +
                '}';
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static Integer create(Comment comment) {

        Session session = MySessionFactory.getSessionFactory().openSession();

        session.beginTransaction();
        session.save( comment );
        session.getTransaction().commit();

        session.close();

        System.out.println("Successfully created " + comment.toString());
        return comment.getIdComment();
    }


}
