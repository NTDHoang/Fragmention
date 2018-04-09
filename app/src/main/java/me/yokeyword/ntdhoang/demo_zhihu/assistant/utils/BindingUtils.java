package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.adapters.ListenerUtil;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by phanvanlinh on 09/11/2016.
 */
public final class BindingUtils {

    private BindingUtils() {
        // No-op
    }

    public static void notifyChange(BaseObservable... obs) {
        for (BaseObservable o : obs) o.notifyChange();
    }

    @BindingAdapter({ "zoom" })
    public static void setZoom(View view, float scaleLevel) {
        view.setScaleX(scaleLevel);
        view.setScaleY(scaleLevel);
    }

    @BindingAdapter("vectorSrc")
    public static void setVectorSrc(ImageView imageView, Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    @BindingAdapter("vectorSrc")
    public static void setVectorSrc(ImageView imageView, @DrawableRes Integer drawableRes) {
        if (drawableRes == null) {
            return;
        }
        try {
            imageView.setImageDrawable(
                    AppCompatResources.getDrawable(imageView.getContext(), drawableRes));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    @BindingAdapter({ "android:vectorDrawableStart" })
    public static void setVectorDrawableStart(TextView textView, @DrawableRes int drawable) {
        try {
            Drawable d = ResourcesCompat.getDrawable(textView.getResources(), drawable,
                    textView.getContext().getTheme());
            Drawable[] drawables = textView.getCompoundDrawables();
            textView.setCompoundDrawablesWithIntrinsicBounds(d, drawables[1], drawables[2],
                    drawables[3]);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({ "offscreenPageLimit" })
    public static void setAdapter(ViewPager viewPager, int limit) {
        viewPager.setOffscreenPageLimit(limit);
    }

    @BindingAdapter({ "currentItem" })
    public static void setCurrentItem(ViewPager viewPager, int currentItem) {
        viewPager.setCurrentItem(currentItem);
    }

    @BindingAdapter({ "disableItemAnimation" })
    public static void setDisableItemAnimation(RecyclerView recyclerView, boolean isDisable) {
        if (!isDisable) return;
        recyclerView.setItemAnimator(null);
    }


    @BindingAdapter({ "src" })
    public static void setImageSrc(ImageView view, int src) {
        try {
            view.setImageDrawable(ContextCompat.getDrawable(view.getContext(), src));
        } catch (Resources.NotFoundException e) {
            view.setImageDrawable(null);
        }
    }

    @BindingConversion
    public static String convertBindableToString(BindableText bindableEditText) {
        return bindableEditText.get();
    }



    @BindingAdapter({ "setWebViewClient" })
    public static void setWebViewClient(WebView view, WebViewClient client) {
        view.setWebViewClient(client);
        view.getSettings().setJavaScriptEnabled(true);
    }

    @BindingAdapter({ "webChromeClient" })
    public static void setWebChromeClient(WebView view, WebChromeClient client) {
        view.setWebChromeClient(client);
    }

    @BindingAdapter({ "loadUrl" })
    public static void loadUrl(WebView view, String url) {
        view.loadUrl(url);
    }

    @BindingAdapter({ "movementMethod" })
    public static void setMovementMethod(TextView textView, String method) {
        switch (method) {
            case "link":
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "scroll":
                textView.setMovementMethod(new ScrollingMovementMethod());
                break;
        }
    }

    @BindingAdapter({ "setNestedScrollingEnabled" })
    public static void setNestedScrollingEnabled(RecyclerView view,
            boolean isNestedScrollingEnabled) {
        view.setNestedScrollingEnabled(isNestedScrollingEnabled);
    }

    @BindingAdapter("colorSchemeResources")
    public static void colorSchemeResources(SwipeRefreshLayout swipeRefreshLayout,
            int[] colorResIds) {
        swipeRefreshLayout.setColorSchemeColors(colorResIds);
    }

    @BindingAdapter({ "onClickSafely", "blockInMillis" })
    public static void setOnClickSafely(View view, final View.OnClickListener listener,
            final int blockTimeInMillis) {
        view.setOnClickListener(new View.OnClickListener() {
            private Blocker mBlocker = new Blocker();

            @Override
            public void onClick(View v) {
                if (!mBlocker.block(blockTimeInMillis)) listener.onClick(v);
            }
        });
    }

    @BindingAdapter({ "onClickSafely" })
    public static void setOnClickSafely(View view, final View.OnClickListener listener) {
        view.setOnClickListener(new View.OnClickListener() {
            private Blocker mBlocker = new Blocker();

            @Override
            public void onClick(View v) {
                if (!mBlocker.block()) listener.onClick(v);
            }
        });
    }


    @BindingAdapter({ "onSelected" })
    public static void setSelected(View view, boolean isSelected) {
        view.setSelected(isSelected);
    }

    @BindingAdapter({ "onGlobalLayout" })
    public static void setOnGlobalLayout(View view,
            ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (onGlobalLayoutListener == null) {
            return;
        }
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    @BindingAdapter({ "setOffscreenPageLimit" })
    public static void setOffscreenPageLimit(ViewPager viewPager, int quantity) {
        viewPager.setOffscreenPageLimit(quantity);
    }


    @BindingAdapter({ "text", "scrollToTop" })
    public static void setText(final TextView textView, final String content, boolean scrollToTop) {
        textView.setText(content);
        if (scrollToTop) {
            textView.setMovementMethod(new ScrollingMovementMethod());
            textView.scrollTo(0, 0);
        }
    }

    @BindingAdapter(value = { "pageChangedListener" })
    public static void setOnPageChangedListener(ViewPager view,
            ViewPager.OnPageChangeListener listener) {
        view.addOnPageChangeListener(listener);
    }

    @BindingAdapter({ "scrollToTop" })
    public static void setScrollToTop(RecyclerView recyclerView, boolean isScroll) {
        if (!isScroll) return;
        if (recyclerView == null) return;
        recyclerView.scrollToPosition(0);
    }

    @BindingAdapter({ "smoothScrollToTop" })
    public static void setSmoothScrollToTop(RecyclerView recyclerView, boolean isScroll) {
        if (!isScroll) return;
        if (recyclerView == null) return;
        recyclerView.smoothScrollToPosition(0);
    }

    @BindingAdapter({ "scrollToPosition", "offset" })
    public static void setScrollToPosition(final RecyclerView recyclerView, final int position,
            final float offset) {
        if (recyclerView == null || recyclerView.getAdapter() == null) return;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(
                    position, -recyclerView.getPaddingTop() + (int) offset);
        }
    }



    @BindingAdapter({ "setUpWithViewPager", "isViewPagerAdapterChanged" })
    public static void setUpWithViewpager(final TabLayout tabLayout, ViewPager viewPager,
            boolean isViewPagerAdapterChanged) {
        if (isViewPagerAdapterChanged) {
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @BindingAdapter(value = { "pageChangeListener" })
    public static void setPageChangeListener(ViewPager viewPager,
            ViewPager.OnPageChangeListener listener) {
        viewPager.addOnPageChangeListener(listener);
    }

    /**
     * Use this attribute instead of <code>android:tint</code> because <code>DataBinding</code> only
     * supports <code>android:tint</code> from API 21+
     */
    @BindingAdapter(value = { "tintColor" })
    public static void setTintColor(ImageView imageView, @ColorRes int colorId) {
        imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), colorId));
    }




    @InverseBindingAdapter(attribute = "refreshing", event = "refreshingAttrChanged")
    public static boolean isSwipeRefreshLayoutRefreshing(SwipeRefreshLayout view) {
        return view.isRefreshing();
    }

    @BindingAdapter("refreshing")
    public static void setSwipeRefreshLayoutRefreshing(SwipeRefreshLayout view,
            boolean refreshing) {
        if (refreshing != view.isRefreshing()) {
            view.setRefreshing(refreshing);
        }
    }

    @BindingAdapter(value = { "onRefreshListener", "refreshingAttrChanged" }, requireAll = false)
    public static void setOnRefreshListener(final SwipeRefreshLayout view,
            final SwipeRefreshLayout.OnRefreshListener listener,
            final InverseBindingListener refreshingAttrChanged) {

        SwipeRefreshLayout.OnRefreshListener newValue = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (listener != null) {
                    if (refreshingAttrChanged != null) {
                        refreshingAttrChanged.onChange();
                    }
                    listener.onRefresh();
                }
            }
        };

        SwipeRefreshLayout.OnRefreshListener oldValue =
                ListenerUtil.trackListener(view, newValue, 1 + 2 << 24);
        if (oldValue != null) {
            view.setOnRefreshListener(null);
        }
        view.setOnRefreshListener(newValue);
    }

    @BindingAdapter("isShowRefreshing")
    public static void setSwipeRefreshLayoutVisibility(SwipeRefreshLayout view,
            boolean isShowRefreshing) {
        view.setRefreshing(isShowRefreshing);
    }

    @BindingAdapter({ "textSelected" })
    public static void setTextSelected(TextView textView, boolean isSelected) {
        textView.setSelected(isSelected);
    }



    @BindingAdapter({ "url", "client", "data" })
    public static void postWebViewRequest(WebView webView, String url, WebViewClient client,
            String data) {
        if (TextUtils.isEmpty(url) || !Patterns.WEB_URL.matcher(url).matches() || TextUtils.isEmpty(
                data)) {
            return;
        }
        webView.setWebViewClient(client);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.postUrl(url, data.getBytes());
    }

    @BindingAdapter({ "url", "client" })
    public static void loadWebViewUrl(WebView webView, String url, WebViewClient client) {
        if (TextUtils.isEmpty(url) || !Patterns.WEB_URL.matcher(url).matches()) {
            return;
        }
        webView.setWebViewClient(client);
        webView.loadUrl(url);
    }

    @BindingAdapter({ "enable" })
    public static void setEnable(View view, boolean enable) {
        view.setEnabled(enable);
    }

    @BindingAdapter({ "layoutParams" })
    public static void setLayoutParams(View view, ViewGroup.LayoutParams layoutParams) {
        if (layoutParams == null) {
            return;
        }
        view.setLayoutParams(layoutParams);
    }


    @BindingAdapter({ "requestFocus" })
    public static void requestFocus(EditText editText, boolean isRequestFocus) {
        if (isRequestFocus) {
            editText.requestFocus();
        }
    }

    @BindingAdapter({ "itemAnimator" })
    public static void setItemAnimatorRecyclerView(RecyclerView recyclerView,
            boolean isHasAnimator) {
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(
                isHasAnimator);
    }

    @BindingAdapter({ "underline" })
    public static void setUnderline(TextView textView, boolean underline) {
        if (underline) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    @BindingAdapter({ "cacheMode" })
    public static void setCacheMode(WebView webView, int mode) {
        webView.getSettings().setCacheMode(mode);
    }

}
