package com.example.matheus.projeto_final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import entidades.Patrimony;
import entidades.Storage;
import webService.ServiceCallback;
import webService.WebService;

public class EditActivity extends AppCompatActivity  implements ServiceCallback{

    public final static String ID_PATRIMONY = "ID_PATRIMONY";
    private int id;
    private Patrimony patrimony;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        id = (int) getIntent().getExtras().get(ID_PATRIMONY);
        progressDialog = ProgressDialog.show(this, "", "Consultando dados no servidor...");
        progressDialog.show();
        WebService.getInstance().searchByID(id, this);

    }


    public void editPatrimony(){
        patrimony.setName(((EditText)findViewById(R.id.etNameEdit)).getText().toString());
        patrimony.setResponsible(((EditText)findViewById(R.id.etReponsibleEdit)).getText().toString());
        patrimony.setLocation((((EditText)findViewById(R.id.etLocationEdit)).getText().toString()));
        patrimony.setDescription(((EditText)findViewById(R.id.etDescriptEdit)).getText().toString());
        patrimony.setLastUpdater(Storage.getInstance().getLoggedUser());
    }

    public void btEditPatrimonyClick(View view){
        editPatrimony();
        progressDialog = ProgressDialog.show(this, "", "Enviando dados para o servidor...");
        WebService.getInstance().edit(patrimony, this);
    }

    public void setFieldsValues(Patrimony patrimony){
        ((EditText)findViewById(R.id.etNameEdit)).setText(patrimony.getName());
        ((EditText)findViewById(R.id.etReponsibleEdit)).setText(patrimony.getResponsible());
        ((EditText)findViewById(R.id.etLocationEdit)).setText(patrimony.getLocation());
        ((EditText)findViewById(R.id.etDescriptEdit)).setText(patrimony.getDescription());
        ((EditText)findViewById(R.id.etIdEdit)).setText(""+patrimony.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        ((TextView)findViewById(R.id.tvRegisterDate)).setText("Cadastrado por " + patrimony.getRegisteredBy().getName()
                + " na data " + sdf.format(patrimony.getRegisterDate()));
        if(patrimony.getLastUpdate() != null){
            ((TextView)findViewById(R.id.tvEditDate)).setText("Alterado pela última vez por " + patrimony.getLastUpdater().getName()
                    + " na data " + sdf.format(patrimony.getLastUpdate()));
        }
    }

    @Override
    public void serviceCallback(String result, int code) {
        if(code == WebService.SEARCH_PATRIMONY){
            patrimony = Storage.getInstance().getPatrimonyById(id);
            if(patrimony != null){
                setFieldsValues(patrimony);
            }else{
                Toast.makeText(this, "Não foi possível carregar os dados do patrimônio", Toast.LENGTH_SHORT).show();
            }
        }else if(code == WebService.EDIT_PATRIMONY){
            Toast.makeText(this, "O patrimônio foi editado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }
        progressDialog.dismiss();
    }
}
