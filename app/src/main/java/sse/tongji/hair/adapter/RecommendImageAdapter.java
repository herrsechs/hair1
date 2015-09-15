package sse.tongji.hair.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sse.tongji.hair.R;

/**
 * Created by LLLLLyj on 2015/9/8.
 */
public class RecommendImageAdapter extends RecyclerView.Adapter<RecommendImageAdapter.RecommendImageViewHolder>  {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final int  ITEM_COUNT = 5;
    private int[] haircuts = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f };

    public RecommendImageAdapter(Context context){
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    public int getItemCount(){
        return this.ITEM_COUNT;
    }

    public void onBindViewHolder(RecommendImageViewHolder holder, int position){
        holder.recommended_image.setImageResource(haircuts[position]);
    }

    public RecommendImageViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        return new RecommendImageViewHolder(mLayoutInflater.inflate(R.layout.recommended_image, parent, false));
    }

    public static class RecommendImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView recommended_image;

        public RecommendImageViewHolder(View view){
            super(view);
            this.recommended_image = (ImageView)view.findViewById(R.id.recommended_image);
            this.recommended_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chooseImage();
                }
            });
        }

        protected void chooseImage(){

        }
    }


}
