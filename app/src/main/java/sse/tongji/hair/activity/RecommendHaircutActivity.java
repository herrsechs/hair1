package sse.tongji.hair.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import sse.tongji.hair.R;
import sse.tongji.hair.adapter.PersonImageAdapter;
import sse.tongji.hair.adapter.RecommendImageAdapter;

/**
 * Created by LLLLLyj on 2015/9/7.
 */
public class RecommendHaircutActivity extends AppCompatActivity {
    //public ImageView customerImage;
    public Button clearButton;
    public Button okButton;
    public Button refreshButton;
    public ImageView mergedImage;
    public RecyclerView recommendImageRecyclerView;
    public RecyclerView personImageRecyclerView;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_activity);

        //customerImage = (ImageView)findViewById(R.id.customerImage);
        mergedImage = (ImageView)findViewById(R.id.mergedImage);
        recommendImageRecyclerView = (RecyclerView)findViewById(R.id.recommendImageRecyclerView);
        personImageRecyclerView = (RecyclerView)findViewById(R.id.personImage);
        clearButton = (Button)findViewById(R.id.recommended_clear_btn);
        okButton = (Button)findViewById(R.id.recommended_ok_btn);
        refreshButton = (Button)findViewById(R.id.recommended_refresh_btn);

        recommendImageRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recommendImageRecyclerView.setAdapter(new RecommendImageAdapter(this));

        personImageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        personImageRecyclerView.setAdapter(new PersonImageAdapter(this));

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearImage();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mergeImage();
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshImage();
            }
        });

        toolbar = (Toolbar)findViewById(R.id.tl_custom);
        setSupportActionBar(toolbar);
    }

    protected void clearImage(){
        //mergedImage.setImageURI(R.drawable.question);
        mergedImage.setImageResource(R.drawable.question);
    }

    protected void mergeImage(){

    }

    protected void refreshImage(){

    }
}
