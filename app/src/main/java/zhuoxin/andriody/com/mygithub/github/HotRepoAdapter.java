package zhuoxin.andriody.com.mygithub.github;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



import java.util.List;

import zhuoxin.andriody.com.mygithub.github.repoList.RepoListFragment;

/**
 * Created by 123 on 2016/9/29.
 */
public class HotRepoAdapter extends FragmentPagerAdapter{

    private List<Language> list;//数据源

    public HotRepoAdapter(FragmentManager fm, Context context) {
        super(fm);
        //构造的时候初始化数据源
        list = Language.getLanguages(context);
    }

    @Override
    public Fragment getItem(int position) {
        return RepoListFragment.getInstance(list.get(position));
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }
}
