package com.example.administrator.chengyu_game.CallBack;

/**
 * Created by Administrator on 2019/4/21 0021.
 */

public interface GetChengyuHintCallBack {
    void onSuccess(String chengyu, String remain_money);

    void onFailed();
}
