package com.exmple.lession_gift_animi;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.nfc.Tag;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exmple.lession_gift_animi.view.CircleImageView;
import com.exmple.lession_gift_animi.view.MyImgAnim;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private CircleImageView mImgAvatar0, mImgAvatar1, mImgAvatar2, mImgAvatar3, mImgAvatar4, mImgAvatar5, mImgAvatar6, mImgAvatar7, mImgAvatar8, mImgAvatar9;


    private ImageView mImghead;

    private LinearLayout mLLAvatar1;

    private Button mBtnSend;

    private FrameLayout mFLContent;

    //所有麦上固定值点
    private List<PointF> mSetAllPoint1 = new ArrayList<>();

    //需要分发礼物的点
    private List<PointF> mListAllPoint = new ArrayList<>();

    //head
    PointF pointHead = new PointF();

    //上麦用户
    private Context mContext;

    //麦序 播放初始动画的位置
    int startIndex = 2;

    private List<List<PointF>> animPlayerQueue = new ArrayList<>();
    private boolean mIsRunning;

    private  SVGAImageView mImgViewSvga;

    private SVGAParser mSvgaParser;
    private TextView mTxtAnim;
    private String mUrlSvga = "https://github.com/yyued/SVGA-Samples/blob/master/posche.svga?raw=true";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewByIds();
        initValues();
        mSetAllPoint1.clear();
        mListAllPoint.clear();
        mContext = this;
        mSvgaParser = new SVGAParser(this);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //setTextAnim();
