package sse.tongji.hair.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import sse.tongji.hair.R;

/**
 * Created by LLLLLyj on 2015/9/10.
 */
public class PersonImageAdapter extends RecyclerView.Adapter<PersonImageAdapter.PersonImageViewHolder> {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    public PersonImageAdapter(Context context){
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public PersonImageViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        return new PersonImageViewHolder(mLayoutInflater.inflate(R.layout.recommended_image, parent, false));
    }

    @Override
    public int getItemCount() { return 1;}

    @Override
    public void onBindViewHolder(PersonImageViewHolder holder, int position){

    }
    public static class PersonImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView pImage;

        public PersonImageViewHolder(View view){
            super(view);
            pImage = (ImageView)view.findViewById(R.id.recommended_image);
            pImage.setImageResource(R.drawable.zhang);
        }
    }
}
