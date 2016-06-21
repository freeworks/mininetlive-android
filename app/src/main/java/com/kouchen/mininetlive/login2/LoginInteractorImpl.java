package com.kouchen.mininetlive.login2;

import android.util.Log;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.rest.service.AuthService;
import com.kouchen.mininetlive.rest.service.base.HttpBinResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractorImpl implements LoginInteractor {

    public static final String TAG = LoginInteractorImpl.class.getSimpleName();

    @Override
    public void login(final OnLoginFinishedListener listener, final Platform platform,
        final String... args) {

        Call<HttpBinResponse> call = null;
        if (platform == null) {
            if (args.length < 2) {
                listener.onError("少参数");
                return;
            }
            loginForPhone(listener, args);
        } else {
            authorize(platform.getName(), listener);
        }
    }

    private void loginForPhone(final OnLoginFinishedListener listener, String[] args) {
        AuthService accountService = MNLApplication.getRestClient().getAccountService();
        Call<HttpBinResponse> call;
        call = accountService.login(args[0], args[1]);
        // Asynchronously execute HTTP request
        call.enqueue(new Callback<HttpBinResponse>() {
            @Override
            public void onResponse(Call<HttpBinResponse> call, Response<HttpBinResponse> response) {
                // http response status code + headers
                System.out.println("Response status code: " + response.code());
                // isSuccess is true if response code => 200 and <= 300
                if (!response.isSuccess()) {
                    // print response body if unsuccessful
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        // do nothing
                    }
                    return;
                }

                // if parsing the JSON body failed, `response.body()` returns null
                HttpBinResponse decodedResponse = response.body();
                if (decodedResponse == null) return;

                // at this point the JSON body has been successfully parsed
                System.out.println("Response (contains request infos):");
                System.out.println("- url:         " + decodedResponse.url);
                System.out.println("- ip:          " + decodedResponse.origin);
                System.out.println("- headers:     " + decodedResponse.headers);
                System.out.println("- args:        " + decodedResponse.args);
                System.out.println("- form params: " + decodedResponse.form);
                System.out.println("- json params: " + decodedResponse.json);

//                boolean error = false;
//                if (TextUtils.isEmpty(username)){
//                    listener.onUsernameError();
//                    error = true;
//                }
//                if (TextUtils.isEmpty(password)){
//                    listener.onPasswordError();
//                    error = true;
//                }
//                if (!error){
//                    listener.onSuccess();
//                }

                listener.onSuccess();
            }

            @Override
            public void onFailure(Call<HttpBinResponse> call, Throwable t) {
                System.out.println("onFailure");
                System.out.println(t.getMessage());
            }
        });
    }

    public void authorize(String platformName,final OnLoginFinishedListener listener){
        Platform plat = ShareSDK.getPlatform(platformName);
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
            public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
                if (action == Platform.ACTION_USER_INFOR) {
                    AuthService accountService = MNLApplication.getRestClient().getAccountService();
//                    plat.getDb().getUserId(), plat.getDb().getToken(),
//                            plat.getDb().getExpiresIn());
                    Call<HttpBinResponse> call = accountService.oauthLogin(plat.getName(),
                            res.get("openid").toString(),
                            res.get("access_token").toString(),
                            Long.valueOf(res.get("expires_in").toString()));
                    call.enqueue(new Callback<HttpBinResponse>(){

                        @Override
                        public void onResponse(Call<HttpBinResponse> call, Response<HttpBinResponse> response) {
                            if (response.isSuccess()){
                                HttpBinResponse decodedResponse = response.body();
                                if (decodedResponse == null) {
                                    return;
                                }
                                Map json = decodedResponse.json;
                                //TODO
                                listener.onToRegister();

                            }
                        }

                        @Override
                        public void onFailure(Call<HttpBinResponse> call, Throwable t) {
                            //TODO
                        }
                    } );
                }
            }

            public void onError(Platform plat, int action, Throwable t) {
                if (action == Platform.ACTION_USER_INFOR) {
                    //
                }
                Log.e(TAG,"",t);
            }

            public void onCancel(Platform plat, int action) {
                if (action == Platform.ACTION_USER_INFOR) {

                }
            }
        });
        plat.showUser(null);
    }
}