//                for (PointF pointF1 : mSetAllPoint1) {
//                    mListAllPoint.add(pointF1);
//                }
//                animPlayerQueue.add(mListAllPoint);
//                animPlayerQueue.add(mListAllPoint);
//                animPlayerQueue.add(mListAllPoint);
//                //PointF pointF = new PointF(mListAllPoint.get(startIndex).x, mListAllPoint.get(startIndex).y);
//                //先获取队列第一个元素
//                send(pointHead, animPlayerQueue.get(0));
//
                playerSvga(mUrlSvga);

                //new VoiceGiftUtil(mContext).prepareAsyncAnimation(mUrlSvga);
            }
        });
    }


    private void initValues() {
        mSetAllPoint1.clear();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addPoint(0, mImgAvatar0);
                addPoint(1, mImgAvatar1);
                addPoint(2, mImgAvatar2);
                addPoint(3, mImgAvatar3);
                addPoint(4, mImgAvatar4);
                addPoint(5, mImgAvatar5);
                addPoint(6, mImgAvatar6);
                addPoint(7, mImgAvatar7);
                addPoint(8, mImgAvatar8);
                setPointHead();
            }
        }, 1000);
        for (PointF p : mListAllPoint) {
            Log.d(TAG, "x=" + p.x + ",y=" + p.y);
        }


    }

    private void addPoint(final int index, final CircleImageView circleImageView) {


        float dx = circleImageView.getX();
        float dy = mLLAvatar1.getY();

        //如果是头部
        if (index == 0) {
            dy = 50;
        } else if (index > 4) {
            dx += 50;
            dy += 350;
        } else {
            dx += 50;
            dy += 50;
        }
        PointF pointF = new PointF();
        pointF.set(dx, dy);
        mSetAllPoint1.add(new PointF(dx, dy));

        Log.d(TAG, "size=" + (mSetAllPoint1.size() - 1) + ",x=" + pointF.x);


    }

    private void setPointHead() {
        float dy = mLLAvatar1.getY() + 150;
        float dx = mImgAvatar3.getX() - 100;
        pointHead.set(dx, dy);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    /**
     * 播放头设置
     * <p>
     * 1. 是否在麦上 ，麦上用户弹出，后台再执行剩余动画
     * 2. 没在麦上 中间弹出
     * <p>
     * 3. 先播放头部动画 ，放大缩小
     * 4.
     */
    private void send(PointF start, List<PointF> lists) {

        mIsRunning = true;
        mFLContent.removeAllViews();
        ImageView imageViewHead = new ImageView(mContext);
        imageViewHead.setBackgroundResource(R.mipmap.icon_browse);
        imageViewHead.setVisibility(View.INVISIBLE);
        imageViewHead.setLayoutParams(new ViewGroup.LayoutParams(250, 250));
        for (int i = 0; i < lists.size(); i++) {
            SVGAImageView svgaImageView = new SVGAImageView(mContext);
            svgaImageView.setLayoutParams(new ViewGroup.LayoutParams(180, 180));
            FrameLayout frameWrapper = new FrameLayout(mContext);
            frameWrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT));

            ImageView imageView = new ImageView(mContext);
            imageView.setBackgroundResource(R.mipmap.icon_browse);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(180, 180));
            imageView.setVisibility(View.INVISIBLE);

            TextView textView = new TextView(mContext);
            textView.setText("X 1");
            textView.setTextSize(18);
            // textView.setGravity(Gravity.RIGHT| Gravity.BOTTOM);
            textView.setVisibility(View.INVISIBLE);

            frameWrapper.addView(imageView);
            frameWrapper.addView(textView);
            frameWrapper.addView(svgaImageView);
            mFLContent.addView(frameWrapper);


        }


        mFLContent.addView(imageViewHead);


        setHeadAnima(imageViewHead, start, lists);


    }

    private void playerSvga( String path) {

        try {
            mSvgaParser.decodeFromURL(new URL(mUrlSvga)
                    , new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(SVGAVideoEntity videoItem) {
                    SVGADrawable drawable = new SVGADrawable(videoItem);
                    mImgViewSvga.setImageDrawable(drawable);
                    mImgViewSvga.startAnimation();
                }

                @Override
                public void onError() {
                    Log.i("test","onError");
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void setTextAnim() {

        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f, 1f);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(mTxtAnim, scaleX, scaleY);

        GiftAnimationUtil.scaleGiftNum(mTxtAnim).start();


    }

    /**
     * 设置头部动画
     * <p>
     * 第一帧动画
     * 缩放
     */
    private void setHeadAnima(final ImageView mImageView, PointF start, List<PointF> lists) {

        final List<PointF> listAnima = lists;
        final PointF statrPointF = start;
        Path path = new Path();
        path.moveTo(start.x, start.y);
        //path.lineTo(start.x, start.y);
        mImageView.setVisibility(View.VISIBLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mImageView, mImageView.X, mImageView.Y, path);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(600);
        // objectAnimator.start();

        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(mImageView, "scaleX", 1f, 2f, 1.8f);
        objectAnimatorX.setDuration(1000);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(mImageView, "scaleY", 1f, 2f, 1.8f);
        objectAnimatorY.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator).with(objectAnimatorX).with(objectAnimatorY);
        animatorSet.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator objectAnimatorAlpa = ObjectAnimator.ofFloat(mImageView, "alpha", 1f, 0f);
                objectAnimatorAlpa.setDuration(100);
                objectAnimatorAlpa.start();

                mFLContent.removeView(mImageView);
                objectAnimatorAlpa.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        for (int i = 0; i < listAnima.size(); i++) {
                            final PointF pointF = listAnima.get(i);

                            //排除自己再麦上位置
                            if ((pointF.x == statrPointF.x && statrPointF.y == pointF.y)) {
                                continue;
                            }
                            FrameLayout imageView = (FrameLayout) mFLContent.getChildAt(i);
                            ImageView imageView1 = (ImageView) imageView.getChildAt(0);
                            imageView1.setVisibility(View.VISIBLE);
                            final TextView textClick = (TextView) imageView.getChildAt(1);
                            final SVGAImageView imageViewSvga = (SVGAImageView) imageView.getChildAt(2);

                            Path path = new Path();
                            path.moveTo(statrPointF.x, statrPointF.y);
                            path.lineTo(pointF.x, pointF.y);

                            final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, imageView.X, imageView.Y, path);
                            objectAnimator.setInterpolator(new LinearInterpolator());
                            objectAnimator.setDuration(600);

                            //objectAnimator.start();
                            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0.8f);
                            objectAnimatorX.setDuration(600);
                            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 0.8f);
                            objectAnimatorY.setDuration(600);
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.play(objectAnimator).with(objectAnimatorX).with(objectAnimatorY);

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Path path1 = new Path();
                                    path1.moveTo(90, 80);

                                    final ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textClick, textClick.X, textClick.Y, path1);
                                    objectAnimator1.setInterpolator(new LinearInterpolator());
                                    objectAnimator1.setDuration(600);
                                    objectAnimator1.start();
                                    ObjectAnimator objectAnimatorText = ObjectAnimator.ofFloat(textClick, textClick.X, textClick.Y, path1);
                                    objectAnimatorText.setInterpolator(new LinearInterpolator());
                                    objectAnimatorText.setDuration(10);
                                    textClick.setVisibility(View.VISIBLE);


                                    // objectAnimator1.start();
                                    GiftAnimationUtil.scaleGiftNum(textClick).start();

                                    playerSvga(mUrlSvga);

                                }
                            }, 1000);
                            //监听最后一个结束动画
                            if (i == listAnima.size() - 1) {
                                animPlayerQueue.remove(listAnima);
                                // animatorSet.addListener(animEnd);
                            }
                            animatorSet.start();


                        }
                        mIsRunning = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        }, 1000);

//        ObjectAnimator objectAnimator1= ObjectAnimator.ofObject(mImageView,"c");
//
    }


    /**
     * 结束动画监听，显示连击
     * <p>
     * 判断当前动画有没有结束，没有结束，只执行连击效果
     */
    Animator.AnimatorListener animEnd = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            if (animPlayerQueue.size() > 0) {
                send(pointHead, animPlayerQueue.get(0));
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private void findViewByIds() {
        mImgAvatar0 = findViewById(R.id.img_avatar0);
        mImgAvatar1 = findViewById(R.id.img_avatar1);
        mImgAvatar2 = findViewById(R.id.img_avatar2);
        mImgAvatar3 = findViewById(R.id.img_avatar3);
        mImgAvatar4 = findViewById(R.id.img_avatar4);
        mImgAvatar5 = findViewById(R.id.img_avatar5);
        mImgAvatar6 = findViewById(R.id.img_avatar6);
        mImgAvatar7 = findViewById(R.id.img_avatar7);
        mImgAvatar8 = findViewById(R.id.img_avatar8);

        mTxtAnim = findViewById(R.id.text);

        mBtnSend = findViewById(R.id.btn_send);
        mFLContent = findViewById(R.id.fl_anim_content);
        mLLAvatar1 = findViewById(R.id.ll_avatar1);
        mImgViewSvga=findViewById(R.id.img_view_svga);
    }
}
