package com.example.administrator.chengyu_game.Presenter;

import android.graphics.Bitmap;

import com.example.administrator.chengyu_game.Bean.ChengyuDetails;
import com.example.administrator.chengyu_game.CallBack.ChengyuImgCallBack;
import com.example.administrator.chengyu_game.CallBack.ChengyuTextCallBack;
import com.example.administrator.chengyu_game.Model.ChengyuModelImple;
import com.example.administrator.chengyu_game.View.ShowDetailsView;

/**
 * Created by Administrator on 2019/4/12 0012.
 */

public class ShowDetailsPresenter {

    private ShowDetailsView mShowDetailsView;
    public static ChengyuModelImple mChengyuModel;

    public ShowDetailsPresenter(ShowDetailsView mShowDetailsView) {
        this.mShowDetailsView = mShowDetailsView;
        mChengyuModel = MainPresenter.mChengyuModel;
    }

    public void getChengyuDetails(int level) {
        mChengyuModel.getChengyuTextAndImg(level, new ChengyuImgCallBack() {
                    @Override
                    public void onImgSuccess(Bitmap chengyuImg) {
                        mShowDetailsView.showChengyuImgSuccess(chengyuImg);
                    }

                    @Override
                    public void onImgFail() {
                        mShowDetailsView.showChengyuImgFail();
                    }
                }, new ChengyuTextCallBack() {
                    @Override
                    public void onChengyuTextSuccess(ChengyuDetails chengyuDetails) {
                        mShowDetailsView.showChengyuTextSuccess(chengyuDetails);
                    }

                    @Override
                    public void onChengyuTextFail() {
                        mShowDetailsView.showChengyuImgFail();
                    }
                }
        );
    }
}
