package com.example.administrator.chengyu_game.View;

import android.graphics.Bitmap;

import com.example.administrator.chengyu_game.Bean.ChengyuDetails;

/**
 * Created by Administrator on 2019/4/10 0010.
 */

public interface MainView {
    void showChengyuText(ChengyuDetails c);

    void showChengyuImg(Bitmap b);

    void showAnswerTrue(boolean isBest);

    void showChengyuTextError();

    void showChengyuImgError();

    void showAnswerFalse();

    void showChengyuZiDataSuccess(int[] nums, String chengyu);

    void showChengyuZiDataFailed();

    void showUserDataSuccess(String userJson);

    void showUserDataFailed();

    void showChengyuHintSuccess(String chengyu, String remain_money);

    void showChengyuHintFailed();
}
