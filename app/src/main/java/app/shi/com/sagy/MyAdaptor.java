package app.shi.com.sagy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.ViewHolder> {
    private String[] mDataset;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        TextView v = (TextView)
        View v;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mtextlayout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public MyAdaptor(String[] mDataset) {
        this.mDataset = mDataset;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.list_Text);
        }
    }
}