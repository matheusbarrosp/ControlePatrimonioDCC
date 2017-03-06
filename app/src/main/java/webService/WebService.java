package webService;

import entidades.Patrimony;
import entidades.Storage;

/**
 * Created by Matheus on 23/01/2017.
 */
public class WebService {

    public static int REGISTER_PATRIMONY = 0;
    public static int SEARCH_PATRIMONY = 1;
    public static int EDIT_PATRIMONY = 2;
    public static int LOGIN = 3;

    private static WebService instance;
    private WebService(){

    }

    public static synchronized WebService getInstance(){
        if(instance == null){
           instance = new WebService();
        }
        return instance;
    }

    public void login(String name, String password, ServiceCallback callback){
        new Rest(callback, WebService.LOGIN).execute();
    }

    public void edit(Patrimony patrimony, ServiceCallback callback){
        new Rest(callback, WebService.EDIT_PATRIMONY).execute();
    }

    public void register(Patrimony patrimony, ServiceCallback callback){
        new Rest(callback, WebService.REGISTER_PATRIMONY).execute();
    }

    public void searchByID(int id, ServiceCallback callback){
        new Rest(callback, WebService.SEARCH_PATRIMONY).execute();
    }

    public void searchByResponsible(String responsible, ServiceCallback callback){
        new Rest(callback, WebService.SEARCH_PATRIMONY).execute();
    }

    public void searchByLocation(String location, ServiceCallback callback){
        new Rest(callback, WebService.SEARCH_PATRIMONY).execute();
    }
}
