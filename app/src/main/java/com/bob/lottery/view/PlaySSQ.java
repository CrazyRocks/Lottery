package com.bob.lottery.view;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.bob.lottery.Manager.BottomManager;
import com.bob.lottery.Manager.MiddleManager;
import com.bob.lottery.Manager.TitleManager;
import com.bob.lottery.R;
import com.bob.lottery.bean.ShoppingCart;
import com.bob.lottery.bean.Ticket;
import com.bob.lottery.element.CurrentIssueElement;
import com.bob.lottery.engine.CommonInfoEngine;
import com.bob.lottery.engine.ShakeListener;
import com.bob.lottery.factory.BeanFactory;
import com.bob.lottery.protocol.Message;
import com.bob.lottery.protocol.Oelement;
import com.bob.lottery.util.ConstantValue;
import com.bob.lottery.util.PromptManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/3/5.
 */

//双色球选号界面
public class PlaySSQ extends BaseUI implements PlayGame{
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
    private ShakeListener listener;



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

        redContainer.setOnActionUpListener(new MyGridView.OnActionUpListener() {
            @Override
            public void onActionUp(View view, int position) {
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
                changeNotice();
            }
        });



        blueContainer.setOnActionUpListener(new MyGridView.OnActionUpListener() {
            @Override
            public void onActionUp(View view, int position) {
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
                changeNotice();
            }
        });


    }

    @Override
    public int getID() {
        return ConstantValue.VIEW_SSQ;
    }

    @Override
    public void onClick(View v) {
        Random random=new Random();
        switch (v.getId()) {
            case R.id.ii_ssq_random_red:
                redNums.clear();
                //机选红球
                while (redNums.size()<6){
                    int num = random.nextInt(33) + 1;
                    if (redNums.contains(num))
                    {
                        continue;
                    }
                    redNums.add(num);
                }
                //界面更新
                changeNotice();
                redAdapter.notifyDataSetChanged();
                break;
            case R.id.ii_ssq_random_blue:
                blueNums.clear();
                int num = random.nextInt(16) + 1;
                blueNums.add(num);
                blueAdapter.notifyDataSetChanged();
                changeNotice();
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
        changeNotice();
        clear();
        listener=new ShakeListener(context) {
            @Override
            public void randomCure() {
                randomSSQ();
            }
        };
        manager.registerListener(listener,manager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER),SensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
    }


    //机选一注
    private void randomSSQ() {
        Random random=new Random();
        redNums.clear();
        //机选红球
        while (redNums.size()<6){
            int num = random.nextInt(33) + 1;
            if (redNums.contains(num))
            {
                continue;
            }
            redNums.add(num);
        }
        //界面更新
        redAdapter.notifyDataSetChanged();

        //机选蓝球
        blueNums.clear();
        int num = random.nextInt(16) + 1;
        blueNums.add(num);
        blueAdapter.notifyDataSetChanged();

        changeNotice();

    }

    @Override
    public void onPause() {
        manager.unregisterListener(listener);
        super.onPause();
    }

    //底部导航提示信息
    private void changeNotice(){
        String notice="";
        //以一注为分隔
        if (redNums.size()<6){
            notice="您还需要选择"+(6-redNums.size())+"个红球";
        }else if (blueNums.size()==0){
            notice="您还需要选择"+1+"个蓝球";
        }else {
            notice="共"+calc()+" 注"+calc()*2+" 元";
        }

        BottomManager.getInstance().changeGameBottomNotice(notice);
    }

    //计算注数
    private int calc(){
        //红球
        int redC = (int) (factorial(redNums.size()) / (factorial(6) * factorial(redNums.size() - 6)));
        //蓝球
        int blueC = blueNums.size();
        return redC * blueC;
    }


    //计算一个数的阶乘（递归）
    private long factorial(int num) {
        // num=7 7*6*5...*1

        if (num > 1) {
            return num * factorial(num - 1);
        } else if (num == 1 || num == 0) {
            return 1;
        } else {
            throw new IllegalArgumentException("num >= 0");
        }
    }



   //清除
   @Override
    public void clear() {
        redNums.clear();
        blueNums.clear();
        changeNotice();

        redAdapter.notifyDataSetChanged();
        blueAdapter.notifyDataSetChanged();

    }

    @Override
    public void done() {
        //①判断：用户是否选择了一注投注
        if (redNums.size()>=6 && blueNums.size()>=1){
            //// 一个购物车中，只能放置一个彩种，当前期的投注信息
            // ②判断：是否获取到了当前销售期的信息
            if (bundle!=null){
                // ③封装用户的投注信息：红球、蓝球、注数
                Ticket ticket=new Ticket();
                DecimalFormat decimalFormat = new DecimalFormat("00");
                StringBuffer redBuffer = new StringBuffer();
                for (Integer item : redNums) {
                    redBuffer.append(" ").append(decimalFormat.format(item));
                }
                ticket.setRedNum(redBuffer.substring(1));

                StringBuffer blueBuffer = new StringBuffer();
                for (Integer item : blueNums) {
                    blueBuffer.append(" ").append(decimalFormat.format(item));
                }
                ticket.setBlueNum(blueBuffer.substring(1));

                ticket.setNum(calc());
                // ④创建彩票购物车，将投注信息添加到购物车中
                ShoppingCart.getInstance().getTickets().add(ticket);
                // ⑤设置彩种的标示，设置彩种期次
                ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
                ShoppingCart.getInstance().setLotteryid(ConstantValue.SSQ);
                // ⑥界面跳转——购物车展示
                MiddleManager.getInstance().changeUI(Shopping.class, bundle);
            }else {
                // 重新获取期次信息
                getCurrentIssueInfo();
            }

        }else {
            // 提示：需要选择一注
            PromptManager.showToast(context, "需要选择一注");
        }



    }


    private void getCurrentIssueInfo() {
        new MyHttpTask<Integer>() {
            protected void onPreExecute() {
                // 显示滚动条
                PromptManager.showProgressDialog(context);
            }

            @Override
            protected Message doInBackground(Integer... params) {
                // 获取数据——业务的调用
                CommonInfoEngine engine = BeanFactory.getImpl(CommonInfoEngine.class);
                return engine.getCurrentIssueInfo(params[0]);
            }

            @Override
            protected void onPostExecute(Message result) {
                PromptManager.closeProgressDialog();
                // 更新界面
                if (result != null) {
                    Oelement oelement = result.getBody().getOelement();

                    if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
                        CurrentIssueElement element = (CurrentIssueElement) result.getBody().getElements().get(0);
                        // 创建bundle
                        bundle = new Bundle();
                        bundle.putString("issue", element.getIssue());

                        changeTitle();
                    } else {
                        PromptManager.showToast(context, oelement.getErrormsg());
                    }
                } else {
                    // 可能：网络不通、权限、服务器出错、非法数据……
                    // 如何提示用户
                    PromptManager.showToast(context, "服务器忙，请稍后重试……");
                }

                super.onPostExecute(result);
            }
        }.executeProxy(ConstantValue.SSQ);
    }

}
