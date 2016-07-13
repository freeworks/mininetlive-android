package com.kouchen.mininetlive.auth;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.rest.service.AuthService;
import com.kouchen.mininetlive.rest.service.HttpResponse;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthInteractorImpl implements AuthInteractor {

    public static final String TAG = AuthInteractorImpl.class.getSimpleName();


    @Override
    public void oauthLogin(final OnLoginFinishedListener listener, Platform platform, String... args) {
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
            public void onComplete(final Platform plat, int action, final HashMap<String, Object> res) {
                if (action == Platform.ACTION_USER_INFOR) {
                    final AuthService accountService = MNLApplication.getRestClient().getAuthService();
                    Call<HttpResponse> call = accountService.oauthLogin(plat.getName(),
                            plat.getDb().getUserId(),
                            plat.getDb().getToken(),
                            plat.getDb().getExpiresIn());
                    call.enqueue(new Callback<HttpResponse>() {

                        @Override
                        public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                            if (response.isSuccess()) {
                                HttpResponse httpResponse = response.body();
                                if (httpResponse.ret == 0) {
                                    JsonObject data = httpResponse.data.getAsJsonObject();
                                    Gson gson = new Gson();
                                    MNLApplication.getCacheManager().put("token", gson.fromJson(data.get("token"), String.class));
                                    UserInfo user = gson.fromJson(data.get("user"), UserInfo.class);
                                    MNLApplication.getCacheManager().put("user", user);
                                    listener.onSuccess();
                                } else {
                                    oauthRegister(plat, res, listener, accountService);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<HttpResponse> call, Throwable t) {
                            Log.e(TAG, "onFailure: ", t);
                            listener.onError("登陆失败");
                        }
                    });
                }
            }

            public void onError(Platform plat, int action, Throwable t) {
                listener.onError("登陆失败");
            }

            public void onCancel(Platform plat, int action) {
                listener.onError("取消成功");
            }
        });
        plat.showUser(null);
    }

    private void oauthRegister(Platform plat, HashMap<String, Object> res, final OnLoginFinishedListener listener, AuthService accountService) {
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
            listener.onError("第三方那个登陆失败，请检查" + plat.getName() + "是否安装正常");
            return;
        }
        listener.onRegisting();
        call = accountService.oauthRegister(plat.getName(),
                plat.getDb().getUserId(),
                plat.getDb().getToken(),
                plat.getDb().getExpiresIn(),
                city,
                nickname, gender, avatar);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        JsonObject data = httpResponse.data.getAsJsonObject();
                        Gson gson = new Gson();
                        MNLApplication.getCacheManager().put("token", gson.fromJson(data.get("token"), String.class));
                        UserInfo user = gson.fromJson(data.get("user"), UserInfo.class);
                        MNLApplication.getCacheManager().put("user", user);
                        listener.onSuccess();
                    } else {
                        listener.onError(httpResponse.msg);
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                listener.onError("登陆失败");
            }
        });
    }

    @Override
    public void login(final OnLoginFinishedListener listener, final String phone, final String password) {
        AuthService accountService = MNLApplication.getRestClient().getAuthService();
        Call<HttpResponse> call = accountService.login(phone, password);
        call.enqueue(new Callback<HttpResponse>() {

            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        JsonObject data = httpResponse.data.getAsJsonObject();
                        Gson gson = new Gson();
                        MNLApplication.getCacheManager().put("token", gson.fromJson(data.get("token"), String.class));
                        UserInfo user = gson.fromJson(data.get("user"), UserInfo.class);
                        MNLApplication.getCacheManager().put("user", user);
                        listener.onSuccess();
                    } else {
                        listener.onError(httpResponse.msg);
                    }
                } else {
                    listener.onError("登陆失败");
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                listener.onError("登陆失败");
            }
        });
    }

    @Override
    public void getVCode(final OnLoginFinishedListener listener, String phone) {
        AuthService accountService = MNLApplication.getRestClient().getAuthService();
        Call<HttpResponse> call = accountService.getVCode(phone);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse resp = response.body();
                    if (resp.ret == 0) {
                        listener.onSuccess();
                    } else {
                        listener.onError(resp.msg);
                    }
                } else {
                    listener.onError("获取验证码失败");
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                listener.onError("获取验证码失败");
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void register(final OnLoginFinishedListener listener, String phone, String vcode, String password, String inviteCode) {
        AuthService accountService = MNLApplication.getRestClient().getAuthService();
        Call<HttpResponse> call = accountService.register(phone, vcode, password, inviteCode);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        JsonObject data = httpResponse.data.getAsJsonObject();
                        Gson gson = new Gson();
                        MNLApplication.getCacheManager().put("token", gson.fromJson(data.get("token"), String.class));
                        UserInfo user = gson.fromJson(data.get("user"), UserInfo.class);
                        MNLApplication.getCacheManager().put("user", user);
                        listener.onSuccess();
                    } else {
                        listener.onError(httpResponse.msg);
                    }
                } else {
                    listener.onError("注册失败");
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                listener.onError("注册失败");
            }
        });
    }
}
