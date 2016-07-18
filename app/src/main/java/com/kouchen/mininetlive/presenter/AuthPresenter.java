package com.kouchen.mininetlive.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.contracts.AuthContract;
import com.kouchen.mininetlive.models.UserInfo;
import com.kouchen.mininetlive.api.AuthService;
import com.kouchen.mininetlive.models.HttpResponse;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cainli on 16/6/25.
 */
public class AuthPresenter implements AuthContract.Presenter {

    private static final String TAG = "AuthPresenter";

    private AuthContract.View mAuthView;

    private AuthService mAuthService;

    public AuthPresenter(@NonNull AuthService authService,@NonNull AuthContract.View authView) {
        this.mAuthView = authView;
        this.mAuthService = authService;
    }

    @Override
    public void validateCredentials(Platform platform, String... args) {
        Platform plat = ShareSDK.getPlatform(platform.getName());
        if (plat == null) {
            return;
        }
        if (plat.isAuthValid()) {
            plat.removeAccount(true);
            return;
        }
        //使用SSO授权，通过客户单授权
        plat.SSOSetting(false);
        plat.setPlatformActionListener(new PlatformActionListener() {
            public void onComplete(final Platform plat, int action,
                final HashMap<String, Object> res) {
                if (action == Platform.ACTION_USER_INFOR) {
                    Call<HttpResponse> call = mAuthService.oauthLogin(plat.getName(), plat.getDb().getUserId(),
                            plat.getDb().getToken(), plat.getDb().getExpiresIn());
                    call.enqueue(new Callback<HttpResponse>() {

                        @Override
                        public void onResponse(Call<HttpResponse> call,
                            Response<HttpResponse> response) {
                            if (response.isSuccess()) {
                                HttpResponse httpResponse = response.body();
                                if (httpResponse.ret == 0) {
                                    JsonObject data = httpResponse.data.getAsJsonObject();
                                    Gson gson = new Gson();
                                    MNLApplication.getCacheManager()
                                        .put("token",
                                            gson.fromJson(data.get("token"), String.class));
                                    UserInfo user = gson.fromJson(data.get("user"), UserInfo.class);
                                    MNLApplication.getCacheManager().put("user", user);
                                    mAuthView.onSuccess(user);
                                } else {
                                    oauthRegister(plat, res, mAuthView, mAuthService);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<HttpResponse> call, Throwable t) {
                            Log.e(TAG, "onFailure: ", t);
                            mAuthView.onError("登陆失败");
                        }
                    });
                }
            }

            public void onError(Platform plat, int action, Throwable t) {
                mAuthView.onError("登陆失败");
            }

            public void onCancel(Platform plat, int action) {
                mAuthView.onError("取消成功");
            }
        });
        plat.showUser(null);
    }

    private void oauthRegister(Platform plat, HashMap<String, Object> res,
        final AuthContract.View mAuthView, AuthService accountService) {
        Call<HttpResponse> call;
        String nickname = null;
        int gender = 2;
        String avatar = null;
        String city = null;
        boolean success = false;
        switch (plat.getName()) {
            case "QQ":
                if (res.get("ret") == Integer.valueOf(0)) {
                    success = true;
                    nickname = (String) res.get("nickname");
                    if ("男".equals(res.get("gender"))) {
                        gender = 1;
                    } else if ("女".equals(res.get("gender"))) {
                        gender = 0;
                    }
                    avatar = String.valueOf(res.get("figureurl_2"));
                    city = String.valueOf(res.get("province"));
                } else {
                    success = true;
                }
                break;
            case "SinaWeibo":
                success = true;
                nickname = (String) res.get("screen_name");
                if ("m".equals(res.get("gender"))) {
                    gender = 1;
                } else if ("f".equals(res.get("gender"))) {
                    gender = 0;
                }
                avatar = String.valueOf(res.get("avatar_large"));
                city = String.valueOf(res.get("location"));
                break;
            case "Wechat":
                success = true;
                nickname = (String) res.get("nickname");
                gender = (int) res.get("sex");
                avatar = String.valueOf(res.get("headimgurl"));
                city = String.valueOf(res.get("province"));
                break;
        }
        if (!success) {
            mAuthView.onError("第三方那个登陆失败，请检查" + plat.getName() + "是否安装正常");
            return;
        }
        mAuthView.showProgress();
        call = accountService.oauthRegister(plat.getName(), plat.getDb().getUserId(),
            plat.getDb().getToken(), plat.getDb().getExpiresIn(), city, nickname, gender, avatar);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        JsonObject data = httpResponse.data.getAsJsonObject();
                        Gson gson = new Gson();
                        MNLApplication.getCacheManager()
                            .put("token", gson.fromJson(data.get("token"), String.class));
                        UserInfo user = gson.fromJson(data.get("user"), UserInfo.class);
                        MNLApplication.getCacheManager().put("user", user);
                        mAuthView.onSuccess(user);
                    } else {
                        mAuthView.onError(httpResponse.msg);
                    }
                } else {
                    mAuthView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mAuthView.onError("登陆失败");
            }
        });
    }

    @Override
    public void getVCode(String phone) {
        Call<HttpResponse> call = mAuthService.getVCode(phone);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse resp = response.body();
                    if (resp.ret == 0) {
                        mAuthView.onSuccess(null);
                    } else {
                        mAuthView.onError(resp.msg);
                    }
                } else {
                    mAuthView.onError("获取验证码失败");
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mAuthView.onError("获取验证码失败");
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }


    @Override
    public void register(String phone, String vcode, String password, String inviteCode, String nickname, int gender) {
        if (mAuthView != null) {
            mAuthView.showProgress();
        }
        Call<HttpResponse> call = mAuthService.register(phone, vcode, password, inviteCode,nickname,gender);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mAuthView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        JsonObject data = httpResponse.data.getAsJsonObject();
                        Gson gson = new Gson();
                        MNLApplication.getCacheManager()
                                .put("token", gson.fromJson(data.get("token"), String.class));
                        UserInfo user = gson.fromJson(data.get("user"), UserInfo.class);
                        MNLApplication.getCacheManager().put("user", user);
                        mAuthView.onSuccess(null);
                    } else {
                        mAuthView.onError(httpResponse.msg);
                    }
                } else {
                    mAuthView.onError("注册失败");
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                mAuthView.hideProgress();
                mAuthView.onError("注册失败");
            }
        });
    }

    @Override
    public void checkPhone(String phone) {
        if (mAuthView != null) {
            mAuthView.showProgress();
        }
        mAuthView.onSuccess(null);
//        Call<HttpResponse> call = mAuthService.checkPhone(phone);
//        call.enqueue(new Callback<HttpResponse>() {
//            @Override
//            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
//                mAuthView.hideProgress();
//                if (response.isSuccess()) {
//                    HttpResponse httpResponse = response.body();
//                    if (httpResponse.ret == 0) {
//                        mAuthView.onSuccess(null);
//                    } else {
//                        mAuthView.onError(httpResponse.msg);
//                    }
//                } else {
//                    mAuthView.onError("手机号校验失败");
//                }
//            }
//            @Override
//            public void onFailure(Call<HttpResponse> call, Throwable t) {
//                Log.e(TAG, "onFailure: ", t);
//                mAuthView.hideProgress();
//                mAuthView.onError("手机号校验失败");
//            }
//        });
    }

    @Override
    public void login(String phone, String password) {
        if (mAuthView != null) {
            mAuthView.showProgress();
        }
        Call<HttpResponse> call = mAuthService.login(phone, password);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mAuthView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        JsonObject data = httpResponse.data.getAsJsonObject();
                        Gson gson = new Gson();
                        MNLApplication.getCacheManager()
                            .put("token", gson.fromJson(data.get("token"), String.class));
                        UserInfo user = gson.fromJson(data.get("user"), UserInfo.class);
                        MNLApplication.getCacheManager().put("user", user);
                        mAuthView.onSuccess(user);
                    } else {
                        mAuthView.onError(httpResponse.msg);
                    }
                } else {
                    mAuthView.onError("登陆失败");
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                mAuthView.hideProgress();
                mAuthView.onError("登陆失败");
            }
        });
    }

}




