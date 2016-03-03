package com.bob.lottery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bob.lottery.Manager.BottomManager;
import com.bob.lottery.Manager.TitleManager;

public class MainActivity extends AppCompatActivity {

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

    }

}
