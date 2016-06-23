/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.kouchen.mininetlive.auth;

import android.os.Handler;
import android.os.Looper;
import cn.sharesdk.framework.Platform;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    private Handler uiHandler = new Handler(Looper.getMainLooper());

    private EventHandler eventHandler =  new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (loginView != null) {
                                loginView.onSubmitVCodeSuccess();
                            }
                        }
                    });
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (loginView != null) {
                                loginView.onGetVCodeSuccess();
                            }
                        }
                    });
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }else{
                //{"detail":"用户提交校验的验证码错误。","status":468,"description":"需要校验的验证码错误"}
                ((Throwable)data).printStackTrace();
            }
        }};

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public void validateCredentials(Platform platform, String... args) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginInteractor.oauthLogin(this,platform,args);
    }

    @Override
    public void login(String phone, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginInteractor.login(this,phone,password);
    }

    @Override
    public void sendMSM(String s) {
        if (loginView != null) {
            loginView.showProgress();
        }
        SMSSDK.getVerificationCode("+86", s, new OnSendMessageHandler() {
            @Override
            public boolean onSendMessage(String s, String s1) {
                return false;
            }
        });
    }

    @Override
    public void commmitSMS(String phone,String code) {
        if (loginView != null) {
            loginView.showProgress();
        }
        SMSSDK.submitVerificationCode("+86",phone,code);
    }

    @Override
    public void onDestroy() {
        loginView = null;
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.navigateToHome();
        }
    }

    @Override
    public void onError(String msg) {
        if (loginView != null) {
            loginView.setError(msg);
            loginView.hideProgress();
        }
    }

    @Override
    public void onToRegister() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.startRegister();
        }
    }
}
