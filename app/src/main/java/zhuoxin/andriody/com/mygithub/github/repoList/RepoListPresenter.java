package zhuoxin.andriody.com.mygithub.github.repoList;



import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhuoxin.andriody.com.mygithub.github.Language;
import zhuoxin.andriody.com.mygithub.github.repoList.model.Repo;
import zhuoxin.andriody.com.mygithub.github.repoList.model.RepoResult;
import zhuoxin.andriody.com.mygithub.github.repoList.view.RepoListView;
import zhuoxin.andriody.com.mygithub.network.GithubClient;

/**
 * Created by 123 on 2016/9/30.
 */

// 业务类，主要做数据的请求
public class RepoListPresenter {
    private Language language;
    private int nextPage = 1;
    private RepoListView repoView;

    public RepoListPresenter(RepoListView repoView,Language language) {
        this.repoView = repoView;
        this.language = language;
    }

    // 刷新的业务
    public void refresh(){
        // 显示刷新视图
        repoView.showContentView();
        nextPage = 1;
        Call<RepoResult> refreshCall = GithubClient.getInstance().searchRepos("language:" + language.getPath(), nextPage);
        refreshCall.enqueue(refreshCallback);
    }

    // 加载的业务
    public void loadMore(){

        repoView.showLoadingView();

        Call<RepoResult> refreshCall = GithubClient.getInstance().searchRepos("language:" + language.getPath(), nextPage);
        //执行数据请求
        refreshCall.enqueue(LoadMoreCallback);
    }

    private Callback<RepoResult> LoadMoreCallback = new Callback<RepoResult>() {

        // 响应成功
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {

            repoView.hideLoadView();

            if (response.isSuccessful()){

                RepoResult result = response.body();

                // 如果为空
                if (result==null){
                    repoView.showLoadError();
                    return;
                }

                // 结果不为空
                if (result.getTotalCount()<=0){
                    // 里面的仓库数据是空的
                    repoView.showMessage("没有更多数据");
                    return;
                }

                List<Repo> repoList = result.getRepoList();
                if (repoList!=null){
                    // 设置加载出来的数据
                    repoView.addLoadData(repoList);
                    nextPage++;
                }
            }
        }

        // 失败
        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            repoView.hideLoadView();
            repoView.showMessage("响应失败"+t.getMessage());
        }
    };

    private Callback<RepoResult> refreshCallback = new Callback<RepoResult>() {

        // 响应成功
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            // 停止刷新
            repoView.stopRefresh();

            // 响应成功
            if (response.isSuccessful()){
                RepoResult repoResult = response.body();

                // 判断数据是不是空的
                if (repoResult==null){
                    repoView.showEmptyView();
                    return;
                }

                if (repoResult.getTotalCount()<=0){
                    repoView.showEmptyView();
                    repoView.refreshData(null);
                    return;
                }
                // 有数据，设置数据
                List<Repo> repoList = repoResult.getRepoList();
                if (repoList!=null){
                    repoView.refreshData(repoList);// 设置刷新的数据
                    // 下拉刷新完之后，第一页数据请求完成，下次上拉加载，从第二页开始
                    nextPage = 2;
                    return;
                }
            }
            repoView.showErrorView();
        }

        // 失败
        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            repoView.stopRefresh();
            repoView.showMessage("请求失败"+t.getMessage());
        }
    };
}
