package app.shi.com.sagy.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.shi.com.sagy.MyAdaptor;
import app.shi.com.sagy.R;

/**
 * Created by Rujuu on 6/22/2018.
 */

public class no_adapter extends RecyclerView.Adapter<no_adapter.ViewHolder> {
    private String[] mDataset;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView name;
        TextView vilname;
        ImageView icon;
        public TextView mTextView;


        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.no_name_TV);
            name = (TextView) v.findViewById(R.id.no_village_name_TV);
            icon=(ImageView) v.findViewById(R.id.no_iv);
            mTextView = (TextView) v.findViewById(R.id.list_Text);
        }
    }

    @Override
    public no_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        TextView v = (TextView)
        View v;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_nodaloff, parent, false);
        no_adapter.ViewHolder vh = new no_adapter.ViewHolder(v);
        return vh;
    }

    public no_adapter(String[] mDataset) {
        this.mDataset = mDataset;
    }


    @Override
    public void onBindViewHolder(no_adapter.ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
















}
