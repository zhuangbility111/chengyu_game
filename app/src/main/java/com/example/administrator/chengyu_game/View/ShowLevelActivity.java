package com.example.administrator.chengyu_game.View;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.administrator.chengyu_game.Bean.User;
import com.example.administrator.chengyu_game.Presenter.ShowSelectLevelPresenter;
import com.example.administrator.chengyu_game.R;
import com.example.administrator.chengyu_game.Utils.SelectLevelAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ShowLevelActivity extends AppCompatActivity implements ShowLevelView{

    private GridView mGvShowSelectLevel;
    private List<String> selectLevelList = new ArrayList<>(424);
    private SelectLevelAdapter selectLevelAdapter = new SelectLevelAdapter(this, selectLevelList);
    private User user = new User();
    private ShowSelectLevelPresenter mPresenter = new ShowSelectLevelPresenter(this);
    public static Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_level);
        user.setUsername(getIntent().getStringExtra("username"));
        initView();
    }

    private void initView() {
        mGvShowSelectLevel = (GridView) findViewById(R.id.mGvShowSelectLevel);
        mGvShowSelectLevel.setAdapter(selectLevelAdapter);
        mGvShowSelectLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if ("1".equals(selectLevelList.get(i))) {
                    Adapter adapter = adapterView.getAdapter();
                    String level = String.valueOf(i+1);
                    Intent intent = new Intent(ShowLevelActivity.this, MainActivity.class);
                    intent.putExtra("level", level);
                    intent.putExtra("userJson", new Gson().toJson(user));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSelectLevel();
    }

    private void getSelectLevel() {
        mPresenter.getSelectLevel(user.getUsername());
    }

    @Override
    public void getSelectLevelDataSuccess(String userJson) {
        user = new Gson().fromJson(userJson, User.class);
        int max = Integer.parseInt(user.getMax_level());
        int i = 0;
        selectLevelList.clear();
        for (; i < max; i++) {
            selectLevelList.add("1");
            System.out.println(i);
        }
        if (max < 424) {
            selectLevelList.add("1");
            i++;
        }
        for (; i < 424; i++) {
            selectLevelList.add("0");
        }
        selectLevelAdapter.notifyDataSetChanged();
    }

    @Override
    public void getSelectLevelDatafail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
