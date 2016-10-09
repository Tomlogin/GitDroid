package zhuoxin.andriody.com.mygithub;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import zhuoxin.andriody.com.mygithub.commons.ActivityUtils;
import zhuoxin.andriody.com.mygithub.favorite.FavoriteFragment;
import zhuoxin.andriody.com.mygithub.gank.GankFragment;
import zhuoxin.andriody.com.mygithub.github.HotRepoFragment;
import zhuoxin.andriody.com.mygithub.github.hotuser.HotUserFragment;
import zhuoxin.andriody.com.mygithub.login.LoginActivity;
import zhuoxin.andriody.com.mygithub.login.model.UserRepo;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.navigationView)
    NavigationView navigationView;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    private Button btnLogin;
    private ImageView ivIcon;

    private ActivityUtils activityUtils;
    private HotRepoFragment hotRepoFragment;
    private HotUserFragment hotUserFragment;
    private GankFragment gankFragment;
    private FavoriteFragment favoriteFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 会触发onContentChanged方法
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);

        // 处理toolbar作为ActionBar
        setSupportActionBar(toolbar);
        //设置侧滑栏的每一项点击事件
        navigationView.setNavigationItemSelectedListener(this);
        //设置侧滑栏的点击事件(实现侧滑)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);

        btnLogin = ButterKnife.findById(navigationView.getHeaderView(0), R.id.btnLogin);
        ivIcon = ButterKnife.findById(navigationView.getHeaderView(0), R.id.ivIcon);
        //登录的点击事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.startActivity(LoginActivity.class);

            }
        });

        hotRepoFragment = new HotRepoFragment();
        replaceFragment(hotRepoFragment);
    }
    //每一个事务都是同时要执行的一套变化.可以在一个给定的事务中设置你想执行的所有变化,使用诸如 add(),
    // remove(), 和 replace().然后, 要给activity应用事务, 必须调用 commit().
    private void replaceFragment(Fragment fragment){
        //获取fragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //通过fragmentManager开启一个事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //通过事物更换fragment
        fragmentTransaction.replace(R.id.container,fragment);
        //提交
        fragmentTransaction.commit();

    }
    @Override
    protected void onStart() {
        super.onStart();

        if (UserRepo.isEmpty()){
            btnLogin.setText(R.string.login_github);
            return;
        }
        // 切换账号按钮
        btnLogin.setText(R.string.switch_account);
        //Main页面的标题
        getSupportActionBar().setTitle(UserRepo.getUser().getLogin());

        // 设置头像
        ImageLoader.getInstance().displayImage(UserRepo.getUser().getAvatar(),ivIcon);
    }
//侧滑栏的每一项点击事件的实现
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if (item.isChecked()){
            item.setChecked(false);
        }

        // 根据不同的item的id来进行切换
        switch (item.getItemId()){

            // 最热门
            case R.id.github_hot_repo:
                if (!hotRepoFragment.isAdded()) {
                    replaceFragment(hotRepoFragment);
                }
                break;
            //开发者
            case R.id.github_hot_coder:
                if (hotUserFragment == null)
                    hotUserFragment = new HotUserFragment();
                if (!hotUserFragment.isAdded()) {
                    replaceFragment(hotUserFragment);
                }
                break;
            // 每日干货
            case R.id.tips_daily:
                if (gankFragment==null){
                    gankFragment = new GankFragment();
                }
                if (!gankFragment.isAdded()){
                    replaceFragment(gankFragment);
                }
                break;
            // 我的收藏
            case R.id.arsenal_my_repo:
                if (favoriteFragment==null){
                    favoriteFragment = new FavoriteFragment();
                }
                if (!favoriteFragment.isAdded()){
                    replaceFragment(favoriteFragment);
                }
                break;
        }

        // 关闭drawerLayout

        drawerLayout.closeDrawer(GravityCompat.START);

        // 返回true，代表将该菜单项变为checked状态
        return true;
    }
}
