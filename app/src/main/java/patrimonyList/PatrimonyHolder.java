package patrimonyList;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.matheus.projeto_final.R;
import com.google.android.gms.vision.text.Line;

public class PatrimonyHolder extends RecyclerView.ViewHolder {

    private final TextView name;
    private final TextView description;
    private final TextView responsible;
    private final TextView location;
    private final LinearLayout container;

    public PatrimonyHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.tvNamePatrimony);
        description = (TextView) view.findViewById(R.id.tvDescriptionPatrimony);
        responsible = (TextView) view.findViewById(R.id.tvReponsiblePatrimony);
        location = (TextView) view.findViewById(R.id.tvLocationPatrimony);
        container = (LinearLayout) view.findViewById(R.id.patrimonyContainer);
    }

    public LinearLayout getContainer(){
        return this.container;
    }
    public TextView getTextName(){
        return this.name;
    }

    public TextView getTextDescription(){
        return this.description;
    }

    public TextView getTextResponsible(){
        return this.responsible;
    }

    public TextView getTextLocation(){
        return this.location;
    }

}
