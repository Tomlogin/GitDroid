package zhuoxin.andriody.com.mygithub.github;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import butterknife.Bind;
import butterknife.ButterKnife;
import zhuoxin.andriody.com.mygithub.R;

public class HotRepoFragment extends Fragment {

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_hot_repo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        //创建一个Adapter
        HotRepoAdapter adapter = new HotRepoAdapter(getChildFragmentManager(),getContext());
        //设置adapter
        viewPager.setAdapter(adapter);
        //设置tabLayout和viewPager滑动同步
        tabLayout.setupWithViewPager(viewPager);
    }
}
