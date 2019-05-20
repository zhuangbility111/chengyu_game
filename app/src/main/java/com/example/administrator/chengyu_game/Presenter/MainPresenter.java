package com.example.administrator.chengyu_game.Presenter;

import android.graphics.Bitmap;

import com.example.administrator.chengyu_game.Bean.ChengyuDetails;
import com.example.administrator.chengyu_game.CallBack.CheckAnswerCallBack;
import com.example.administrator.chengyu_game.CallBack.ChengyuImgCallBack;
import com.example.administrator.chengyu_game.CallBack.ChengyuTextCallBack;
import com.example.administrator.chengyu_game.CallBack.ChengyuZiCallBack;
import com.example.administrator.chengyu_game.CallBack.GetChengyuHintCallBack;
import com.example.administrator.chengyu_game.CallBack.GetUserCallBack;
import com.example.administrator.chengyu_game.Model.ChengyuModelImple;
import com.example.administrator.chengyu_game.View.MainActivity;
import com.example.administrator.chengyu_game.View.MainView;

/**
 * Created by Administrator on 2019/4/10 0010.
 */

public class MainPresenter {

    private MainView mMainView;

    public static ChengyuModelImple mChengyuModel;

    public MainPresenter(MainView mainView, String userJson) {
        this.mMainView = mainView;
        mChengyuModel = new ChengyuModelImple(userJson);
    }

    public void getChengyuText(int level) {
//        mChengyuModel.getChengyuText(level, new ChengyuTextCallBack() {
//            @Override
//            public void onChengyuTextSuccess(final ChengyuDetails chengyuDetails) {
//                MainActivity.mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mMainView.showChengyuText(chengyuDetails);
//                    }
//                });
//
//            }
//            @Override
//            public void onChengyuTextFail() {
//                MainActivity.mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mMainView.showChengyuTextError();
//                    }
//                });
//            }
//        });
    }



    public void getChengyuImg(int level) {
        mChengyuModel.getChengyuTextAndImg(level, new ChengyuImgCallBack() {
            @Override
            public void onImgSuccess(final Bitmap chengyuImg) {
                MainActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMainView.showChengyuImg(chengyuImg);
                    }
                });

            }

            @Override
            public void onImgFail() {
                MainActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMainView.showChengyuImgError();
                    }
                });
            }
        }, new ChengyuTextCallBack() {
            @Override
            public void onChengyuTextSuccess(final ChengyuDetails chengyuDetails) {
                MainActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }
            @Override
            public void onChengyuTextFail() {
                MainActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMainView.showChengyuTextError();
                    }
                });
            }
        });
    }

    public void checkForAnswer(int level, String answer) {
        mChengyuModel.checkForAnswer(level, answer, new CheckAnswerCallBack() {
            @Override
            public void onAnswerTrue(final boolean isBest) {
                MainActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMainView.showAnswerTrue(isBest);
                    }
                });
            }

            @Override
            public void onAnswerFalse() {
                MainActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMainView.showAnswerFalse();
                    }
                });

            }
        }, new GetUserCallBack() {
            @Override
            public void onSuccess(final String userJson) {
                MainActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMainView.showUserDataSuccess(userJson);
                    }
                });
            }

            @Override
            public void onFailed() {
                MainActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMainView.showUserDataFailed();
                    }
                });
            }
        });
    }

    public void getChengyuZiData(int level, final int[] nums, int times) {
        ChengyuZiCallBack callBack = new ChengyuZiCallBack() {
            @Override
            public void onSuccess(final String chengyu) {
                MainActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMainView.showChengyuZiDataSuccess(nums, chengyu);
                    }
                });

            }

            @Override
            public void onFailed() {
                mMainView.showChengyuZiDataFailed();
            }
        };
        if (times == 1) {
            mChengyuModel.getChengyuZiData(level, callBack);
        }
        else {
            int l = (int)(1+Math.random()*400);
            mChengyuModel.getChengyuZiData(l, callBack);
        }
    }

    public void getUserData() {
        mChengyuModel.getUserData(new GetUserCallBack() {
            @Override
            public void onSuccess(String userJson) {
                mMainView.showUserDataSuccess(userJson);
            }

            @Override
            public void onFailed() {

            }
        });
    }

    public void getChengyuHint() {
        mChengyuModel.getChengyuHint(new GetChengyuHintCallBack() {
            @Override
            public void onSuccess(final String chengyu, final String remain_money) {
                MainActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMainView.showChengyuHintSuccess(chengyu, remain_money);
                    }
                });

            }

            @Override
            public void onFailed() {
                MainActivity.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMainView.showChengyuHintFailed();
                    }
                });

            }
        });
    }
}
