package com.exmple.lession_gift_animi;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class GiftAnimationUtil {


    /**
     * @param target
     * @param star     动画起始坐标
     * @param end      动画终止坐标
     * @param duration 持续时间
     * @return 创建一个从左到右的飞入动画
     * 礼物飞入动画
     */
    public static ObjectAnimator createFlyFromLtoR(final View target, float star, float end, int duration, TimeInterpolator interpolator) {
        //1.个人信息先飞出来
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(target, "translationX",
                star, end);
        anim1.setInterpolator(interpolator);
        anim1.setDuration(duration);
        return anim1;
    }


    /**
     * @param target
     * @return 播放帧动画
     */
    public static AnimationDrawable startAnimationDrawable(ImageView target) {
        AnimationDrawable animationDrawable = (AnimationDrawable) target.getDrawable();
        if (animationDrawable != null) {
            target.setVisibility(View.VISIBLE);
            animationDrawable.start();
        }
        return animationDrawable;
    }


    /**
     * @param target
     * @param drawable 设置帧动画
     */
    public static void setAnimationDrawable(ImageView target, AnimationDrawable drawable) {

        target.setBackground(drawable);
    }


    /**
     * @param target
     * @return 送礼数字变化
     */
    public static ObjectAnimator scaleGiftNum(final TextView target) {
        PropertyValuesHolder anim4 = PropertyValuesHolder.ofFloat("scaleX",
                1.7f, 0.8f, 1f);
        PropertyValuesHolder anim5 = PropertyValuesHolder.ofFloat("scaleY",
                1.7f, 0.8f, 1f);
        PropertyValuesHolder anim6 = PropertyValuesHolder.ofFloat("alpha",
                1.0f, 0f, 1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, anim4, anim5, anim6).setDuration(300);
        return animator;

    }

    /**
     * @param target
     * @return gif图标动画
     */
    public static ObjectAnimator scaleGif(final View target) {
        PropertyValuesHolder anim4 = PropertyValuesHolder.ofFloat("scaleX",
                0f, 1f);
        PropertyValuesHolder anim5 = PropertyValuesHolder.ofFloat("scaleY",
                0f, 1f);
        PropertyValuesHolder anim6 = PropertyValuesHolder.ofFloat("alpha",
                1.0f, 0f, 1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, anim4, anim5, anim6).setDuration(200);
        return animator;

    }

    /**
     * @return 送礼数字变化
     */
    public static AnimatorSet scaleGiftNums(final TextView tvx, final TextView tvnum) {
        tvx.setVisibility(View.VISIBLE);
        tvnum.setVisibility(View.VISIBLE);
        PropertyValuesHolder anim4 = PropertyValuesHolder.ofFloat("scaleX",
                1.5f, 1f);
        PropertyValuesHolder anim5 = PropertyValuesHolder.ofFloat("scaleY",
                1.5f, 1f);

        PropertyValuesHolder anim6 = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder anim7 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(tvx, anim4, anim5).setDuration(300);
        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(tvnum, anim6, anim7).setDuration(300);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animator).with(animator2);
        animSet.start();
        return animSet;
    }

    /**
     * @return 连击控件进来时播放动画
     */
    public static void setBallInAnim(final View tvx) {

        PropertyValuesHolder anim4 = PropertyValuesHolder.ofFloat("scaleX",
                0.25f, 1.2f);
        PropertyValuesHolder anim5 = PropertyValuesHolder.ofFloat("scaleY",
                0.25f, 1.2f);
        PropertyValuesHolder anim55 = PropertyValuesHolder.ofFloat("rotation",
                -72f, -27f);

        PropertyValuesHolder anim6 = PropertyValuesHolder.ofFloat("scaleX",
                1.2f, 1f);
        PropertyValuesHolder anim7 = PropertyValuesHolder.ofFloat("scaleY",
                1.2f, 1f);
        PropertyValuesHolder anim77 = PropertyValuesHolder.ofFloat("rotation",
                -27f, 0f);


        PropertyValuesHolder anim8 = PropertyValuesHolder.ofFloat("scaleX",
                1f, 1.1f);
        PropertyValuesHolder anim9 = PropertyValuesHolder.ofFloat("scaleY",
                1f, 1.1f);


        PropertyValuesHolder anim10 = PropertyValuesHolder.ofFloat("scaleX",
                1.1f, 1f);
        PropertyValuesHolder anim11 = PropertyValuesHolder.ofFloat("scaleY",
                1.1f, 1f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(tvx, anim4, anim5, anim55).setDuration(160);
        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(tvx, anim6, anim7, anim77).setDuration(80);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(tvx, anim8, anim9).setDuration(80);
        ObjectAnimator animator4 = ObjectAnimator.ofPropertyValuesHolder(tvx, anim10, anim11).setDuration(120);


        AnimatorSet animSet = new AnimatorSet();
        animSet.playSequentially(animator, animator2, animator3, animator4);
        animSet.start();

    }

    //连击动画收起时动画
    public static AnimatorSet setBallOutAnim(final View tvx) {
        PropertyValuesHolder anim4 = PropertyValuesHolder.ofFloat("scaleX",
                1f, 1.3f);
        PropertyValuesHolder anim5 = PropertyValuesHolder.ofFloat("scaleY",
                1f, 1.3f);

        PropertyValuesHolder anim6 = PropertyValuesHolder.ofFloat("scaleX",
                1.3f, 0.78f);
        PropertyValuesHolder anim7 = PropertyValuesHolder.ofFloat("scaleY",
                1.3f, 0.78f);


        PropertyValuesHolder anim8 = PropertyValuesHolder.ofFloat("scaleX",
                0.78f, 0.3f);
        PropertyValuesHolder anim9 = PropertyValuesHolder.ofFloat("scaleY",
                0.78f, 0.3f);


        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(tvx, anim4, anim5).setDuration(80);
        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(tvx, anim6, anim7).setDuration(200);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(tvx, anim8, anim9).setDuration(40);


        AnimatorSet animSet = new AnimatorSet();
        animSet.playSequentially(animator, animator2, animator3);
        return animSet;
    }



}