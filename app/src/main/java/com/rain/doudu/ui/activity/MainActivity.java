package com.rain.doudu.ui.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.rain.doudu.R;
import com.rain.doudu.bean.http.baidu.Person;
import com.rain.doudu.bean.http.jiangjianyu.User;
import com.rain.doudu.common.URL;
import com.rain.doudu.ui.fragment.AboutAppFragment;
import com.rain.doudu.ui.fragment.BaiduMessageFragment;
import com.rain.doudu.ui.fragment.BaseFragment;
import com.rain.doudu.ui.fragment.BookCollectionFragment;
import com.rain.doudu.ui.fragment.BookWebFragment;
import com.rain.doudu.ui.fragment.DiaryFragment;
import com.rain.doudu.ui.fragment.HomeFragment;
import com.rain.doudu.ui.fragment.MyMessageFragment;
import com.rain.doudu.ui.fragment.MyReviewFragment;
import com.rain.doudu.ui.holder.SearchViewHolder;
import com.rain.doudu.utils.common.KeyBoardUtils;
import com.rain.doudu.utils.common.ScreenUtils;
import com.rain.doudu.utils.common.ToastUtils;
import com.rain.doudu.utils.customtabs.CustomTabActivityHelper;
import com.rain.doudu.utils.customtabs.GlideCircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int EXIT_APP_DELAY = 1000;
    private static final String LOGIN_USER = "LOGIN_USER";


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    private FloatingActionButton mFab;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private BaseFragment currentFragment;
    private int currentIndex;
    private SwitchCompat mThemeSwitch;
    private PopupWindow mPopupWindow;
    private SearchViewHolder holder;
    private long lastTime = 0;
    private User mUser;
    private Person mPerson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        if (savedInstanceState == null) {
            currentFragment = HomeFragment.newInstance();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.fl_content, currentFragment).commit();
        } else {
            //activity销毁后记住销毁前所在页面
            currentIndex = savedInstanceState.getInt("currentIndex");
            switch (this.currentIndex) {
                case 0:
                    if (currentFragment == null) {
                        currentFragment = HomeFragment.newInstance();
                    }
                    switchContent(null, currentFragment);
                    break;
                case 1:
                    if (currentFragment == null) {
                        currentFragment = BookCollectionFragment.newInstance();
                    }
                    switchContent(null, currentFragment);
                    break;

            }
        }


        //登录
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        TextView textView;
        ImageView imageView;
        textView = (TextView) headerView.findViewById(R.id.myMsg);
        imageView = (ImageView) headerView.findViewById(R.id.myHead);
        mUser = (User) getIntent().getSerializableExtra(LOGIN_USER);
        mPerson = (Person) getIntent().getSerializableExtra("BAIDUER");
        if (mUser == null & mPerson == null) {
            imageView.setVisibility(View.GONE);
            Button mToLoginButton = (Button) headerView.findViewById(R.id.toLogin);
            mToLoginButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            });
        } else {
            Button mToLoginButton = (Button) headerView.findViewById(R.id.toLogin);
            mToLoginButton.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            if (mUser != null) {
                textView.setText(mUser.getMyName());
            } else {
                textView.setText(mPerson.getUsername());
                Glide.with(this)
                        .load(URL.BAIDU_SMALL_IMAGE + mPerson.getPortrait())
                        .placeholder(R.drawable.baidu_logo)
                        .transform(new GlideCircleTransform(getApplicationContext()))
                        .into(imageView);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initEvents() {

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == null) {
                currentFragment = HomeFragment.newInstance();
            }
            if (!(currentFragment instanceof HomeFragment)) {
                switchContent(currentFragment, HomeFragment.newInstance());
                mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                return;
            }
            if ((System.currentTimeMillis() - lastTime) > EXIT_APP_DELAY) {
                Snackbar.make(drawer, getString(R.string.press_twice_exit), Snackbar.LENGTH_SHORT)
                        .setAction(R.string.exit_directly, v -> {
                            MainActivity.super.onBackPressed();
                        })
                        .show();
                lastTime = System.currentTimeMillis();
            } else {
                moveTaskToBack(true);
            }
        }
    }

    public void setToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            mToolbar = toolbar;
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            mNavigationView.setNavigationItemSelectedListener(this);
        }
    }


    /**
     * 搜索框
     */
    public void showSearchView() {
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (mPopupWindow == null) {
            holder = new SearchViewHolder(this, code -> {
                switch (code) {
                    case SearchViewHolder.RESULT_SEARCH_EMPTY_KEYWORD:
                        Snackbar.make(drawer, R.string.keyword_is_empty, Snackbar.LENGTH_SHORT).show();
                        break;
                    case SearchViewHolder.RESULT_SEARCH_SEARCH:
                        String q = holder.et_search_content.getText().toString();
                        if (q.startsWith("@")) {
                            CustomTabActivityHelper.openCustomTab(
                                    this,
                                    new CustomTabsIntent.Builder()
                                            .setShowTitle(true)
                                            .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                                            .addDefaultShareMenuItem()
                                            .build(),
                                    Uri.parse(q.replace("@", "")));
                        } else {
                            Intent intent = new Intent(this, SearchResultActivity.class);
                            intent.putExtra("q", q);
                            startActivity(intent);
                        }
                        break;

                    case SearchViewHolder.RESULT_SEARCH_CANCEL:
                        mPopupWindow.dismiss();
                        break;
                }
            });
            mPopupWindow = new PopupWindow(holder.getContentView(),
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
            // 设置popWindow的显示和消失动画
//                mPopupWindow.setAnimationStyle(R.style.PopupWindowStyle);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    holder.et_search_content.setText("");
                    KeyBoardUtils.closeKeyBord(holder.et_search_content, MainActivity.this);
                    ValueAnimator animator = ValueAnimator.ofFloat(0.7f, 1f);
                    animator.setDuration(500);
                    animator.addUpdateListener(animation -> {
                        lp.alpha = (float) animation.getAnimatedValue();
                        lp.dimAmount = (float) animation.getAnimatedValue();
                        getWindow().setAttributes(lp);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    });
                    animator.start();
                }
            });
            mPopupWindow.setTouchInterceptor((v, event) -> false);
        }
        KeyBoardUtils.openKeyBord(holder.et_search_content, MainActivity.this);
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.7f);
        animator.setDuration(500);
        animator.addUpdateListener(animation -> {
            lp.alpha = (float) animation.getAnimatedValue();
            lp.dimAmount = (float) animation.getAnimatedValue();
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        });
        mPopupWindow.showAtLocation(mToolbar, Gravity.NO_GRAVITY, 0, ScreenUtils.getStatusHeight(activity));
        animator.start();
    }

    public void showAddDiaryView() {
        Intent intent = new Intent(MainActivity.this, DiaryAddActivity.class);
        intent.putExtra("USER",mUser);
        startActivity(intent);
    }

    /**
     * switch fragment.
     *
     * @param from
     * @param to
     */

    public void switchContent(BaseFragment from, BaseFragment to) {
        if (currentFragment == to) {
            return;
        } else {
            currentFragment = to;
            //添加渐隐渐现的动画
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out);
            ft.replace(R.id.fl_content, to).commit();
        }
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        int menuId = R.menu.main;
        if (currentFragment instanceof HomeFragment) {
            menuId = R.menu.main;
        } else if (currentFragment instanceof BookCollectionFragment) {
            menuId = R.menu.menu_empty;
        } else if (currentFragment instanceof DiaryFragment) {
            menuId = R.menu.menu_add_diary;
        } else if (currentFragment instanceof MyReviewFragment) {
            menuId = R.menu.menu_empty;
        } else if (currentFragment instanceof AboutAppFragment) {
            menuId = R.menu.menu_empty;
        } else if (currentFragment instanceof MyMessageFragment) {
            menuId = R.menu.menu_empty;
        } else if (currentFragment instanceof BookWebFragment) {
            menuId = R.menu.menu_empty;
        }else if (currentFragment instanceof BaiduMessageFragment) {
            menuId = R.menu.menu_empty;
        }
        getMenuInflater().inflate(menuId, menu);
        currentFragment.onCreateOptionsMenu(menu, getMenuInflater());
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            showSearchView();
            return true;
        }
        if (id == R.id.add_diary) {
            showAddDiaryView();
            return true;
        }
        currentFragment.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentIndex", currentIndex);
        super.onSaveInstanceState(outState);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            switchContent(currentFragment, HomeFragment.newInstance());
        } else if (id == R.id.nav_ebook) {
            switchContent(currentFragment, BookCollectionFragment.newInstance());
        } else if (id == R.id.nav_book) {
            switchContent(currentFragment, BookWebFragment.newInstance());
        } else if (id == R.id.nav_bookshelf) {
            switchContent(currentFragment, MyReviewFragment.newInstance());
        } else if (id == R.id.nav_manage) {
            if (mUser == null & mPerson == null) {
                ToastUtils.showShort("请先登录！");
            } else {
                switchContent(currentFragment, DiaryFragment.newInstance());
            }
        } else if (id == R.id.nav_theme) {

            if (mUser == null & mPerson == null) {
                ToastUtils.showShort("请先登录！");
            } else {
                if (mUser != null) {
                    switchContent(currentFragment, MyMessageFragment.newInstance());
                } else {
                    switchContent(currentFragment, BaiduMessageFragment.newInstance());
                }
            }
        } else if (id == R.id.nav_send) {
            switchContent(currentFragment, AboutAppFragment.newInstance());
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Intent newIntent(Context packageContext, User u) {
        Intent i = new Intent(packageContext, MainActivity.class);
        i.putExtra(LOGIN_USER, u);
        return i;
    }

}
