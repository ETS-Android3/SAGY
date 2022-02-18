package app.shi.com.sagy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by NIDHI on 27-03-2018.
 */

public class VillageAdapter extends RecyclerView.Adapter<VillageAdapter.ViewHolder> {
    private String[] mDataset;
    private int[] img;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView title;
        ImageView icon;
        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tv_vil);
            icon=(ImageView) v.findViewById(R.id.iv_vil);
        }
    }

    public VillageAdapter(String[] mData,int[] im) {
        this.mDataset = mData;
        this.img=im;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlevillage, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(mDataset[position]);
        holder.icon.setImageResource(img[position]);
    }

    @Override

        public int getItemCount() {
            return mDataset.length;
        }
    }

