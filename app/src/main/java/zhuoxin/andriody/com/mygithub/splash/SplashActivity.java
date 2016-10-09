package zhuoxin.andriody.com.mygithub.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import zhuoxin.andriody.com.mygithub.MainActivity;
import zhuoxin.andriody.com.mygithub.R;
import zhuoxin.andriody.com.mygithub.commons.ActivityUtils;
import zhuoxin.andriody.com.mygithub.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private ActivityUtils activityUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activityUtils = new ActivityUtils(this);
        ButterKnife.bind(this);

    }
         //登录
    @OnClick(R.id.btnLogin) void login() {
        activityUtils.startActivity(LoginActivity.class);
        finish();
    }
     //直接使用
    @OnClick(R.id.btnEnter) void enter() {
        activityUtils.startActivity(MainActivity.class);
        finish();
    }
}
