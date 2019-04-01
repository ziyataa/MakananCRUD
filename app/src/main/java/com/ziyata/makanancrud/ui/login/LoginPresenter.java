package com.ziyata.makanancrud.ui.login;

import android.content.Context;

import com.ziyata.makanancrud.data.remote.ApiClient;
import com.ziyata.makanancrud.data.remote.ApiInterface;
import com.ziyata.makanancrud.model.login.login.LoginData;
import com.ziyata.makanancrud.model.login.login.LoginResponse;
import com.ziyata.makanancrud.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter{

    private final LoginContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private SessionManager msessionManager;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void doLogin(String username, String password) {
        // Mengecek username dan password
        if (username.isEmpty()){
            view.usernameError("Username is Empety");
            return;
        }

        if (password.isEmpty()){
            view.passwordError("Password is Empety");
            return;
        }
        view.showProgress();
        Call<LoginResponse> call = apiInterface.loginUser(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        if (response.body().getData() != null) {
                            LoginData loginData = response.body().getData();
                            String message = response.body().getMessage();
                            view.loginSuccess(message, loginData);
                        } else {
                            view.loginFailure("Data tidak ada");
                        }
                    } else {
                        view.loginFailure(response.body().getMessage());
                    }
                }else {
                    view.loginFailure("Data tidak ada");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideProgress();
                view.loginFailure(t.getMessage());
            }
        });
    }

    @Override
    public void saveDataUser(Context context, LoginData loginData) {
        // Membuat object SessionManager
        msessionManager = new SessionManager(context);
        // Mengesave data ke SharedPreference dengan menggunakan method dari class SessionManager
        msessionManager.createSession(loginData);
    }

    @Override
    public void checkLogin(Context context) {
        // Membuat object Sessionmanager
        msessionManager = new SessionManager(context);
        // Mengambbil data KEY_IS_LOGIN bernilai true
        Boolean isLogin = msessionManager.isLogin();
        // Mengecek apakah KEY_IS_LOGIN bnernilai true
        if (isLogin){
            // Menyuruh view untuk melakukan perpindahan ke MainActivity
            view.isLogin();
        }

    }
}
