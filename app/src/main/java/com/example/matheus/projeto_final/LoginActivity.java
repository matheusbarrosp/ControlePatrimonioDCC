package com.example.matheus.projeto_final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import entidades.Storage;
import entidades.User;
import webService.ServiceCallback;
import webService.WebService;

public class LoginActivity extends AppCompatActivity implements ServiceCallback{


    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ((EditText) findViewById(R.id.etUsername)).setText("amanda");
        ((EditText) findViewById(R.id.etPassword)).setText("123");
    }

    public void btLoginClick(View view){
        progressDialog = ProgressDialog.show(this, "", "Autenticando...");
        progressDialog.show();
        String usuario = ((EditText) findViewById(R.id.etUsername)).getText().toString();
        String password = ((EditText) findViewById(R.id.etPassword)).getText().toString();

        WebService.getInstance().login(usuario, password, this);

    }

    @Override
    public void serviceCallback(String result, int code) {
        if(code == WebService.LOGIN) {
            String usuario = ((EditText) findViewById(R.id.etUsername)).getText().toString();
            Storage.getInstance().setLoggedUser(new User(1, usuario, usuario));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        progressDialog.dismiss();
    }
}
