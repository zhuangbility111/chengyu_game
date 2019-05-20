package com.example.administrator.chengyu_game.Presenter;

import com.example.administrator.chengyu_game.CallBack.GetUserCallBack;
import com.example.administrator.chengyu_game.Model.LevelModelImple;
import com.example.administrator.chengyu_game.View.MainActivity;
import com.example.administrator.chengyu_game.View.ShowLevelActivity;
import com.example.administrator.chengyu_game.View.ShowLevelView;

/**
 * Created by Administrator on 2019/5/16 0016.
 */

public class ShowSelectLevelPresenter {

    private LevelModelImple mLevelModel;
    private ShowLevelView mShowLevelView;

    public ShowSelectLevelPresenter(ShowLevelView mShowLevelView) {
        this.mShowLevelView = mShowLevelView;
        this.mLevelModel = new LevelModelImple();
    }

    public void getSelectLevel(String account) {
        mLevelModel.getSelectLevel(account, new GetUserCallBack() {
            @Override
            public void onSuccess(final String userJson) {
                ShowLevelActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mShowLevelView.getSelectLevelDataSuccess(userJson);
                    }
                });
            }

            @Override
            public void onFailed() {
                ShowLevelActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mShowLevelView.getSelectLevelDatafail("错误");
                    }
                });
            }
        });
    }

}
