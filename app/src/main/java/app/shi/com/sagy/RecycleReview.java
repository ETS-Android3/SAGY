package app.shi.com.sagy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

public class RecycleReview extends AppCompatActivity {
    private RecyclerView mrecyclerView;
    private RecyclerView.Adapter madapter;
    private RecyclerView.LayoutManager mlayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent= new Intent(RecycleReview.this,DataAnalysis.class);
        startActivity(intent);
//        setContentView(R.layout.activity_recycle_review);
//        mrecyclerView =(RecyclerView) findViewById(R.id.my_recycler_view);
//        mrecyclerView.setHasFixedSize(true);
//        mlayoutManager = new LinearLayoutManager(this);
//        mrecyclerView.setLayoutManager(mlayoutManager);
//        String[] name ={"sourabh","Ruju","yash","nidhi","abhinity","hiral"};
//        madapter = new MyAdaptor(name);
//        mrecyclerView.setAdapter(madapter);

    }
}
