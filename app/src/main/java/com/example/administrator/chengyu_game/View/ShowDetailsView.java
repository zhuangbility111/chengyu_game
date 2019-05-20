package com.example.administrator.chengyu_game.View;

import android.graphics.Bitmap;

import com.example.administrator.chengyu_game.Bean.ChengyuDetails;

/**
 * Created by Administrator on 2019/4/12 0012.
 */

public interface ShowDetailsView {

    void showChengyuTextSuccess(ChengyuDetails chengyuDetails);

    void showChengyuTextFail();

    void showChengyuImgSuccess(Bitmap chengyuImg);

    void showChengyuImgFail();
}
