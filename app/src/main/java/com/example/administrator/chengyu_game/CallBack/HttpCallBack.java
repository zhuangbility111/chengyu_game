package com.example.administrator.chengyu_game.CallBack;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/7/28 0028.
 */

public interface HttpCallBack {
    void onTextSuccess(String response);

    void onImgSuccess(Bitmap bitmap);

    void onTextFail();

    void onImgFail();


}
