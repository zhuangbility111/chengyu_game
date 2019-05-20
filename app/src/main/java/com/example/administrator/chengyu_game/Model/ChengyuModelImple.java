package com.example.administrator.chengyu_game.Model;

import android.graphics.Bitmap;

import com.example.administrator.chengyu_game.Bean.ChengyuDetails;
import com.example.administrator.chengyu_game.Bean.User;
import com.example.administrator.chengyu_game.CallBack.CheckAnswerCallBack;
import com.example.administrator.chengyu_game.CallBack.ChengyuTextCallBack;
import com.example.administrator.chengyu_game.CallBack.ChengyuImgCallBack;
import com.example.administrator.chengyu_game.CallBack.ChengyuZiCallBack;
import com.example.administrator.chengyu_game.CallBack.GetChengyuHintCallBack;
import com.example.administrator.chengyu_game.CallBack.GetUserCallBack;
import com.example.administrator.chengyu_game.CallBack.HttpCallBack;
import com.example.administrator.chengyu_game.Utils.HttpUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/10 0010.
 */

public class ChengyuModelImple implements ChengyuModel {
    private ChengyuDetails mChengyuDetails;
    private User user;
    private String userJson;
    private Bitmap mChengyuImg;
    private Gson mGson = new Gson();
    private String mChengyuTextUrl = HttpUtils.IP_ADDRESS +"chengyu/details?";
    private String mChengyuImgUrl = HttpUtils.IP_ADDRESS + "chengyu/img/";
    private String mUploadUserUrl = HttpUtils.IP_ADDRESS + "chengyu/user/upload_information";

    public ChengyuModelImple(String userJson) {
        this.user = mGson.fromJson(userJson, User.class);
        this.userJson = userJson;
    }

    // 加载成语文本和对应的图片
    private void loadChengyuTextAndImg(final int level, final ChengyuTextCallBack chengyuTextCallBack,
                                       final ChengyuImgCallBack chengyuImgCallBack) {
        String url = mChengyuTextUrl+"level="+String.valueOf(level);
        // 先加载成语文本
        HttpUtils.getText(url, new HttpCallBack() {
            @Override
            public void onTextSuccess(String response) {
                response = response.replace("\'", "\"");
//                System.out.println(response);
                mChengyuDetails = mGson.fromJson(response, ChengyuDetails.class);
                // 加载文本成功后，在回调接口中加载图片
                if (mChengyuDetails != null && Integer.parseInt(mChengyuDetails.getLevel()) == level) {
                    System.out.println("加载图片");
                    loadChengyuImg(level, chengyuImgCallBack);
                }
            }

            @Override
            public void onImgSuccess(Bitmap bitmap) {

            }

            @Override
            public void onTextFail() {
                chengyuTextCallBack.onChengyuTextFail();
            }

            @Override
            public void onImgFail() {

            }
        });
    }

    private void loadChengyuImg(int level, final ChengyuImgCallBack chengyuImgCallBack) {
        String url = mChengyuImgUrl+mChengyuDetails.getChengyu()+".png";
        HttpUtils.getImg(url, new HttpCallBack() {
            @Override
            public void onTextSuccess(String response) {

            }

            @Override
            public void onImgSuccess(Bitmap bitmap) {
                mChengyuImg = bitmap;
                chengyuImgCallBack.onImgSuccess(bitmap);
            }

            @Override
            public void onTextFail() {

            }

            @Override
            public void onImgFail() {
                chengyuImgCallBack.onImgFail();
            }
        });
    }

//    public void getChengyuText(int level, final ChengyuTextCallBack mChengyuTextCallBack) {
//        // 如果当前关卡对应的成语未加载进来，则通过网络进行加载
//        if (mChengyuDetails == null || Integer.parseInt(mChengyuDetails.getLevel()) != level) {
//
//            loadChengyuTextByHttp(mChengyuTextUrl+"level="+String.valueOf(level), mChengyuTextCallBack);
//        }
//        if (mChengyuDetails != null && Integer.parseInt(mChengyuDetails.getLevel()) == level) {
//            // 通过Presenter层将结果反馈至View层进行展示
//            mChengyuTextCallBack.onChengyuTextSuccess(mChengyuDetails);
//        }
//    }

    // 加载成语候选字
    private void loadChengyuZiData(final int level, final ChengyuZiCallBack chengyuZiCallBack) {
        String url = mChengyuTextUrl+"level="+String.valueOf(level);
        // 先加载成语文本
        HttpUtils.getText(url, new HttpCallBack() {
            @Override
            public void onTextSuccess(String response) {
                response = response.replace('\'', '\"');
                System.out.println(response);
                ChengyuDetails mChengyuDetails = mGson.fromJson(response, ChengyuDetails.class);
                mChengyuDetails.setYingwen(mChengyuDetails.getChengyu().replace('~', '\''));
                chengyuZiCallBack.onSuccess(mChengyuDetails.getChengyu());
            }

            @Override
            public void onImgSuccess(Bitmap bitmap) {

            }

            @Override
            public void onTextFail() {
                chengyuZiCallBack.onFailed();
            }

            @Override
            public void onImgFail() {

            }
        });
    }

