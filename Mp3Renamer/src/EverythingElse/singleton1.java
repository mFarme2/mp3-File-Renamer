package EverythingElse;

/**
 * Created by Morgan on 4/18/2016.
 */

public class singleton1 {
    private static singleton1 instance = null;

    protected singleton1(){
    }

    public static singleton1 getInstance(){
        if (instance == null){
            instance = new singleton1();
        }
        return instance;
    }
}
