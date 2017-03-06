package fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Camera;
import android.icu.text.NumberFormat;
import android.nfc.FormatException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheus.projeto_final.CameraActivity;
import com.example.matheus.projeto_final.MainActivity;
import com.example.matheus.projeto_final.R;

import java.util.ArrayList;

import entidades.Patrimony;
import entidades.Storage;
import patrimonyList.PatrimonyAdapter;
import webService.ServiceCallback;
import webService.WebService;


public class SearchFragment extends Fragment implements View.OnClickListener, ServiceCallback{



    private ProgressDialog progressDialog;
    private PatrimonyAdapter patrimonyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_tab,container,false);
        initRecycleView(view);
        ((Button) view.findViewById(R.id.btSearch)).setOnClickListener(this);
        ((ImageButton) view.findViewById(R.id.btQr)).setOnClickListener(this);
        return view;
    }


    public void initRecycleView(View view){
        MainActivity main = (MainActivity) getActivity();
        patrimonyAdapter = new PatrimonyAdapter(main.getBaseContext());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setAdapter(patrimonyAdapter);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
    }

    public void btSearchClick(View view){
        String option = ((Spinner)getActivity().findViewById(R.id.spinner)).getSelectedItem().toString();
        if(option.equals("Id")) {
            try {
                int id = Integer.parseInt(((EditText) getActivity().findViewById(R.id.etSearch)).getText().toString());
                progressDialog = ProgressDialog.show(getActivity(), "", "Consultando dados no servidor...");
                progressDialog.show();
                WebService.getInstance().searchByID(id, this);
            } catch (NumberFormatException ex) {
                Toast.makeText(getActivity(), "O id deve ser um número inteiro", Toast.LENGTH_SHORT).show();
            }
        }else{
            if(option.equals("Responsável")){
                String responsible = ((EditText) getActivity().findViewById(R.id.etSearch)).getText().toString();
                progressDialog = ProgressDialog.show(getActivity(), "", "Consultando dados no servidor...");
                progressDialog.show();
                WebService.getInstance().searchByResponsible(responsible, this);
            }else if(option.equals("Local")){
                String location = ((EditText) getActivity().findViewById(R.id.etSearch)).getText().toString();
                progressDialog = ProgressDialog.show(getActivity(), "", "Consultando dados no servidor...");
                progressDialog.show();
                WebService.getInstance().searchByResponsible(location, this);
            }
        }
    }

    public void btQrClick(View view){
        Intent intent = new Intent(getActivity(), CameraActivity.class);
        startActivityForResult(intent, MainActivity.SEARCH_CAMERA_RESULT);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btSearch){
            btSearchClick(view);
        }else if(view.getId() == R.id.btQr){
            btQrClick(view);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == MainActivity.SEARCH_CAMERA_RESULT){
            if(resultCode == CameraActivity.RESULT_CAMERA_OK){
                String value = data.getStringExtra(CameraActivity.RESULT_CAMERA_KEY);
                ((Spinner) getActivity().findViewById(R.id.spinner)).setSelection(0);
                ((TextView) getActivity().findViewById(R.id.etSearch)).setText(value);
                ((Button) getActivity().findViewById(R.id.btSearch)).performClick();
            }else if(resultCode == CameraActivity.RESULT_CAMERA_FAIL){
                Toast.makeText(getActivity(), "Barcode detector não instalado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void serviceCallback(String result, int code) {
        if(code == WebService.SEARCH_PATRIMONY){
            String option = ((Spinner)getActivity().findViewById(R.id.spinner)).getSelectedItem().toString();
            Patrimony patrimony = null;
            int id = -1;
            if(option.equals("Id")){
                id = Integer.parseInt(((EditText)getActivity().findViewById(R.id.etSearch)).getText().toString());
                patrimony = Storage.getInstance().getPatrimonyById(id);
                if(patrimony != null){
                    patrimonyAdapter.setNewResult(Storage.getInstance().getPatrimonyById(id));
                }else{
                    Toast.makeText(getActivity(), "Patrimônio não encontrado", Toast.LENGTH_SHORT).show();
                    patrimonyAdapter.clear();
                }
            }else{
                ArrayList<Patrimony> patrimonies = new ArrayList<>();
                if(option.equals("Responsável")){
                    String responsible = ((EditText)getActivity().findViewById(R.id.etSearch)).getText().toString();
                    patrimonies = Storage.getInstance().getPatrimonyByResponsible(responsible);
                }else if(option.equals("Local")){
                    String location = ((EditText)getActivity().findViewById(R.id.etSearch)).getText().toString();
                    patrimonies = Storage.getInstance().getPatrimonyByLocation(location);
                }
                if(patrimonies.isEmpty() == false){
                    patrimonyAdapter.setNewResult(patrimonies);
                }else{
                    Toast.makeText(getActivity(), "Patrimônio não encontrado", Toast.LENGTH_SHORT).show();
                    patrimonyAdapter.clear();
                }
            }
        }
        progressDialog.dismiss();
    }
}