    // 获取成语文本与图片
    public void getChengyuTextAndImg(int level, final ChengyuImgCallBack mChengyuImgCallBack,
                                     ChengyuTextCallBack mChengyuTextCallBack) {
        // 如果当前关卡对应的成语未加载进来，则通过网络进行加载
        if (mChengyuDetails == null || mChengyuImg == null || Integer.parseInt(mChengyuDetails.getLevel()) != level) {
            loadChengyuTextAndImg(level, mChengyuTextCallBack, mChengyuImgCallBack);
        }
        else {
            mChengyuImgCallBack.onImgSuccess(mChengyuImg);
            mChengyuTextCallBack.onChengyuTextSuccess(mChengyuDetails);
        }

//        if (mChengyuDetails != null && Integer.parseInt(mChengyuDetails.getLevel()) == level) {
//
//            System.out.println(mChengyuImgUrl+mChengyuDetails.getChengyu()+".png");
//            loadChengyuImgByHttp(mChengyuImgUrl+mChengyuDetails.getChengyu()+".png", mChengyuImgCallBack);
//        }

    }

    // 检查成语答案是否正确
    public void checkForAnswer(int level, String answer,
                               final CheckAnswerCallBack checkAnswerCallBack, final GetUserCallBack userCallBack) {
//        // 如果当前关卡对应的成语未加载进来，则通过网络进行加载
//        if (mChengyuDetails == null || Integer.parseInt(mChengyuDetails.getLevel()) != level) {
//            loadChengyuTextAndImg(mChengyuTextUrl+"level="+String.valueOf(level), chengyuTextCallBack, );
//        }
        if (mChengyuDetails != null && Integer.parseInt(mChengyuDetails.getLevel()) == level) {
            // 如果答案与成语名字相同，则通过presenter层将结果反馈至View进行展示
            // 并更新用户的对应信息（如更新用户的最大关卡，以及获取金币）
            if (answer.equals(mChengyuDetails.getChengyu())) {
                final int maxLevel = Integer.parseInt(user.getMax_level());
                final int money = Integer.parseInt(user.getRemain_money());
                if (level > maxLevel) {
                    checkAnswerCallBack.onAnswerTrue(true);
                    String url = mUploadUserUrl;
                    Map<String, String> tempMap = new HashMap<>();
                    tempMap.put("username", user.getUsername());
                    tempMap.put("max_level", String.valueOf(maxLevel+1));
                    tempMap.put("remain_money", String.valueOf(money+3));
                    HttpUtils.uploadUser(url, tempMap, new HttpCallBack() {
                        @Override
                        public void onTextSuccess(String response) {
                            // 更新后台用户数据成功，则同时更新本地用户的数据，并且显示通关成功，返回更新后的用户数据
                            if (response.equals("1")) {

                                user.setMax_level(String.valueOf(maxLevel+1));
                                user.setRemain_money(String.valueOf(money+3));
                                userCallBack.onSuccess(new Gson().toJson(user));
                            }
                            // 否则后台用户更新数据失败，则不做任何相应
                            else {
                                userCallBack.onFailed();
                            }
                        }

                        @Override
                        public void onImgSuccess(Bitmap bitmap) {

                        }

                        @Override
                        public void onTextFail() {
                            userCallBack.onFailed();
                        }

                        @Override
                        public void onImgFail() {

                        }
                    });
                }
                else {
                    checkAnswerCallBack.onAnswerTrue(false);
                }
            }

                // 如果答案错误，同样返回结果给View进行展示
            else
                checkAnswerCallBack.onAnswerFalse();
        }

    }

    public void getChengyuZiData(int level, ChengyuZiCallBack chengyuZiCallBack) {
        if (mChengyuDetails != null && level == Integer.parseInt(mChengyuDetails.getLevel())) {
            chengyuZiCallBack.onSuccess(mChengyuDetails.getChengyu());
        }
        else {
            loadChengyuZiData(level, chengyuZiCallBack);
        }
    }

    public void getUserData(GetUserCallBack userCallBack) {
        if (user != null) {
            userCallBack.onSuccess(this.userJson);
        }
    }

    public void getChengyuHint(final GetChengyuHintCallBack chengyuHintCallBack) {
        if (mChengyuDetails != null) {
            String url = mUploadUserUrl;
            // 剩余金币为扣去提示的金币数
            final int remainMoney = Integer.parseInt(user.getRemain_money())-5;
            Map<String, String> tempMap = new HashMap<>();
            tempMap.put("username", user.getUsername());
            tempMap.put("max_level", user.getMax_level());
            tempMap.put("remain_money", String.valueOf(remainMoney));
            HttpUtils.uploadUser(url, tempMap, new HttpCallBack() {
                @Override
                public void onTextSuccess(String response) {
                    // 更新后台用户数据成功，则同时更新本地用户的数据，并且显示通关成功，返回更新后的用户数据
                    if (response.equals("1")) {
                        user.setRemain_money(String.valueOf(remainMoney));
                        chengyuHintCallBack.onSuccess(mChengyuDetails.getChengyu(), String.valueOf(remainMoney));
                    }
                    // 否则后台用户更新数据失败，则不做任何相应
                    else {
                        chengyuHintCallBack.onFailed();
                    }
                }

                @Override
                public void onImgSuccess(Bitmap bitmap) {

                }

                @Override
                public void onTextFail() {
                    chengyuHintCallBack.onFailed();
                }

                @Override
                public void onImgFail() {

                }
            });
        }

    }

}
