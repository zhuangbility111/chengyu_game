package com.example.administrator.chengyu_game.View;

import android.app.NativeActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.administrator.chengyu_game.Bean.ChengyuDetails;
import com.example.administrator.chengyu_game.Bean.User;
import com.example.administrator.chengyu_game.Presenter.MainPresenter;
import com.example.administrator.chengyu_game.R;
import com.example.administrator.chengyu_game.Utils.ChengyuZisAdapter;
import com.example.administrator.chengyu_game.Utils.MusicService;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener{

    private EditText mEtChengyuText;
    private TextView mTvTitle;
    private AppCompatImageView mIvChengyuImg;
    private Toolbar mTbTitle;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RelativeLayout mRlShowChengyuZi;
    private int ziRowNum;
    private int ziColNUm;
    private GridView mGvShowChengyuZi;
    private Button mBtnEnterNextLevel;
    private TextView mTvShowMoney;
    private TextView mTvShowMaxLevel;
    private TextView mTvChengyu1;
    private TextView mTvChengyu2;
    private TextView mTvChengyu3;
    private TextView mTvChengyu4;
    private TextView[] mTvChengyus = new TextView[4];

    // 创建自定义的音乐Service对象
    private MusicService musicService;

    private MusicConnector musicConnector;

    private Drawable img_bg;

    private MainPresenter mMainPresenter;

    public static Handler mHandler = new Handler();

    // 保存是否通关的状态
    private boolean isPass = false;
    // 保存当前背景音乐是否在播放的状态
    private boolean isBgmPlaying = true;
    // 保存当前关卡数
    private int level = 1;

    private List<String> chengyuZisList = new ArrayList<>(24);
    // 用来保存四个成语字框中的字在原GridView中的位置（顺序）
    private int[] chengyuZiFrameIndex = new int[4];
    public ChengyuZisAdapter chengyuZisAdapter = new ChengyuZisAdapter(this, chengyuZisList);


    private class MusicConnector implements ServiceConnection {
        //成功绑定时调用 即bindService（）执行成功同时返回非空Ibinder对象
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicService = ((MusicService.MyMusicBinder) iBinder).getService();

        }

        //不成功绑定时调用
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
            Log.i("binding is fail", "binding is fail");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String userJson = getIntent().getStringExtra("userJson");
        String level = getIntent().getStringExtra("level");
        this.level = Integer.parseInt(level);
        mMainPresenter = new MainPresenter(this, userJson);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_list, menu);
        return true;
    }

    private void initView() {
//        initActionBar();
        mGvShowChengyuZi = (GridView) findViewById(R.id.mGvShowChengyuZi);
        mTvShowMoney = (TextView) findViewById(R.id.mTvShowMoney);
        mTvShowMaxLevel = (TextView) findViewById(R.id.mTvShowMaxLevel);
        mMainPresenter.getUserData();
        mTvChengyu1 = (TextView) findViewById(R.id.mTvSelectedChengyu1);
        mTvChengyus[0] = mTvChengyu1;
        mTvChengyu1.setOnClickListener(this);
        mTvChengyu2 = (TextView) findViewById(R.id.mTvSelectedChengyu2);
        mTvChengyus[1] = mTvChengyu2;
        mTvChengyu2.setOnClickListener(this);
        mTvChengyu3 = (TextView) findViewById(R.id.mTvSelectedChengyu3);
        mTvChengyus[2] = mTvChengyu3;
        mTvChengyu3.setOnClickListener(this);
        mTvChengyu4 = (TextView) findViewById(R.id.mTvSelectedChengyu4);
        mTvChengyus[3] = mTvChengyu4;
        mTvChengyu4.setOnClickListener(this);
//        mEtChengyuText = (EditText) findViewById(R.id.mEtText1);
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        mIvChengyuImg = (AppCompatImageView) findViewById(R.id.mIvChengyuImg);
//        mBtSubmitAnswer = (Button) findViewById(R.id.mBtSubmitAnswer);
//        mBtSubmitAnswer.setOnClickListener(this);
//        mBtShowDetails = (Button) findViewById(R.id.mBtShowDetails);
//        mBtShowDetails.setOnClickListener(this);
        mBtnEnterNextLevel = (Button) findViewById(R.id.mBtEnterNextLevel);
        mBtnEnterNextLevel.setOnClickListener(this);
        mTbTitle = (Toolbar) findViewById(R.id.mTbMainActivity);
        mTbTitle.inflateMenu(R.menu.toolbar_menu_list);
        mTbTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_get_hint: {
                        getChengyuHint();
                    }break;
                    case R.id.action_bgm_play: {
                        if (isBgmPlaying) {
                            pauseMusic();
                            item.setTitle("播放背景音乐");
                        }
                        else {
                            playMusic();
                            item.setTitle("暂停背景音乐");
                        }
                    }break;
                }
                return false;
            }
        });
        mTvTitle.setText("第  "+ String.valueOf(level)+"  关");
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSrlMainActivity);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 先判断是否有网络，有网络则加载，没有网络则进行提示
                if (isNetWorkAvailable(MainActivity.this)) {
                    mMainPresenter.getChengyuImg(level);
                }
                else {
                    Toast.makeText(MainActivity.this, "无网络！请打开网络！", Toast.LENGTH_LONG).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                }

            }
        });
        if (android.os.Build.VERSION.SDK_INT >= 21)
            img_bg = getResources().getDrawable(R.drawable.img_bg_border, getTheme());
        else
            img_bg = getResources().getDrawable(R.drawable.img_bg_border);
        mMainPresenter.getChengyuImg(level);
        initShowChengyuZiView();
        playMusic();
    }

    private void initShowChengyuZiView() {
        for (int i = 0 ; i < 4; i++)
            chengyuZiFrameIndex[i] = -1;
        mGvShowChengyuZi.setAdapter(chengyuZisAdapter);
        mGvShowChengyuZi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter adapter = adapterView.getAdapter();
                String zi = (String) adapter.getItem(i);
                if (!zi.equals("")) {
                    selectChengyuFrame(zi, i);
                    chengyuZisAdapter.notifyDataSetChanged();
                }
            }
        });
        getChengyuZiData();
    }

    private void selectChengyuFrame(String zi, int index) {
        for (int i = 0; i < 4; i++) {
            if (chengyuZiFrameIndex[i] == -1) {
                mTvChengyus[i].setText(zi);
                chengyuZiFrameIndex[i] = index;
                chengyuZisList.set(index, "");
                break;
            }
        }

        // 如果四个框中有大于等于1个空位，则不进行成语的判定
        // 如果四个框中没有空位，则进行成语的判定
        for (int i = 0; i < 4; i++) {
            if (chengyuZiFrameIndex[i] == -1)
                return;
        }
        mMainPresenter.checkForAnswer(level, mTvChengyu1.getText().toString()
                + mTvChengyu2.getText().toString()
                + mTvChengyu3.getText().toString()
                + mTvChengyu4.getText().toString());
    }

    private void getChengyuZiData() {
        // 使用这个list来实现成语字的随机位置显示
        List<Integer> numList = new ArrayList<>();
        chengyuZisList.clear();
        for (int i = 0; i < 4; i++) {
            mTvChengyus[i].setText("");
            chengyuZiFrameIndex[i] = -1;
        }
        for (int i = 0; i < 24; i++) {
            chengyuZisList.add("");
            numList.add(i);
        }
        for (int i = 0; i < 6; i++) {
            // nums保存的是这四个随机字在GridView中的位置
            int[] nums = new int[4];
            for (int j = 0; j < 4; j++) {
                int index = (int)(1+Math.random()*numList.size())-1;
                nums[j] = numList.get(index);
                numList.remove(index);
            }
            mMainPresenter.getChengyuZiData(this.level, nums, i+1);
        }
    }

    // 判断网络状态（是否有网络）
    public static boolean isNetWorkAvailable(Context context){
        boolean isAvailable = false ;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isAvailable()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void playMusic() {
        Intent intent = new Intent(this, MusicService.class);
        musicConnector = new MusicConnector();
        bindService(intent, musicConnector, Context.BIND_AUTO_CREATE);
        isBgmPlaying = true;
    }

    private void pauseMusic() {
        unbindService(musicConnector);
        isBgmPlaying = false;
    }

    private void getChengyuHint() {
        // 金币余额足够，并且此关尚未通关，进行提示
        if (Integer.parseInt(mTvShowMoney.getText().toString()) - 5 >= 0 && !isPass) {
            AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(MainActivity.this);
            normalDialog.setTitle("提示");
            normalDialog.setMessage("是否需要花费5金币以获得当前关卡的答案？");
            normalDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // ...To-do
                    mMainPresenter.getChengyuHint();
                }
            });

            normalDialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            // 创建实例并显示
            normalDialog.show();
        }
        else {
            Toast.makeText(this, "金币不足！", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.mBtSubmitAnswer:{
//                String answer = mEtChengyuText.getText().toString();
//                System.out.println("提交");
//                mMainPresenter.checkForAnswer(level, answer);
//            }break;
//            case R.id.mBtShowDetails:{

//            }break;
//
            // 进入下一关卡，更新相应的状态
            case R.id.mBtEnterNextLevel:{
                enterNextLevel();
            }
            case R.id.mTvSelectedChengyu1: {
                if (chengyuZiFrameIndex[0] != -1) {
                    chengyuZisList.set(chengyuZiFrameIndex[0], mTvChengyu1.getText().toString());
                    mTvChengyu1.setText("");
                    chengyuZiFrameIndex[0] = -1;
                    chengyuZisAdapter.notifyDataSetChanged();
                }
            }break;
            case R.id.mTvSelectedChengyu2: {
                if (chengyuZiFrameIndex[1] != -1) {
                    chengyuZisList.set(chengyuZiFrameIndex[1], mTvChengyu2.getText().toString());
                    mTvChengyu2.setText("");
                    chengyuZiFrameIndex[1] = -1;
                    chengyuZisAdapter.notifyDataSetChanged();
                }

            }break;
            case R.id.mTvSelectedChengyu3: {
                if (chengyuZiFrameIndex[2] != -1) {
                    chengyuZisList.set(chengyuZiFrameIndex[2], mTvChengyu3.getText().toString());
                    mTvChengyu3.setText("");
                    chengyuZiFrameIndex[2] = -1;
                    chengyuZisAdapter.notifyDataSetChanged();
                }

            }break;
            case R.id.mTvSelectedChengyu4: {
                if (chengyuZiFrameIndex[3] != -1) {
                    chengyuZisList.set(chengyuZiFrameIndex[3], mTvChengyu4.getText().toString());
                    mTvChengyu4.setText("");
                    chengyuZiFrameIndex[3] = -1;
                    chengyuZisAdapter.notifyDataSetChanged();
                }
            }break;
        }
    }

//    private void initActionBar() {
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
//    }

    @Override
    public void showChengyuText(ChengyuDetails c) {

    }

    @Override
    public void showChengyuImg(Bitmap b) {
        mIvChengyuImg.setImageBitmap(b);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showAnswerTrue(boolean isBest) {
        isPass = true;
        mBtnEnterNextLevel.setVisibility(View.VISIBLE);
        showTrueDialog(isBest);
//        mBtSubmitAnswer.setVisibility(View.GONE);
//        mBtShowDetails.setVisibility(View.VISIBLE);
//        mBtEnterNextLevel.setVisibility(View.VISIBLE);
    }

    private void showTrueDialog(boolean isBest) {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setTitle("提示");
        if (isBest) {
            normalDialog.setMessage("恭喜通关！您已创造了最好的历史成绩！金币+3！");
        }
        normalDialog.setMessage("恭喜通关！");
        normalDialog.setPositiveButton("进入下一关", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterNextLevel();
            }
        });
        normalDialog.setNeutralButton("查看成语详情", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, ShowDetailsActivity.class);
                intent.putExtra("level", level);
                startActivity(intent);
            }
        });
        normalDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // 创建实例并显示
        normalDialog.show();
    }

    private void enterNextLevel() {
        isPass = false;
        mBtnEnterNextLevel.setVisibility(View.GONE);
        level++;
        mTvTitle.setText("第  "+ String.valueOf(level)+"  关");
        mIvChengyuImg.setImageDrawable(img_bg);
        mMainPresenter.getChengyuImg(level);
        getChengyuZiData();
    }

    @Override
    public void showChengyuTextError() {
        Toast.makeText(this, "成语信息加载失败！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showChengyuImgError() {
        Toast.makeText(this, "图片加载失败！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAnswerFalse() {
        Toast.makeText(this, "答案错误！不要气馁，再想一想！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showChengyuZiDataSuccess(int[] nums, String chengyu) {
        for (int i = 0; i < nums.length; i++) {
            chengyuZisList.set(nums[i], String.valueOf(chengyu.charAt(i)));
        }
        chengyuZisAdapter.notifyDataSetChanged();
    }

    @Override
    public void showChengyuZiDataFailed() {

    }

    @Override
    public void showUserDataSuccess(String userJson) {
        User user = new Gson().fromJson(userJson, User.class);
        mTvShowMoney.setText(user.getRemain_money());
        mTvShowMaxLevel.setText(user.getMax_level());
    }

    @Override
    public void showUserDataFailed() {

    }

    @Override
    public void showChengyuHintSuccess(String chengyu, String remain_money) {
        for (int i = 0; i < 4; i++) {
            // 若原成语框上有字，则先将成语框上原有的文字去掉
            if (chengyuZiFrameIndex[i] != -1) {
                chengyuZisList.set(chengyuZiFrameIndex[i], mTvChengyus[i].getText().toString());
            }
            // 再将正确的答案填上去
            chengyuZiFrameIndex[i] = chengyuZisList.indexOf(String.valueOf(chengyu.charAt(i)));
            chengyuZisList.set(chengyuZiFrameIndex[i], "");
            mTvChengyus[i].setText(String.valueOf(chengyu.charAt(i)));
        }
        chengyuZisAdapter.notifyDataSetChanged();
        mTvShowMoney.setText(remain_money);
        mMainPresenter.checkForAnswer(level, mTvChengyu1.getText().toString()
                + mTvChengyu2.getText().toString()
                + mTvChengyu3.getText().toString()
                + mTvChengyu4.getText().toString());
    }

    @Override
    public void showChengyuHintFailed() {

    }


}
