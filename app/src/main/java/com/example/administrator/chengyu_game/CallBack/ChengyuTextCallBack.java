package com.example.administrator.chengyu_game.CallBack;

import android.graphics.Bitmap;

import com.example.administrator.chengyu_game.Bean.ChengyuDetails;

/**
 * Created by Administrator on 2019/4/10 0010.
 */

public interface ChengyuTextCallBack {
    void onChengyuTextSuccess(ChengyuDetails chengyuDetails);

    void onChengyuTextFail();

}
