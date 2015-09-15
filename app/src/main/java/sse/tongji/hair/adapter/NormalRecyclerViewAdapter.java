package sse.tongji.hair.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sse.tongji.hair.R;

/**
 * Created by lenovo on 2015/8/26.
 */
public class NormalRecyclerViewAdapter extends RecyclerView.Adapter<NormalRecyclerViewAdapter.nViewHolder>{
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private String[] mTitles;

    public NormalRecyclerViewAdapter(Context context){
        mTitles = context.getResources().getStringArray(R.array.titles);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public nViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new nViewHolder(mLayoutInflater.inflate(R.layout.item_text, parent, false));
    }

    public void onBindViewHolder(nViewHolder holder, int position){
        holder.mTextView.setText(mTitles[position]);
    }

    public int getItemCount(){
        return mTitles == null ? 0 : mTitles.length;
    }

    public static class nViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;

        nViewHolder(View view){
            super(view);
            this.mTextView = (TextView)view.findViewById(R.id.text_view);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                }
            });
        }
    }
}
