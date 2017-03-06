package webService;

import android.os.AsyncTask;

/**
 * Created by Matheus on 23/01/2017.
 */
public class Rest extends AsyncTask<String, Integer, String> {

    private final ServiceCallback callback;
    private final int code;

    public Rest(ServiceCallback callback, int code){
        this.callback = callback;
        this.code = code;
    }

    @Override
    protected void onPostExecute(String result){
        callback.serviceCallback(result, code);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello";
    }

}
