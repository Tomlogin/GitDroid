package zhuoxin.andriody.com.mygithub.login;

/**
 * Created by 123 on 2016/10/8.
 */
public interface LoginView {

    /**
     * 1. 显示信息
     * 2. 显示正在加载
     * 3. 重新请求
     * 4. 跳转到主页面
     */

    void showMessage(String msg);

    void showProgress();

    void resetWebView();

    void navigationToMain();
}
