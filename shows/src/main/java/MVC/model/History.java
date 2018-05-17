package MVC.model;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import session.factory.MySessionFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by plesha on 15-May-18.
 */

@Entity
@Table(name="history")
public class History implements Serializable {

    private static final long serialVersionUID = 6128016096756071380L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idhistory")
    private int idHistory;

    @Column(name="user_idUser")
    private int user_idUser;

    @Column(name="show_tb_idShow")
    private int show_tb_idShow;


    public History(){
    }

    public History( int idUser, int idShow ){
        this.user_idUser = idUser;
        this.show_tb_idShow = idShow;
    }

    public int getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(int idHistory) {
        this.idHistory = idHistory;
    }

    public int getUser_idUser() {
        return user_idUser;
    }

    public void setUser_idUser(int user_idUser) {
        this.user_idUser = user_idUser;
    }

    public int getShow_tb_idShow() {
        return show_tb_idShow;
    }

    public void setShow_tb_idShow(int show_tb_idShow) {
        this.show_tb_idShow = show_tb_idShow;
    }


    public static List getShowsHistory( int idUser ){

        Session session = MySessionFactory.getSessionFactory().openSession();

        String sql = "SELECT show_tb.Name\n" +
                     "FROM history JOIN show_tb ON show_tb.idShow = history.show_tb_idShow\n" +
                     "WHERE history.user_idUser = "+idUser + ";";

        SQLQuery query = session.createSQLQuery(sql);

        List list = query.list();

       // double result = Double.parseDouble( list.get(0).toString() );

        session.close();

        return  list;

    }



}
