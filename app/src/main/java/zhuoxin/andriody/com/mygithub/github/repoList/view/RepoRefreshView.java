package zhuoxin.andriody.com.mygithub.github.repoList.view;



import java.util.List;

import zhuoxin.andriody.com.mygithub.github.repoList.model.Repo;

/**
 * Created by 123 on 2016/9/30.
 */
public interface RepoRefreshView {

    // 停止刷新
    void stopRefresh();

    // 显示信息
    void showMessage(String msg);

    // 拿到刷新得到的数据
    void refreshData(List<Repo> repoList);

    // 显示空视图
    void showEmptyView();

    // 显示刷新视图
    void showContentView();

    // 显示错误视图
    void showErrorView();
}
