package app.shi.com.sagy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.shi.com.sagy.model.RecieveScheme;

/**
 * Created by Abhinity on 3/26/18.
 */

/*class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

    CardView schemeid;
    private itemClickListner item;


    public RecyclerViewHolder(View itemView) {
        super(itemView);
        schemeid = (CardView)itemView.findViewById(R.id.schemeid);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    private void setItemClickListner(itemClickListner itemClickListner)
    {
        this.item = item;
    }
    @Override
    public void onClick(View view) {
        item.onClick(view,getAdapterPosition(),false);
    }
    @Override
    public boolean onLongClick(View view) {
        item.onClick(view,getAdapterPosition(),true);
        return true;
    }
}*/

public class SchemeAdapterClass extends RecyclerView.Adapter<SchemeAdapterClass.ViewHolder>{

    private List<RecieveScheme> recievelist;
    private Context mctx;
    private OnSchemeItemClick listener;

    public SchemeAdapterClass(List<RecieveScheme> recievelist,Context mctx,OnSchemeItemClick listener)
    {
        this.recievelist = recievelist;
        this.mctx = mctx;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scheme1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindviewholder(recievelist.get(position),position,listener);
    }

    @Override
    public int getItemCount() {
        return recievelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.scheme_name);

        }

        public void bindviewholder(final RecieveScheme recieveScheme, int position, final OnSchemeItemClick listener) {

            name.setText(String.valueOf(recieveScheme.getName()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnSchemeItemClick(recieveScheme);
                }
            });
        }
    }
}
