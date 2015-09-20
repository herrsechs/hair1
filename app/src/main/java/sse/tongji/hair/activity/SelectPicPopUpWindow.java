package sse.tongji.hair.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import sse.tongji.hair.R;

/**
 * Created by LLLLLyj on 2015/9/20.
 */
public class SelectPicPopUpWindow extends PopupWindow {
    private Button pic_camera;
    private Button pic_gallery;
    private Button cancel_btn;
    private View menuView;

    public SelectPicPopUpWindow(Activity context, View.OnClickListener itemOnClick){
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        menuView = inflater.inflate(R.layout.bottom_popup, null);

        pic_camera = (Button)menuView.findViewById(R.id.popup_pick_from_photo);
        pic_gallery = (Button)menuView.findViewById(R.id.popup_pick_from_gallery);
        cancel_btn = (Button)menuView.findViewById(R.id.popup_pick_dismiss);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        pic_gallery.setOnClickListener(itemOnClick);
        pic_camera.setOnClickListener(itemOnClick);

        this.setContentView(menuView);
        this.setAnimationStyle(R.style.Animation_AppCompat_DropDownUp);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);

        menuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = menuView.findViewById(R.id.popup_window).getTop();
                int y = (int) motionEvent.getY();
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(y < height)
                        dismiss();
                }
                return false;
            }
        });
    }
}
