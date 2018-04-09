package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.AnimRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by longcb on 12/1/16.
 * Email: topcbl@gmail.com
 */
public final class AnimationUtils {
    private static final int DRAWABLE_DURATION = 250;
    private static final int RESET_ANIMATION_DELAY = 1500;
    private static final int RESET_RIPPLE_ANIMATION_DELAY = 300;
    private static final long DELAY_VISIBLE_VIEW_TIME = 2000;
    private static final int NEXT_MISSION_ANIMATE_DURATION = 500;
    private static final int STAMP_ANIMATION_TIME = 350;

    public static Object createAnimation(View view, @AnimType int type,
            @Nullable AnimatorListenerAdapter listener, @Nullable Object params) {
        switch (type) {
            case AnimType.RIPPLE:
                return createRippleAnimation(view, listener);
            case AnimType.SHRINK:
                return createShrinkAnimation(view, listener);
            case AnimType.SHRINK_N_FADED:
                return createShrinkAndFadedAnimation(view, listener);
            case AnimType.FLIP_FLOP:
                break;
            case AnimType.ROTATE:
                return createRotateAnimation(view, listener);
            case AnimType.TOP_DOWN:
                return createTopDownAnimation(view, listener, params);
            case AnimType.BOTTOM_UP:
                return createBottomUpAnimation(view, listener, params);
            case AnimType.ANTICIPATE_SCALE:
                return createAnticipateScale(view, listener);
            case AnimType.SCALE_DOWN_UP:
                return createScaleDownThenUpAnimation(view, listener, params);
            case AnimType.SLIDE_IN_LEFT:
                return createSlideInLeftAnimation(view, listener, params);
            default:
                break;
        }
        return null;
    }

    private static ObjectAnimator createBottomUpAnimation(View view,
            AnimatorListenerAdapter listener, Object params) {
        float distance = (Float) params;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", -distance);
        animator.removeAllListeners();
        animator.addListener(listener);
        return animator;
    }

