package zhuoxin.andriody.com.mygithub.login;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;
import zhuoxin.andriody.com.mygithub.MainActivity;
import zhuoxin.andriody.com.mygithub.R;
import zhuoxin.andriody.com.mygithub.commons.ActivityUtils;
import zhuoxin.andriody.com.mygithub.github.HotRepoFragment;
import zhuoxin.andriody.com.mygithub.network.GithubApi;


/**
 * 1.使用WebView来加载登录网址，实现登录账户的填写及登录
 * 2.是否同意授权，如果同意授权，重定向另一个URL，会有一个临时授权码code
 * 3.拿到临时授权码之后，使用API来获得用户Token
 * 4.获得用户Token之后，访问user,public_repo,repo，主要为了拿到用户信息展示出来
 */
public class LoginActivity extends AppCompatActivity implements LoginView{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.gifImageView)
    GifImageView gifImageView;
    private ActivityUtils activityUtils;

    private LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this);
        activityUtils = new ActivityUtils(this);
        setSupportActionBar(toolbar);
        //给toolbar设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              activityUtils.startActivity(MainActivity.class);
            }
        });
        initWebview();
    }
    //设置Webview
    private void initWebview() {
        // 删除所有的Cookie, 主要是为了清除登录信息
        CookieManager manager=CookieManager.getInstance();
        manager.removeAllCookie();
        //加载登录网页
        webView.loadUrl(GithubApi.AUTH_URL);
        //让webview获取焦点
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        //给webview设置监听，加载完成之前显示动画，完成之后隐藏动画
        webView.setWebChromeClient(webChromeClient);
        //在webview加载并监听webview的页面刷新
        webView.setWebViewClient(webviewClient);
    }
    private WebViewClient webviewClient=new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri=Uri.parse(url);
            if(GithubApi.CALL_BACK.equals(uri.getScheme())){
             String code = uri.getQueryParameter("code");
           presenter.login(code);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };
    private WebChromeClient webChromeClient=new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if(newProgress>=100){
                gifImageView.setVisibility(View.GONE);
            }
        }
    };
    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void showProgress() {
        gifImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void resetWebView() {
        initWebview();
    }

    @Override
    public void navigationToMain() {
        activityUtils.startActivity(MainActivity.class);
        finish();
    }


}
