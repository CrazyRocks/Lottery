package com.bob.lottery.Manager;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bob.lottery.R;
import com.bob.lottery.util.ConstantValue;
import com.bob.lottery.view.SecondUI;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.BasicEofSensorWatcher;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2016/3/3.
 */

//管理标题容器
public class TitleManager implements Observer{
    //拿到容器
    private RelativeLayout commonContainer;
    private RelativeLayout loginContainer;
    private RelativeLayout unLoginContainer;

    private ImageView goback;// 返回
    private ImageView help;// 帮助
    private ImageView login;// 登录

    private TextView titleContent;// 标题内容
    private TextView userInfo;// 用户信息


    private static TitleManager instance = new TitleManager();

    private TitleManager() {

    }

    public static TitleManager getInstance() {
        return instance;
    }


    //初始化
    public void init(Activity activity){
        commonContainer = (RelativeLayout) activity
                .findViewById(R.id.ii_common_container);
        unLoginContainer = (RelativeLayout) activity
                .findViewById(R.id.ii_unlogin_title);
        loginContainer = (RelativeLayout) activity
                .findViewById(R.id.ii_login_title);

        goback = (ImageView) activity.findViewById(R.id.ii_title_goback);
        help = (ImageView) activity.findViewById(R.id.ii_title_help);
        login = (ImageView) activity.findViewById(R.id.ii_title_login);

        setListener();
    }

    //监听
    private void setListener() {
        goback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("返回键");

            }
        });
        help.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("help");

            }
        });
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("login");

				//SecondUI secondUI = new SecondUI(MiddleManager.getInstance().getContext());
                MiddleManager.getInstance().changeUI(SecondUI.class);//changeUI需要修改，不能传递对象，但是明确目标
            }
        });

    }


    private void initTitle(){
        commonContainer.setVisibility(View.GONE);
        loginContainer.setVisibility(View.GONE);
        unLoginContainer.setVisibility(View.GONE);
    }

    //显示和隐藏
    //显示通用
    public void showCommonTitle(){
        initTitle();
        commonContainer.setVisibility(View.VISIBLE);
    }

    //显示未登录
    public void showUnLoginTitle(){
        initTitle();
        unLoginContainer.setVisibility(View.VISIBLE);
    }

    //显示登陆
    public void showLoginTitle(){
        initTitle();
        loginContainer.setVisibility(View.VISIBLE);
    }


    public void changeTitle(String title) {
        titleContent.setText(title);
    }

    @Override
    public void update(Observable observable, Object data) {
        //不空，是数字
        if (data!=null && StringUtils.isNumeric(data.toString())){
            int id=Integer.parseInt(data.toString());
                switch (id){
                    case ConstantValue.VIEW_FISRT:
                    case ConstantValue.VIEW_HALL:
                        showUnLoginTitle();
                        break;
                    case ConstantValue.VIEW_SECOND:
                        showCommonTitle();
                        break;
                }
        }
    }
}
