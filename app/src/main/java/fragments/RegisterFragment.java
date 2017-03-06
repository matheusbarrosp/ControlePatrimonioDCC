package fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheus.projeto_final.CameraActivity;
import com.example.matheus.projeto_final.MainActivity;
import com.example.matheus.projeto_final.R;

import entidades.Patrimony;
import entidades.Storage;
import webService.ServiceCallback;
import webService.WebService;

/**
 * Created by hp1 on 21-01-2015.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener, ServiceCallback{



    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_tab,container,false);
        ((ImageButton)v.findViewById(R.id.btRegisterQr)).setOnClickListener(this);
        ((Button)v.findViewById(R.id.btRegisterPatrimony)).setOnClickListener(this);
        return v;
    }



    public void clearFields(){
        ((EditText)getActivity().findViewById(R.id.etId)).setText("");
        ((EditText)getActivity().findViewById(R.id.etDescript)).setText("");
        ((EditText)getActivity().findViewById(R.id.etLocation)).setText("");
        ((EditText)getActivity().findViewById(R.id.etName)).setText("");
        ((EditText)getActivity().findViewById(R.id.etReponsible)).setText("");
    }

    public void btRegisterPatrimonyClick(View v){
        Patrimony patrimony = buildPatrimony();
        if(patrimony != null){
            progressDialog = ProgressDialog.show(getActivity(), "", "Cadastrando patrimônio no servidor...");
            progressDialog.show();
            WebService.getInstance().register(patrimony, this);
        }
    }

    public Patrimony buildPatrimony(){
        MainActivity main = (MainActivity) getActivity();
        int id;
        try{
            id = Integer.parseInt(((EditText) main.findViewById(R.id.etId)).getText().toString());
        }catch(NumberFormatException ex){
            Toast.makeText(main, "Id inválido", Toast.LENGTH_LONG).show();
            return null;
        }
        String name = ((EditText) main.findViewById(R.id.etName)).getText().toString();
        if(name.isEmpty()){
            Toast.makeText(main, "O campo Nome deve ser preenchido", Toast.LENGTH_LONG).show();
            return null;
        }
        String reponsible = ((EditText) main.findViewById(R.id.etReponsible)).getText().toString();
        String location = ((EditText) main.findViewById(R.id.etLocation)).getText().toString();
        if(location.isEmpty()){
            Toast.makeText(main, "O campo Local deve ser preenchido", Toast.LENGTH_LONG).show();
            return null;
        }
        String descript = ((EditText) main.findViewById(R.id.etDescript)).getText().toString();

        return new Patrimony(id, name, reponsible, location, descript, Storage.getInstance().getLoggedUser());
    }


    public void btRegisterQrClick(View v){
        Intent intent = new Intent(getActivity(), CameraActivity.class);
        startActivityForResult(intent, MainActivity.REGISTER_CAMERA_RESULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == MainActivity.REGISTER_CAMERA_RESULT){
            if(resultCode == CameraActivity.RESULT_CAMERA_OK){
                String value = data.getStringExtra(CameraActivity.RESULT_CAMERA_KEY);
                ((TextView) getActivity().findViewById(R.id.etId)).setText(value);
            }else if(resultCode == CameraActivity.RESULT_CAMERA_FAIL){
                Toast.makeText(getActivity(), "Barcode detector não instalado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkExistingId(int id){
        return Storage.getInstance().getPatrimonyById(id) != null;
    }

    @Override
    public void serviceCallback(String result, int code) {
        if(code == WebService.REGISTER_PATRIMONY){
            int id = Integer.parseInt(((EditText) getActivity().findViewById(R.id.etId)).getText().toString());
            if(checkExistingId(id)){
                Toast.makeText(getActivity(), "O identificador do patrimônio já existe", Toast.LENGTH_SHORT).show();
            }else {
                Storage.getInstance().patrimonies.add(buildPatrimony());
                Toast.makeText(getActivity(), "O patrimônio foi cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                clearFields();
            }
        }
        progressDialog.dismiss();
    }

    @Override
    public void onClick(View view){
        System.out.println("clicou");
        if(view.getId() == R.id.btRegisterPatrimony){
            btRegisterPatrimonyClick(view);
        }else if(view.getId() == R.id.btRegisterQr){
            btRegisterQrClick(view);
        }
    }
}
