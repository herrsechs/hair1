package sse.tongji.hair.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;

import sse.tongji.hair.R;
import sse.tongji.hair.activity.CommentActivity;
import sse.tongji.hair.httpclient.ImageLoaderUtil;

/**
 * Created by lenovo on 2015/8/28.
 */
public class HaircutItemAdapter  extends RecyclerView.Adapter<HaircutItemAdapter.HaircutItemViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private String[] dates;
    private String[] shops;
    private String[] authors;
    //private Hashtable haircutItemsTable = new Hashtable();
    private ArrayList<HaircutItemContent> haircutItemLists = new ArrayList<>();
    private ImageLoaderUtil imgLoadUtil;
    private int[] haircuts = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f };

    public HaircutItemAdapter(Context context){
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.dates = context.getResources().getStringArray(R.array.haircut_dates);
        this.shops = context.getResources().getStringArray(R.array.haircut_shops);
        this.authors = context.getResources().getStringArray(R.array.haircut_authors);
        this.imgLoadUtil = new ImageLoaderUtil(mContext);
        int[] imageIDs = {1,2,3,4,5,6};

        for(int i = 0; i < haircuts.length; i++){
            HaircutItemContent hic = new HaircutItemContent();
            hic.date = this.dates[i];
            hic.author = this.authors[i];
            hic.shop = this.shops[i];
            hic.haircuts = this.haircuts[i];
            //haircutItemsTable.put(String.valueOf(i), hic);
            haircutItemLists.add(hic);
        }

        //imgLoadUtil.loadImage(imageIDs, "male", "short");
    }

    public int getItemCount(){ return this.haircutItemLists.size(); }

    public void onBindViewHolder(HaircutItemViewHolder holder, int position){
        HaircutItemContent hic = haircutItemLists.get(position);
        /*
        holder.dateText.setText(this.dates[position]);
        holder.authorText.setText(this.authors[position]);
        holder.shopText.setText(this.shops[position]);
        //holder.haircutImage.setImageBitmap(this.imgLoadUtil.getBitmapFromMemCache(String.valueOf(position)));
        holder.haircutImage.setImageResource(haircuts[position]);
        */
        if(hic != null) {
            holder.dateText.setText(hic.date);
            holder.authorText.setText(hic.author);
            holder.shopText.setText(hic.shop);
            holder.haircutImage.setImageResource(hic.haircuts);
        }
    }

    public HaircutItemViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        //View view = mLayoutInflater.inflate(R.layout.haircut_item, parent, false);
        //HaircutItemViewHolder ad = new HaircutItemViewHolder(view);
        //view.setOnClickListener(view);
        return new HaircutItemViewHolder(mLayoutInflater.inflate(R.layout.haircut_item, parent, false));
    }

    public void deletaData(int pos){
        notifyItemRemoved(pos);
        //haircutItemsTable.remove(String.valueOf(pos));
        haircutItemLists.remove(pos);
        Log.d("Debug", "Now we have " + String.valueOf(haircutItemLists.size()) + " items");
        Log.d("Debug", haircutItemLists.toString());
    }

    public static class HaircutItemViewHolder extends RecyclerView.ViewHolder{
        private TextView dateText;
        private TextView authorText;
        private TextView shopText;
        private ImageButton upButton;
        private ImageButton downButton;
        private ImageButton commentButton;
        private ImageButton launchButton;
        private ImageButton roomButton;
        private ImageView haircutImage;

        public  HaircutItemViewHolder(View view){
            super(view);
            this.authorText = (TextView)view.findViewById(R.id.haircut_author);
            this.dateText = (TextView)view.findViewById(R.id.haircut_date);
            this.shopText = (TextView)view.findViewById(R.id.haircut_shop);
            this.upButton = (ImageButton)view.findViewById(R.id.haircut_up_button);
            this.downButton = (ImageButton)view.findViewById(R.id.haircut_down_button);
            this.commentButton = (ImageButton)view.findViewById(R.id.haircut_comment_button);
            this.launchButton = (ImageButton)view.findViewById(R.id.haircut_comment_button);
            this.roomButton = (ImageButton)view.findViewById(R.id.haircut_room_button);
            this.haircutImage = (ImageView)view.findViewById(R.id.haircut_image);

            this.commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(view.getContext(), CommentActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    public class HaircutItemContent{
        public String date = "";
        public String author = "";
        public String shop = "";
        public int haircuts = 0;
        public HaircutItemContent(){
            super();
        }
    }
}
