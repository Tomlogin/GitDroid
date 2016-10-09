package zhuoxin.andriody.com.mygithub.login;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhuoxin.andriody.com.mygithub.login.model.AccessToken;
import zhuoxin.andriody.com.mygithub.login.model.User;
import zhuoxin.andriody.com.mygithub.login.model.UserRepo;
import zhuoxin.andriody.com.mygithub.network.GithubApi;
import zhuoxin.andriody.com.mygithub.network.GithubClient;

/**
 * Created by Administrator on 10/8 0008.
 */
public class LoginPresenter {
    private LoginView loginView;
    private Call<AccessToken> tokenCall;
    private Call<User> userCall;
    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    /**
     * 登录的业务
     * 1. 使用code 获取Token
     */
    public void login(String code){
        loginView.showProgress();
        if(tokenCall!=null) {
            tokenCall.cancel();
        }
        // 利用code 去获取Token
      tokenCall= GithubClient.getInstance().getOAuthToken(GithubApi.CLIENT_ID,GithubApi.CLIENT_SECRET,code);
        tokenCall.enqueue(tokenCallback);
    }
    private Callback<AccessToken> tokenCallback=new Callback<AccessToken>() {
        private Call<User> userCall;
        @Override
        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
            if(response.isSuccessful()){
                // 得到Token值
                AccessToken accessToken  = response.body();
                String token = accessToken.getAccessToken();
                UserRepo.setAccessToken(token);
                if (userCall!=null){
                    userCall.cancel();
                }
                userCall = GithubClient.getInstance().getUser();
                userCall.enqueue(userCallback);
            }

        }

        @Override
        public void onFailure(Call<AccessToken> call, Throwable t) {
            // 请求失败
            loginView.showMessage(t.getMessage());
            loginView.resetWebView();// 重新去请求
            loginView.showProgress();
        }
    };
    private Callback<User> userCallback = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.isSuccessful()){

                // 将获取的用户信息保存
                User user = response.body();
                UserRepo.setUser(user);

                loginView.showMessage("登录成功");
                loginView.navigationToMain();

            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            // 请求失败
            loginView.showMessage(t.getMessage());
            loginView.resetWebView();
            loginView.showProgress();
        }
    };
}
