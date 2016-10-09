package zhuoxin.andriody.com.mygithub.github.repoList.view;



import java.util.List;

import zhuoxin.andriody.com.mygithub.github.repoList.model.Repo;

/**
 * Created by 123 on 2016/9/30.
 */
public interface RepoLoadView {

    void showLoadingView();
    void hideLoadView();
    void showLoadError();
    void showMessage(String msg);
    void addLoadData(List<Repo> list);

}
