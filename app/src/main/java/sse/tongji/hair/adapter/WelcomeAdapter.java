package sse.tongji.hair.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sse.tongji.hair.R;
import sse.tongji.hair.activity.PersonCenterActivity;
import sse.tongji.hair.activity.RecommendHaircutActivity;

/**
 * Created by lenovo on 2015/8/28.
 */
public class WelcomeAdapter extends RecyclerView.Adapter<WelcomeAdapter.WelcomeViewHolder> {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    public WelcomeAdapter(Context context){
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);

    }

    public Context getmContext(){
        return this.mContext;
    }

    public WelcomeViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        return new WelcomeViewHolder(mLayoutInflater.inflate(R.layout.welcom, parent, false));
    }

    @Override
    public int getItemCount() { return 1;}

    @Override
    public void onBindViewHolder(WelcomeViewHolder holder, int position){

    }

    public static class WelcomeViewHolder extends RecyclerView.ViewHolder {
        private Button yesButton;
        private Button noButton;

        public WelcomeViewHolder(View view){
            super(view);
            yesButton = (Button)view.findViewById(R.id.welcome_yes_button);
            noButton = (Button)view.findViewById(R.id.welcome_no_button);

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(view.getContext(), RecommendHaircutActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
