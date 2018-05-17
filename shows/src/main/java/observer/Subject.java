package observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by plesha on 17-May-18.
 */
public class Subject {
    private List<Observer> observers = new ArrayList<Observer>();

    private String receiverName;
    private String emailTo;
    private String showName;

    private int state;

    public int getState() {
        return state;
    }

    public void setEmailData(String recieverName, String emailTo, String showName) {
        this.receiverName = recieverName;
        this.emailTo = emailTo;
        this.showName = showName;
        notifyAllObservers();
    }

    public String getRecieverName() {
        return receiverName;
    }

    public void setRecieverName(String recieverName) {
        this.receiverName = recieverName;
    }


    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }




    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
