package com.bob.lottery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import com.bob.lottery.Manager.BottomManager;
import com.bob.lottery.Manager.MiddleManager;
import com.bob.lottery.Manager.TitleManager;
import com.bob.lottery.util.PromptManager;
import com.bob.lottery.view.Hall;

public class MainActivity extends AppCompatActivity {
    //中间容器
    private RelativeLayout middleContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        TitleManager.getInstance().init(this);
        TitleManager.getInstance().showUnLoginTitle();
        BottomManager.getInstance().init(this);
        BottomManager.getInstance().showCommonBottom();

        middleContainer=(RelativeLayout)findViewById(R.id.ii_middle);
        MiddleManager.getInstance().setMiddleContainer(middleContainer);
        //3.建立关系，观察者添加到容器
        MiddleManager.getInstance().addObserver(TitleManager.getInstance());
        MiddleManager.getInstance().addObserver(BottomManager.getInstance());


        MiddleManager.getInstance().changeUI(Hall.class);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
           boolean result=MiddleManager.getInstance().goBack();
            //返回失败
            if (!result){
                //Toast.makeText(MainActivity.this,"退出？",Toast.LENGTH_SHORT).show();
                PromptManager.showExitSystem(this);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
