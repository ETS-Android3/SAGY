package app.shi.com.sagy;

/**
 * Created by Abhinity on 3/24/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterWallOfFame extends RecyclerView.Adapter<AdapterWallOfFame.ViewHolder> {
    private final Context context;
    List<wall_of_fame_getset> wall_list;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mpImageView;
        TextView mpname;
        TextView mpvillage;
        RatingBar mpscore;
        public ViewHolder(View v) {
            super(v);
            mpImageView = (ImageView) v.findViewById(R.id.mp_img);
            mpname = (TextView) v.findViewById(R.id.mp_name);
            mpvillage = (TextView) v.findViewById(R.id.mp_village);
            mpscore = (RatingBar) v.findViewById(R.id.mp_score);
        }
    }


    public AdapterWallOfFame(List<wall_of_fame_getset> wa, Context context) {
        this.wall_list = wa;
        this.context = context;
    }

    @Override
    public AdapterWallOfFame.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.walloffame, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterWallOfFame.ViewHolder holder, int position) {
        wall_of_fame_getset wall_of_fame_getset = wall_list.get(position);
//        if(wall_of_fame_getset.getMp_img()!=""){
//            String imageUri = "http://phpstack-165541-479108.cloudwaysapps.com/uploads"+wall_of_fame_getset.getMp_img();
//            Picasso.get()
//                    .load(imageUri)
//                    .into(holder.mpImageView);
//        }
//        else{
        Log.d("getnder",""+wall_of_fame_getset.getGender());
        if(wall_of_fame_getset.getGender().equals("male")) {
            holder.mpImageView.setImageResource(R.drawable.ic_malei);
        }
        else if(wall_of_fame_getset.getGender().equals("female")){
            holder.mpImageView.setImageResource(R.drawable.ic_femalei);
        }
        else if(wall_of_fame_getset.getGender().equals("village")){
            holder.mpImageView.setImageResource(R.drawable.ic_sagy);
        }else {
            holder.mpImageView.setImageResource(R.drawable.ic_sagy);
        }

//        }
        holder.mpname.setText(wall_of_fame_getset.getName());
        holder.mpvillage.setText(wall_of_fame_getset.getVname());
        holder.mpscore.setRating(Float.parseFloat(wall_of_fame_getset.getScore().toString()));
    }

    @Override
    public int getItemCount() {
        return wall_list.size();
    }
}
