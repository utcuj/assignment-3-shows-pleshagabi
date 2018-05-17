package observer;

/**
 * Created by plesha on 17-May-18.
 */
public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
