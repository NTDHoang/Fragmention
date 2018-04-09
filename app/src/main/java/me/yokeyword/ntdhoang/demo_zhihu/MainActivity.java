package me.yokeyword.ntdhoang.demo_zhihu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;
import java.util.List;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.ntdhoang.R;
import me.yokeyword.ntdhoang.demo_zhihu.assistant.event.TabSelectedEvent;
import me.yokeyword.ntdhoang.demo_zhihu.assistant.helper.DownloadFile;
import me.yokeyword.ntdhoang.demo_zhihu.assistant.rx2.DisposableManager;
import me.yokeyword.ntdhoang.demo_zhihu.base.BaseMainFragment;
import me.yokeyword.ntdhoang.demo_zhihu.ui.fragment.first.ZhihuFirstFragment;
import me.yokeyword.ntdhoang.demo_zhihu.ui.fragment.first.child.FirstHomeFragment;
import me.yokeyword.ntdhoang.demo_zhihu.ui.fragment.fourth.ZhihuFourthFragment;
import me.yokeyword.ntdhoang.demo_zhihu.ui.fragment.fourth.child.MeFragment;
import me.yokeyword.ntdhoang.demo_zhihu.ui.fragment.second.ZhihuSecondFragment;
import me.yokeyword.ntdhoang.demo_zhihu.ui.fragment.second.child.ViewPagerFragment;
import me.yokeyword.ntdhoang.demo_zhihu.ui.fragment.third.ZhihuThirdFragment;
import me.yokeyword.ntdhoang.demo_zhihu.ui.fragment.third.child.child.OtherPagerFragment;
import me.yokeyword.ntdhoang.demo_zhihu.ui.view.BottomBar;
import me.yokeyword.ntdhoang.demo_zhihu.ui.view.BottomBarTab;
import org.greenrobot.eventbus.EventBus;

/**
 * 类知乎 复杂嵌套Demo tip: 多使用右上角的"查看栈视图"
 * Created by YoKeyword on 16/6/2.
 */
public class MainActivity extends SupportActivity
        implements BaseMainFragment.OnBackToFirstListener, OnRequestPermissionsResultCallback {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    public static final int REQUEST_CODE = 1001;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private BottomBar mBottomBar;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("BINH", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            loader();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihu_activity_main);
        //        EventBus.getDefault().register(this);

        SupportFragment firstFragment = findFragment(ZhihuFirstFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = ZhihuFirstFragment.newInstance();
            mFragments[SECOND] = ZhihuSecondFragment.newInstance();
            mFragments[THIRD] = ZhihuThirdFragment.newInstance();
            mFragments[FOURTH] = ZhihuFourthFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST, mFragments[FIRST],
                    mFragments[SECOND], mFragments[THIRD], mFragments[FOURTH]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.findFragmentByTag()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(ZhihuSecondFragment.class);
            mFragments[THIRD] = findFragment(ZhihuThirdFragment.class);
            mFragments[FOURTH] = findFragment(ZhihuFourthFragment.class);
        }

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO

        if (Build.VERSION.SDK_INT >= 23) {
            //do your check here
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("BINH", "Permission is granted");
                loader();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_CODE);
            }
        }
    }

    private void initView() {
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);

        mBottomBar.addItem(new BottomBarTab(this, R.drawable.ic_home_white_24dp))
                .addItem(new BottomBarTab(this, R.drawable.ic_discover_white_24dp))
                .addItem(new BottomBarTab(this, R.drawable.ic_message_white_24dp))
                .addItem(new BottomBarTab(this, R.drawable.ic_account_circle_white_24dp));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                final SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

                // 如果不在该类别Fragment的主页,则回到主页;
                if (count > 1) {
                    if (currentFragment instanceof ZhihuFirstFragment) {
                        currentFragment.popToChild(FirstHomeFragment.class, false);
                    } else if (currentFragment instanceof ZhihuSecondFragment) {
                        currentFragment.popToChild(ViewPagerFragment.class, false);
                    } else if (currentFragment instanceof ZhihuThirdFragment) {
                        currentFragment.popToChild(OtherPagerFragment.class, false);
                    } else if (currentFragment instanceof ZhihuFourthFragment) {
                        currentFragment.popToChild(MeFragment.class, false);
                    }
                    return;
                }

                // 这里推荐使用EventBus来实现 -> 解耦
                if (count == 1) {
                    // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
                    // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                    EventBus.getDefault().post(new TabSelectedEvent(position));
                }
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }

    /**
     * 这里暂没实现,忽略
     */
    //    @Subscribe
    //    public void onHiddenBottombarEvent(boolean hidden) {
    //        if (hidden) {
    //            mBottomBar.hide();
    //        } else {
    //            mBottomBar.show();
    //        }
    //    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //        EventBus.getDefault().unregister(this);
    }

    private void loader() {
        List<String> tempString = Arrays.asList(
                "https://images.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg?auto=compress&cs=tinysrgb&h=350",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Cat03.jpg/1200px-Cat03.jpg");

        DisposableManager.add(Observable.defer(() -> Observable.just(tempString))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<String>>() {
                    @Override
                    public void onComplete() {
                        Log.d("BINH", "onComplete() called");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<String> list) {
                        for (String m : list) {
                            new DownloadFile(MainActivity.this).execute(m);
                            Log.d("BINH", "onNext() called with: list = [" + m + "]");
                        }
                    }
                }));
    }
}
