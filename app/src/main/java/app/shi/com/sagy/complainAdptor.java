package app.shi.com.sagy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by NIDHI on 27-03-2018.
 */

public class complainAdptor extends RecyclerView.Adapter<complainAdptor.ViewHolder> {
    private final Context context;
    List<Complaint> cmplist;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView time;
        ImageView icon;
        TextView desc;
        TextView id;
        LinearLayout linearLayout;
        public ViewHolder(View v) {
            super(v);
            time = (TextView) v.findViewById(R.id.time);
            icon=(ImageView) v.findViewById(R.id.img);
            desc=(TextView) v.findViewById(R.id.desc);
            id =(TextView) v.findViewById(R.id.status);
            linearLayout = (LinearLayout)v.findViewById(R.id.linear_complaint);

        }
    }

    public complainAdptor(List<Complaint> complaintList, Context context) {
        this.cmplist=complaintList;
        this.context=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Complaint complaint= cmplist.get(position);
        holder.time.setText(complaint.getTime());
        holder.id.setText(complaint.getStatus());
        holder.desc.setText(complaint.getDescription());
        if(complaint.getUid()==0) {
            holder.linearLayout.setBackgroundResource(R.color.colorlight);
        }else if (complaint.getPriority()==1){
            holder.linearLayout.setBackgroundResource(R.color.colorAccent);
        }
        else {
            holder.linearLayout.setBackgroundResource(R.color.colorPrimary);
        }
        Picasso.get()
                .load(context.getString(R.string.imgurl)+complaint.getImage())
                .into(holder.icon);
    }

    @Override

        public int getItemCount() {
            return cmplist.size();
        }
    }

