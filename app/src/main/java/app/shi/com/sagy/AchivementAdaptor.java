package app.shi.com.sagy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lespinside.simplepanorama.view.SphericalView;
import com.squareup.picasso.Picasso;

import java.util.List;

//import com.squareup.picasso.Picasso;

/**
 * Created by NIDHI on 27-03-2018.
 */

public class AchivementAdaptor extends RecyclerView.Adapter<AchivementAdaptor.ViewHolder> {
    private final Context context;
    List<Achivement_fields> af;
    onAchivementItemClick onAchivementItemClick;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView ac_img;
        TextView ac_desc;

        public ViewHolder(View v) {
            super(v);
            ac_img = (ImageView) v.findViewById(R.id.ach_img);
            ac_desc = (TextView) v.findViewById(R.id.ach_desc);
        }

        public  void onbindViewHolder(Context context, final Achivement_fields achivement_fields, int pos, final onAchivementItemClick onAchivementItemClick){
            if (achivement_fields.getAc_img() != "") {
                Picasso.get()
                        .load(context.getString(R.string.imgurl) + achivement_fields.getAc_img())
                        .into(ac_img);
            } else {
                ac_img.setImageResource(R.drawable.company_achievement);
            }
            ac_desc.setText(achivement_fields.getAc_desc());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAchivementItemClick.onAchiveItemClick(achivement_fields);
                }
            });
        }

    }

    public AchivementAdaptor(List<Achivement_fields> achList, Context context, onAchivementItemClick onAchivementItemClick) {
        this.af = achList;
        this.context = context;
        this.onAchivementItemClick=onAchivementItemClick;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.achive_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onbindViewHolder(context,af.get(position),position,onAchivementItemClick);
    }

    @Override
    public int getItemCount() {
        return af.size();
    }


}

