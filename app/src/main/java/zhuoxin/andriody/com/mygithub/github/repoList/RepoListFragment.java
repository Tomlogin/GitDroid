package zhuoxin.andriody.com.mygithub.github.repoList;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import zhuoxin.andriody.com.mygithub.R;
import zhuoxin.andriody.com.mygithub.commons.ActivityUtils;
import zhuoxin.andriody.com.mygithub.components.FooterView;
import zhuoxin.andriody.com.mygithub.github.Language;
import zhuoxin.andriody.com.mygithub.github.repoList.model.Repo;
import zhuoxin.andriody.com.mygithub.github.repoList.view.RepoListView;
import zhuoxin.andriody.com.mygithub.github.repoinfo.RepoInfoActivity;

public class RepoListFragment extends Fragment implements RepoListView {

    @Bind(R.id.lvRepos)
    ListView lvRepos;
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrFrameLayout;
    @Bind(R.id.emptyView)
    TextView emptyView;
    @Bind(R.id.errorView)
    TextView errorView;

    private ActivityUtils activityUtils;

    private static final String KEY_LABGUAGE = "key_language";
    private RepoListAdapter adapter;

    private RepoListPresenter presenter;
    private FooterView footerView;

    // 提供一个创建方法，进行数据（Language）的传递。
    public static RepoListFragment getInstance(Language language) {

        RepoListFragment repoListFragment = new RepoListFragment();

        // Bundle 传递数据
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_LABGUAGE, language);
        repoListFragment.setArguments(bundle);
        return repoListFragment;
    }

    private Language getLanguage() {
        return (Language) getArguments().getSerializable(KEY_LABGUAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_repo_list, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activityUtils = new ActivityUtils(this);
        presenter = new RepoListPresenter(this, getLanguage());

        adapter = new RepoListAdapter();
        lvRepos.setAdapter(adapter);

        lvRepos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Repo repo = adapter.getItem(position);
                RepoInfoActivity.open(getContext(), repo);
            }
        });

        // 判断没有数据去自动刷新
        if (adapter.getCount() == 0) {
            ptrFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 自动刷新的方法
                    ptrFrameLayout.autoRefresh();

                }
            }, 200);
        }

        // 初始化下拉刷新
        initPullToPresh();

        // 上拉加载的事件

        /**
         * ListView 滑动到最后一条时，再进行滑动，触发加载
         * ListView 有一个尾部布局，显示正在加载视图
         * 加载完成之后，尾部布局移除
         */
        initLoadMore();

    }

    private void initLoadMore() {

        footerView = new FooterView(getContext());

        Mugen.with(lvRepos, new MugenCallbacks() {

            // 执行加载，上拉加载业务的请求
            @Override
            public void onLoadMore() {
                //执行业务来进行数据加载
                presenter.loadMore();
            }

            // 是不是正在加载
            @Override
            public boolean isLoading() {
                return footerView.isLoading() && lvRepos.getFooterViewsCount() > 0;
            }

            // 是不是加载完了所有的数据
            @Override
            public boolean hasLoadedAllItems() {
                return footerView.isComplete() && lvRepos.getFooterViewsCount() > 0;
            }
        }).start();
    }

    private void initPullToPresh() {

        // 刷新时间间隔比较短，不触发刷新
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);

        // 关闭Header所用时间
        ptrFrameLayout.setDurationToClose(1500);

        // 更改头布局
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.initWithString("I LIKE ANDROID");
        header.setPadding(0, 60, 0, 60);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);

        ptrFrameLayout.setBackgroundResource(R.color.colorRefresh);

        // 设置刷新的监听，做刷新的操作（去进行数据请求）
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {


            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                // 去做数据的请求
                presenter.refresh();
            }
        });

    }

    // --------------------刷新的视图------------------------------
    @Override
    public void stopRefresh() {
        ptrFrameLayout.refreshComplete();// 停止刷新
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void refreshData(List<Repo> repoList) {
        adapter.clear();
        adapter.addAll(repoList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showContentView() {
        ptrFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView() {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    //--------------------加载的视图实现-----------------------------
    @Override
    public void showLoadingView() {
        if (lvRepos.getFooterViewsCount() == 0) {
            lvRepos.addFooterView(footerView);
        }
        footerView.showLoading();
    }

    @Override
    public void hideLoadView() {
        lvRepos.removeFooterView(footerView);
    }

    @Override
    public void showLoadError() {
        if (lvRepos.getFooterViewsCount() == 0) {
            lvRepos.addFooterView(footerView);
        }
        footerView.showError();
    }

    @Override
    public void addLoadData(List<Repo> list) {
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
