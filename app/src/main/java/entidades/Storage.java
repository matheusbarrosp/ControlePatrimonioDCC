package entidades;

import java.util.ArrayList;

/**
 * Created by Matheus on 23/01/2017.
 */
public class Storage {

    private static Storage instance;
    private User loggedUser;
    public ArrayList<Patrimony> patrimonies; //apenas para testes!

    private Storage(){
        this.patrimonies = new ArrayList<>();
    }

    public User getLoggedUser(){
        return this.loggedUser;
    }

    public ArrayList<Patrimony> getPatrimonyByResponsible(String responsible){
        ArrayList<Patrimony> patrimonies = new ArrayList<Patrimony>();
        for(Patrimony p : this.patrimonies){
            if(p.getResponsible().equalsIgnoreCase(responsible)){
                patrimonies.add(p);
            }
        }
        return patrimonies;
    }

    public ArrayList<Patrimony> getPatrimonyByLocation(String location){
        ArrayList<Patrimony> patrimonies = new ArrayList<Patrimony>();
        for(Patrimony p : this.patrimonies){
            if(p.getLocation().equalsIgnoreCase(location)){
                patrimonies.add(p);
            }
        }
        return patrimonies;
    }

    public Patrimony getPatrimonyById(int id){
        for(Patrimony p : this.patrimonies){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

    public void setLoggedUser(User loggedUser){
        this.patrimonies.add(new Patrimony(1, "Computador1", "Durelli1","DCC 04","Computador 1 do Durelli 1 no Dcc 04", loggedUser));
        this.patrimonies.add(new Patrimony(2, "Computador2", "Durelli1","DCC 05","Computador 2 do Durelli 1 no Dcc 05", loggedUser));
        this.patrimonies.add(new Patrimony(3, "Computador3", "Durelli1","DCC 04","Computador 3 do Durelli 1 no Dcc 04", loggedUser));
        this.patrimonies.add(new Patrimony(4, "Computador4", "Durelli2","DCC 05","Computador 4 do Durelli 2 no Dcc 05", loggedUser));
        this.patrimonies.add(new Patrimony(5, "Computador5", "Durelli2","DCC 04","Computador 5 do Durelli 2 no Dcc 04", loggedUser));
        this.patrimonies.add(new Patrimony(6, "Computador6", "Durelli2","DCC 05","Computador 6 do Durelli 2 no Dcc 05", loggedUser));
        this.loggedUser = loggedUser;

    }

    public static synchronized Storage getInstance(){
        if(instance == null){
            instance = new Storage();
        }
        return instance;
    }
}
