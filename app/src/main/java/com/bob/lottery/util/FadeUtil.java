package com.bob.lottery.util;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by Administrator on 2016/3/4.
 */
//淡入淡出
public class FadeUtil {
    private static Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            View view= (View) msg.obj;
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
    };


    //当前正在展示的淡出
    public static void fadeOut(final View view,long duration){
        AlphaAnimation alphaAnimation=new AlphaAnimation(1,0);
        alphaAnimation.setDuration(duration);
        //执行完后，删除view
        //监听
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                ViewGroup parent = (ViewGroup) view.getParent();
//                parent.removeView(view);
                //2.3绕过异常
                Message msg=Message.obtain();
                msg.obj=view;
                handler.sendMessage(msg);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(alphaAnimation);
    }

    //执行过程中，第二界面处于等待
    //第二界面淡入
    public static void fadeIn(View view,long delay,long duration){
        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        //延时时间
        alphaAnimation.setStartOffset(delay);
        alphaAnimation.setDuration(duration);
        view.startAnimation(alphaAnimation);
    }

}
