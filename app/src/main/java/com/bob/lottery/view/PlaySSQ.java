package com.bob.lottery.view;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;

import com.bob.lottery.Manager.TitleManager;
import com.bob.lottery.R;
import com.bob.lottery.util.ConstantValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/5.
 */

//双色球选号界面
public class PlaySSQ extends BaseUI {
    //通用三步

    //1.标题
    // 判断购彩大厅是否获取到期次信息
    // 如果获取到：拼装标题
    // 否则默认的标题展示

    //2.填充选号容器
    //3.选号：单击+机选红蓝球
    //4.手机摇晃
    //5.提示信息

    // 机选
    private Button randomRed;
    private Button randomBlue;
    // 选号容器
    private MyGridView redContainer;
    private MyGridView blueContainer;

    private PoolAdapter redAdapter;
    private PoolAdapter blueAdapter;

    private List<Integer> redNums;
    private List<Integer> blueNums;
    private SensorManager manager;



    public PlaySSQ(Context context) {
        super(context);
    }

    @Override
    public void init() {
        showInMiddle= (ViewGroup) View.inflate(context, R.layout.il_playssq1,null);
        redContainer = (MyGridView) findViewById(R.id.ii_ssq_red_number_container);
        blueContainer = (MyGridView) findViewById(R.id.ii_ssq_blue_number_container);
        randomRed = (Button) findViewById(R.id.ii_ssq_random_red);
        randomBlue = (Button) findViewById(R.id.ii_ssq_random_blue);

        redNums = new ArrayList<Integer>();
        blueNums = new ArrayList<Integer>();

        redAdapter = new PoolAdapter(context, 33,redNums,R.drawable.id_redball);
        blueAdapter = new PoolAdapter(context, 16,blueNums,R.drawable.id_blueball);


        redContainer.setAdapter(redAdapter);
        blueContainer.setAdapter(blueAdapter);

        manager=(SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public void setListener() {
        randomRed.setOnClickListener(this);
        randomBlue.setOnClickListener(this);

        redContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 判断当前点击的item是否被选中了
                if (!redNums.contains(position + 1)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    view.setBackgroundResource(R.drawable.id_redball);
                    // 摇晃的动画
                    view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.ia_ball_shake));
                    redNums.add(position + 1);
                } else {
                    // 被选中
                    // 还原背景图片
                    view.setBackgroundResource(R.drawable.id_defalut_ball);
                    redNums.remove((Object) (position + 1));
                }

            }
        });

        blueContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 判断当前点击的item是否被选中了
                if (!blueNums.contains(position + 1)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    view.setBackgroundResource(R.drawable.id_blueball);
                    // 摇晃的动画
                    view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.ia_ball_shake));
                    blueNums.add(position + 1);
                } else {
                    // 被选中
                    // 还原背景图片
                    view.setBackgroundResource(R.drawable.id_defalut_ball);
                    blueNums.remove((Object) (position + 1));
                }

            }
        });


    }

    @Override
    public int getID() {
        return ConstantValue.VIEW_SSQ;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ii_ssq_random_red:

                break;
            case R.id.ii_ssq_random_blue:

                break;
        }
        super.onClick(v);
    }

    //修改标题
    private void changeTitle(){
        String titleInfo="";
        if (bundle!=null){
            // 如果获取到：拼装标题
            titleInfo = "双色球第" + bundle.getString("issue") + "期";
        }else {
            // 否则默认的标题展示
            titleInfo = "双色球选号";
        }

        TitleManager.getInstance().changeTitle(titleInfo);

    }


    @Override
    public void onResume() {
        changeTitle();
        super.onResume();
    }
}
