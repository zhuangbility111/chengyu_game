package com.example.administrator.chengyu_game.View;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.chengyu_game.Bean.ChengyuDetails;
import com.example.administrator.chengyu_game.Presenter.ShowDetailsPresenter;
import com.example.administrator.chengyu_game.R;

public class ShowDetailsActivity extends AppCompatActivity implements ShowDetailsView{

    private Toolbar mTbTitle;
    private ImageView mIvTitle;
    private CollapsingToolbarLayout mCtlTitle;
    private TextView mTvChengyu;
    private TextView mTvPingyin;
    private TextView mTvJieshi;
    private TextView mTvChuchu;
    private TextView mTvJulizaoju;
    private TextView mTvPingyindaima;
    private TextView mTvJinyici;
    private TextView mTvFanyici;
    private TextView mTvXiehouyu;
    private TextView mTvDengmi;
    private TextView mTvYongfa;
    private TextView mTvYingwen;
    private TextView mTvGushi;



    private int level;
    private ShowDetailsPresenter mDetailsPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        mDetailsPresenter = new ShowDetailsPresenter(this);
        level = getIntent().getIntExtra("level", 1);
        initView();
    }

    private void initView() {
        mTbTitle = (Toolbar) findViewById(R.id.mTbDetailsTitle);
        mIvTitle = (ImageView) findViewById(R.id.mIvDetailsTitle);
        mCtlTitle = (CollapsingToolbarLayout) findViewById(R.id.mCtlTitle);
        mTvChengyu = (TextView) findViewById(R.id.mTvChengyu);
        mTvPingyin = (TextView) findViewById(R.id.mTvPingyin);
        mTvJieshi = (TextView) findViewById(R.id.mTvJieshi);
        mTvChuchu = (TextView) findViewById(R.id.mTvChuchu);
        mTvJulizaoju = (TextView) findViewById(R.id.mTvJulizaoju);
        mTvPingyindaima = (TextView) findViewById(R.id.mTvPingyindaima);
        mTvJinyici = (TextView) findViewById(R.id.mTvJinyici);
        mTvFanyici = (TextView) findViewById(R.id.mTvFanyici);
        mTvXiehouyu = (TextView) findViewById(R.id.mTvXiehouyu);
        mTvDengmi = (TextView) findViewById(R.id.mTvDengmi);
        mTvYongfa = (TextView) findViewById(R.id.mTvYongfa);
        mTvYingwen = (TextView) findViewById(R.id.mTvYingwen);
        mTvGushi = (TextView) findViewById(R.id.mTvGushi);
        setSupportActionBar(mTbTitle);
        mTbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.out.println("返回");
            }
        });

        mDetailsPresenter.getChengyuDetails(level);
    }


    @Override
    public void showChengyuTextSuccess(ChengyuDetails chengyuDetails) {
        // 通过这个来覆盖Toolbar的标题
        mCtlTitle.setTitle(chengyuDetails.getChengyu());
        System.out.println(chengyuDetails.getChengyu());
        mTvChengyu.setText("【成语】："+chengyuDetails.getChengyu());
        mTvPingyin.setText("【拼音】："+chengyuDetails.getPinyin());
        mTvJieshi.setText("【解释】："+chengyuDetails.getJieshi());
        mTvChuchu.setText("【出处】："+chengyuDetails.getChuchu());
        mTvJulizaoju.setText("【举例造句】："+chengyuDetails.getJulizaoju());
        mTvPingyindaima.setText("【拼音代码】："+chengyuDetails.getPinyindaima());
        mTvJinyici.setText("【近义词】："+chengyuDetails.getJinyici());
        mTvFanyici.setText("【反义词】："+chengyuDetails.getFanyici());
        mTvXiehouyu.setText("【歇后语】："+chengyuDetails.getXiehouyu());
        mTvDengmi.setText("【灯谜】："+chengyuDetails.getDengmi());
        mTvYongfa.setText("【用法】："+chengyuDetails.getYongfa());
        mTvYingwen.setText("【英文】："+chengyuDetails.getYingwen());
        mTvGushi.setText("【故事】："+chengyuDetails.getGushi());
    }

    @Override
    public void showChengyuTextFail() {
        Toast.makeText(this, "成语信息加载失败！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showChengyuImgSuccess(Bitmap chengyuImg) {
        mIvTitle.setImageBitmap(chengyuImg);
    }

    @Override
    public void showChengyuImgFail() {
        Toast.makeText(this, "图片加载失败！", Toast.LENGTH_LONG).show();
    }
}
