package app.shi.com.sagy.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.shi.com.sagy.R;

/**
 * Created by Rujuu on 4/13/2018.
 */

public class ProblemStatementAdapterClass extends RecyclerView.Adapter<ProblemStatementAdapterClass.ViewHolder>{

    private List<app.shi.com.sagy.model.recieveProblemStatement> recievelist;
    private Context mCtx;

    public ProblemStatementAdapterClass(List<app.shi.com.sagy.model.recieveProblemStatement> recievelist, Context mCtx)
    {
        this.recievelist = recievelist;
        this.mCtx = mCtx;
    }

    @Override
    public ProblemStatementAdapterClass.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.problemstatement, parent, false);
        return new ProblemStatementAdapterClass.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindviewholder(recievelist.get(position), position);
    }

    @Override
    public int getItemCount() {
        return recievelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView problem_statement_TV;
        TextView p_village_TV;
        TextView Field_TV;
        TextView contact_details_TV;

        public ViewHolder(View itemView) {
            super(itemView);
            problem_statement_TV = (TextView) itemView.findViewById(R.id.problem_statement_TV);
            p_village_TV = (TextView) itemView.findViewById(R.id.p_village_TV);
            Field_TV = (TextView) itemView.findViewById(R.id.Field_TV);
            contact_details_TV = (TextView) itemView.findViewById(R.id.contact_details_TV);

        }

        public void bindviewholder(app.shi.com.sagy.model.recieveProblemStatement recieve, int position) {

           /* Date date=new Date(Long.parseLong(recieve.gettimeleaderboard())*1000);
            SimpleDateFormat dateformat = new SimpleDateFormat("MMM dd yyyy , HH:mm");*/
            problem_statement_TV.setText(recieve.getProblem_statement_TV());
            p_village_TV.setText(recieve.getP_village_TV());
            Field_TV.setText(recieve.getField_TV());
            contact_details_TV.setText(recieve.getContact_details_TV());

            /*Log.e("DATE : ",dateformat.format(date));*/
        }
    }
}

