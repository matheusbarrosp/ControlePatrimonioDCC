package entidades;

import java.util.Date;

/**
 * Created by Matheus on 23/01/2017.
 */
public class Patrimony {
    private int id;
    private String name;
    private String responsible;
    private String description;
    private String location;
    private User registeredBy;
    private User lastUpdater;
    private Date registerDate;
    private Date lastUpdate;

    public Patrimony(int id, String name, String responsible, String location, String description, User registeredBy, User lastUpdater, Date registerDate, Date lastUpdate) {
        this.id = id;
        this.name = name;
        this.responsible = responsible;
        this.description = description;
        this.location = location;
        this.registeredBy = registeredBy;
        this.lastUpdater = lastUpdater;
        this.registerDate = registerDate;
        this.lastUpdate = lastUpdate;
    }

    public Patrimony(int id, String name, String responsible, String location, String description, User registeredBy){
        this.id = id;
        this.name = name;
        this.responsible = responsible;
        this.description = description;
        this.location = location;
        this.registeredBy = registeredBy;
        this.registerDate = new Date(); //colocar register date
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getResponsible() {
        return responsible;
    }
    public void setResponsible(String responsible){
        this.responsible = responsible;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }

    public User getRegisteredBy() {
        return registeredBy;
    }

    public User getLastUpdater() {
        return lastUpdater;
    }

    public void setLastUpdater(User user){
        this.lastUpdater = user;
        this.lastUpdate = new Date();
    }
    public Date getRegisterDate() {
        return registerDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }


    @Override
    public int hashCode(){
        return this.name.length();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Patrimony){
            return this.id == ((Patrimony)obj).id;
        }
        return false;
    }
}
