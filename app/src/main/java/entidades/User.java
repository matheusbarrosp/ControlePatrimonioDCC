package entidades;

/**
 * Created by Matheus on 23/01/2017.
 */
public class User{

    private int id;
    private String username;
    private String name;

    public User(int id, String username, String name){
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getUsername(){
        return this.username;
    }
}
