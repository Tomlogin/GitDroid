package zhuoxin.andriody.com.mygithub.github.repoinfo;

/**
 * Created by Administrator on 10/8 0008.
 */
public interface repoInfoView {
    /**
     * 1.显示进度条
     * 2.隐藏进度条
     * 3.显示信息
     * 4.加载完数据，显示数据
     */

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void setData(String htmlContent);
}