    public static ObjectAnimator createTopDownAnimation(View view, AnimatorListenerAdapter listener,
            Object params) {
        float distance = (Float) params;
        view.setTranslationY(-distance);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0);
        animator.removeAllListeners();
        animator.addListener(listener);
        return animator;
    }

    public static ObjectAnimator createSlideInLeftAnimation(View view,
            AnimatorListenerAdapter listener, Object params) {
        view.setTranslationX(view.getWidth());
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 0);
        if (params instanceof Number) {
            long duration = ((Number) params).longValue();
            animator.setDuration(duration);
        }
        animator.removeAllListeners();
        if (listener != null) {
            animator.addListener(listener);
        }
        return animator;
    }

    public static AnimationDrawable createAnimationDrawable(Context context,
            @DrawableRes int... resources) {
        AnimationDrawable drawable = new AnimationDrawable();
        for (int resource : resources) {
            drawable.addFrame(ContextCompat.getDrawable(context, resource), DRAWABLE_DURATION);
        }
        drawable.setOneShot(false);
        return drawable;
    }

    public static AnimatorSet createRadarAnimation(View view) {
        return buildScaleAndAlphaAnimator(view, 0, 1f, 0.5f, 0, 1600, ValueAnimator.INFINITE);
    }

    public static void startAnimation(View view, @AnimRes int id) {
        Animation animation = loadAnimation(view.getContext(), id);
        view.startAnimation(animation);
    }

    private static AnimatorSet createRippleAnimation(final View view,
            @Nullable AnimatorListenerAdapter listener) {
        AnimatorSet animatorSet = buildScaleAndAlphaAnimator(view, 0, 10f, 0.3f, 1f, 450, 0);
        if (listener != null) animatorSet.addListener(listener);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // return to original state
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(View.GONE);
                    }
                }, RESET_RIPPLE_ANIMATION_DELAY);
            }
        });
        return animatorSet;
    }

    private static AnimatorSet createShrinkAnimation(final View view,
            @Nullable final AnimatorListenerAdapter listener) {
        AnimatorSet animatorSet =
                buildScaleAnimator(view, 1f, 0, 200, new AccelerateInterpolator());

        if (listener != null) animatorSet.addListener(listener);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // return to original state
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setScaleX(1f);
                        view.setScaleY(1f);
                    }
                }, RESET_ANIMATION_DELAY);
            }
        });
        return animatorSet;
    }

    private static AnimatorSet createShrinkAndFadedAnimation(final View view,
            @Nullable final AnimatorListenerAdapter listener) {
        AnimatorSet animatorSet = buildScaleAndAlphaAnimator(view, 1f, 0, 1f, 0f, 200, 0);

        if (listener != null) animatorSet.addListener(listener);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // return to original state
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setScaleX(1f);
                        view.setScaleY(1f);
                        view.setAlpha(1f);
                    }
                }, RESET_ANIMATION_DELAY);
            }
        });
        return animatorSet;
    }

    public static ObjectAnimator createRotateAnimation(final View view,
            @Nullable final AnimatorListenerAdapter listener) {
        ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "rotation", 0, 360f);
        rotate.setDuration(1500);
        rotate.setRepeatCount(ValueAnimator.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
        if (listener != null) rotate.addListener(listener);
        return rotate;
    }

    private static AnimatorSet createScaleDownThenUpAnimation(View view,
            @Nullable AnimatorListenerAdapter listener, Object params) {
        int duration = (Integer) params;
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 0.2f, 1f);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(duration).setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(listener);
        return animatorSet;
    }

    public static AnimatorSet createTranslationAndScaleAndAlphaAnimation(final View view,
            float translationX, float translationY, float scaleFrom, float scaleTo, float alphaFrom,
            float alphaTo, int duration, @Nullable final AnimatorListenerAdapter listener) {
        AnimatorSet animatorSet = new AnimatorSet();

        ValueAnimator translationXAnimator =
                buildTranslationXAnimator(view, translationX, duration);
        ValueAnimator translationYAnimator =
                buildTranslationYAnimator(view, translationY, duration);

        ValueAnimator scaleXAnimator = buildScaleXAnimator(view, scaleFrom, scaleTo, duration);
        ValueAnimator scaleYAnimator = buildScaleYAnimator(view, scaleFrom, scaleTo, duration);

        ValueAnimator alphaAnimator = buildAlphaAnimator(view, alphaFrom, alphaTo, duration);

        animatorSet.play(translationXAnimator)
                .with(translationYAnimator)
                .with(scaleXAnimator)
                .with(scaleYAnimator)
                .with(alphaAnimator);
        if (listener != null) animatorSet.addListener(listener);
        animatorSet.start();
        return animatorSet;
    }

    public static AnimatorSet createMoveAndScaleThenFadeAnimation(View view, float deltaX,
            float deltaY, float scaleFrom, float scaleTo, int duration, int offset,
            @Nullable final AnimatorListenerAdapter listener) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "translationX", deltaX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "translationY", deltaY);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", scaleFrom, scaleTo);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", scaleFrom, scaleTo);
        ObjectAnimator fade = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(duration);
        animSet.setStartDelay(offset);
        animSet.play(animX).with(animY).with(scaleX).with(scaleY).before(fade);
        if (listener != null) {
            animSet.addListener(listener);
        }
        return animSet;
    }

    @BindingAdapter({ "blindAnimation" })
    public static void setBlindAnimation(ImageView imageView, boolean isStart) {
        if (!isStart) return;
        AnimationUtils.createStarAnimation(imageView, 1.0f, 0.4f, 1.0f, 0.4f, 600, 250).start();
    }

    @BindingAdapter({ "startVisibleAnimation", "onVisibleAnimationFinish" })
    public static void setVisibleView(View view, boolean isStart,
            final OnAnimationListener listener) {
        if (!isStart || listener == null) return;
        view.animate()
                .alpha(1.0f)
                .setDuration(DELAY_VISIBLE_VIEW_TIME)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        listener.onDone();
                    }
                });
    }

    @BindingAdapter({ "animationChatBox" })
    public static void setAnimationChatBox(View view, boolean isStart) {
        if (!isStart) return;
        view.animate()
                .scaleXBy(1f)
                .scaleYBy(1f)
                .alpha(1f)
                .setDuration(NEXT_MISSION_ANIMATE_DURATION)
                .start();
    }


    @BindingAdapter({ "animationType", "animationListener", "animationParams" })
    public static void viewAnimation(View view, @AnimationUtils.AnimType int type,
            @Nullable AnimatorListenerAdapter listener, Object params) {
        view.setTag(AnimationUtils.createAnimation(view, type, listener, params));
    }

    @BindingAdapter({ "animationType", "animationListener" })
    public static void viewAnimation(View view, @AnimationUtils.AnimType int type,
            @Nullable AnimatorListenerAdapter listener) {
        view.setTag(AnimationUtils.createAnimation(view, type, listener, null));
    }

    @BindingAdapter({ "animationType" })
    public static void viewAnimation(View view, @AnimationUtils.AnimType int type) {
        view.setTag(AnimationUtils.createAnimation(view, type, null, null));
    }

    @BindingAdapter({ "animationTrigger" })
    public static void animationTrigger(View view, boolean isTrigger) {
        if (!isTrigger) return;
        Object animator = view.getTag();
        if (animator == null) return;
        if (animator instanceof Animator) {
            ((Animator) animator).start();
        } else if (animator instanceof ViewPropertyAnimator) {
            ((ViewPropertyAnimator) animator).start();
        } else if (animator instanceof AnimationDrawable) {
            ((AnimationDrawable) animator).start();
        }
    }

    @BindingAdapter({ "stampAnimationListener", "stampAnimationTrigger" })
    public static void animateStampAnimation(View view, @Nullable AnimatorListenerAdapter listener,
            boolean isTrigger) {
        if (!isTrigger) return;
        Animator animator = (Animator) createAnimation(view, AnimType.SCALE_DOWN_UP, listener,
                STAMP_ANIMATION_TIME);
        if (animator == null) return;
        animator.start();
    }

    @BindingAdapter({ "animatedProgress" })
    public static void setAnimatedProgress(ProgressBar pb, int progress) {
        AnimationUtils.startAnimatedProgress(pb, progress);
    }

    @BindingAdapter("startFrameAnimation")
    public static void setStartFrameAnimation(ImageView imageView, boolean start) {
        if (imageView.getBackground() == null
                || !(imageView.getBackground() instanceof AnimationDrawable)
                || !start) {
            return;
        }
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        if (drawable.isRunning()) {
            drawable.stop();
        }
        drawable.start();
    }

    @BindingAdapter(value = { "visibility", "inAnim", "outAnim", "duration" }, requireAll = false)
    public static void setVisibility(View view, int visibility, @AnimRes int inAnim,
            @AnimRes int outAnim, int duration) {
        try {
            Animation inAnimation = null;
            Animation outAnimation = null;
            if (inAnim != 0) {
                inAnimation = loadAnimation(view.getContext(), inAnim);
            }
            if (outAnim != 0) {
                outAnimation = loadAnimation(view.getContext(), outAnim);
            }
            switch (visibility) {
                case View.VISIBLE:
                    if (inAnimation != null) {
                        inAnimation.setDuration(
                                duration <= 0 ? inAnimation.getDuration() : duration);
                        view.startAnimation(inAnimation);
                    }
                    break;
                case View.GONE:
                case View.INVISIBLE:
                    if (outAnimation != null) {
                        outAnimation.setDuration(
                                duration <= 0 ? outAnimation.getDuration() : duration);
                        view.startAnimation(outAnimation);
                    }
                    break;
                default:
                    break;
            }
        } catch (Resources.NotFoundException ignore) {

        } finally {
            view.setVisibility(visibility);
        }
    }

    private static Animation loadAnimation(Context context, @AnimRes int id) {
        return android.view.animation.AnimationUtils.loadAnimation(context, id);
    }

    public static AnimatorSet createStarAnimation(View view, float scaleFrom, float scaleTo,
            float alphaFrom, float alphaTo, int duration, int offset) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", scaleFrom, scaleTo);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        scaleX.setRepeatMode(ValueAnimator.REVERSE);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", scaleFrom, scaleTo);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        scaleY.setRepeatMode(ValueAnimator.REVERSE);
        ObjectAnimator fade = ObjectAnimator.ofFloat(view, "alpha", alphaFrom, alphaTo);
        fade.setRepeatCount(ValueAnimator.INFINITE);
        fade.setRepeatMode(ValueAnimator.REVERSE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, fade);
        animatorSet.setDuration(duration);
        animatorSet.setStartDelay(offset);
        return animatorSet;
    }

    public static void startAnimatedProgress(ProgressBar bar, int progress) {
        ObjectAnimator animation = ObjectAnimator.ofInt(bar, "progress", progress);
        animation.setDuration(250);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    public static AnimatorSet createAnticipateScale(View view, float originScale,
            float transformScale, int duration) {
        final AnimatorSet increaseAnimatorSet =
                buildScaleAnimator(view, originScale, transformScale, duration,
                        new AccelerateInterpolator());
        final AnimatorSet decreaseAnimatorSet =
                buildScaleAnimator(view, transformScale, originScale, duration,
                        new AccelerateInterpolator());
        increaseAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                decreaseAnimatorSet.start();
            }
        });
        return increaseAnimatorSet;
    }

    private static AnimatorSet createAnticipateScale(View view, AnimatorListenerAdapter listener) {
        AnimatorSet animationSet = createAnticipateScale(view, 1f, 1.2f, 150);
        if (listener != null) animationSet.addListener(listener);
        return animationSet;
    }

    private static void setRepeatCount(int value, ValueAnimator... animators) {
        for (ValueAnimator animator : animators) {
            animator.setRepeatCount(value);
        }
    }

    private static AnimatorSet buildScaleAnimator(View view, float scaleFrom, float scaleTo,
            int duration, TimeInterpolator interpolator) {
        ObjectAnimator scaleCircleX =
                ObjectAnimator.ofFloat(view, "scaleX", scaleFrom, scaleTo).setDuration(duration);
        ObjectAnimator scaleCircleY =
                ObjectAnimator.ofFloat(view, "scaleY", scaleFrom, scaleTo).setDuration(duration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleCircleX).with(scaleCircleY);
        animatorSet.setInterpolator(interpolator);
        return animatorSet;
    }

    private static AnimatorSet buildScaleAndAlphaAnimator(View view, float scaleFrom, float scaleTo,
            float alphaFrom, float alphaTo, int duration, int repeatCount) {
        ObjectAnimator scaleCircleX =
                ObjectAnimator.ofFloat(view, "scaleX", scaleFrom, scaleTo).setDuration(duration);
        ObjectAnimator scaleCircleY =
                ObjectAnimator.ofFloat(view, "scaleY", scaleFrom, scaleTo).setDuration(duration);
        ObjectAnimator animOut =
                ObjectAnimator.ofFloat(view, "alpha", alphaFrom, alphaTo).setDuration(duration);
        setRepeatCount(repeatCount, animOut, scaleCircleX, scaleCircleY);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleCircleX).with(scaleCircleY).with(animOut);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        return animatorSet;
    }

    private static ObjectAnimator buildScaleXAnimator(View view, float scaleFrom, float scaleTo,
            int duration) {
        return ObjectAnimator.ofFloat(view, "scaleX", scaleFrom, scaleTo).setDuration(duration);
    }

    private static ObjectAnimator buildScaleYAnimator(View view, float scaleFrom, float scaleTo,
            int duration) {
        return ObjectAnimator.ofFloat(view, "scaleY", scaleFrom, scaleTo).setDuration(duration);
    }

    private static ObjectAnimator buildAlphaAnimator(View view, float alphaFrom, float alphaTo,
            int duration) {
        return ObjectAnimator.ofFloat(view, "alpha", alphaFrom, alphaTo).setDuration(duration);
    }

    private static ObjectAnimator buildTranslationXAnimator(View view, float translationX,
            int duration) {
        return ObjectAnimator.ofFloat(view, "translationX", translationX).setDuration(duration);
    }

    private static ObjectAnimator buildTranslationYAnimator(View view, float translationY,
            int duration) {
        return ObjectAnimator.ofFloat(view, "translationY", translationY).setDuration(duration);
    }

    @BindingAdapter({ "shrinkAndStretchMessageViewAnimation" })
    public static void shrinkAndStretchMessageViewAnimation(View view, boolean isStart) {
        if (!isStart) return;
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator scaleYAnimator = buildScaleYAnimator(view, 1.0f, 0.97f, 200);
        scaleYAnimator.setRepeatCount(ValueAnimator.INFINITE);
        scaleYAnimator.setRepeatMode(ValueAnimator.REVERSE);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.play(scaleYAnimator);
        animatorSet.start();
    }


    @BindingAdapter("infinitiveScale")
    public static void setInfinitiveScale(View view, boolean isStart) {
        if (!isStart) return;
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator scaleYAnimator = buildScaleYAnimator(view, 1.0f, 0.97f, 200);
        scaleYAnimator.setRepeatCount(ValueAnimator.INFINITE);
        scaleYAnimator.setRepeatMode(ValueAnimator.REVERSE);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.play(scaleYAnimator);
        animatorSet.start();
    }

    @IntDef({
            AnimType.NONE, AnimType.RIPPLE, AnimType.FLIP_FLOP, AnimType.SHRINK,
            AnimType.SHRINK_N_FADED, AnimType.ROTATE, AnimType.TOP_DOWN, AnimType.BOTTOM_UP,
            AnimType.ANTICIPATE_SCALE, AnimType.SCALE_DOWN_UP, AnimType.SLIDE_IN_LEFT
    })
    public @interface AnimType {
        int NONE = -1;
        int RIPPLE = 1;
        int FLIP_FLOP = 2;
        int SHRINK = 3;
        int SHRINK_N_FADED = 4;
        int ROTATE = 5;
        int TOP_DOWN = 6;
        int BOTTOM_UP = 7;
        int ANTICIPATE_SCALE = 8;
        int SCALE_DOWN_UP = 9;
        int SLIDE_IN_LEFT = 10;
    }
}
