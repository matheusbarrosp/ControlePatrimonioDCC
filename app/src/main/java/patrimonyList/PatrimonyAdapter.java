package patrimonyList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.matheus.projeto_final.EditActivity;
import com.example.matheus.projeto_final.R;

import java.util.ArrayList;

import entidades.Patrimony;
import fragments.RegisterFragment;

/**
 * Created by Matheus on 31/01/2017.
 */

public class PatrimonyAdapter extends RecyclerView.Adapter {

    private ArrayList<Patrimony> patrimonies;
    private Context context;


    public PatrimonyAdapter(Context context) {
        this.patrimonies = new ArrayList<>();
        this.context = context;
    }

    public void clear(){
        this.patrimonies.clear();
        this.notifyDataSetChanged();
    }

    public PatrimonyAdapter(ArrayList<Patrimony> patrimonies, Context context) {
        this.patrimonies = patrimonies;
        this.context = context;
    }

    public void setNewResult(ArrayList<Patrimony> patrimonies){
        this.patrimonies.clear();
        for(Patrimony p : patrimonies){
            this.patrimonies.add(p);
        }
        this.notifyDataSetChanged();
    }

    public void setNewResult(Patrimony patrimony){
        this.patrimonies.clear();
        this.patrimonies.add(patrimony);
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_patrimony, parent, false);
        PatrimonyHolder holder = new PatrimonyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        PatrimonyHolder patrimonyHolder = (PatrimonyHolder) viewHolder;
        final Patrimony patrimony = patrimonies.get(position);
        patrimonyHolder.getTextDescription().setText(patrimony.getDescription());
        patrimonyHolder.getTextLocation().setText(patrimony.getLocation());
        patrimonyHolder.getTextName().setText(patrimony.getName());
        patrimonyHolder.getTextResponsible().setText(patrimony.getResponsible());
        patrimonyHolder.getContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(EditActivity.ID_PATRIMONY, patrimony.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patrimonies.size();
    }
}
