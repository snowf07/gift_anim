package com.exmple.lession_gift_animi;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class VoiceGiftUtil {
    private Context mContext;
    private String mResources;
    private int mCount = 1;
    private SVGAImageView mSVGAImageView;
    private SVGAParser mParser;
    public BigGiftListener mBigGiftListener;

    public interface BigGiftListener {
        void loadFinished();
    }

    /**
     * 构造方法
     *
     * @param context
     */
    public VoiceGiftUtil(Context context) {
        super();
        init(context, null, null);
    }

    public VoiceGiftUtil(Context context, SVGAImageView giftView) {
        super();
        init(context, giftView, null);
    }


    private void init(Context context, SVGAImageView giftView, String res) {
        mContext = context;
        mSVGAImageView = giftView;
        mResources = res;
//        mSVGAImageView.setCallback(mGiftLoadListener);
        File cacheDir = new File(mContext.getCacheDir(), "GIFT");
        try {
            HttpResponseCache.install(cacheDir, 1024 * 1024 * 128);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    SVGACallback svgaCallback = new SVGACallback() {
        @Override
        public void onPause() {

        }

        @Override
        public void onFinished() {
            if (mBigGiftListener != null) {
                mBigGiftListener.loadFinished();
            }
        }

        @Override
        public void onRepeat() {

        }

        @Override
        public void onStep(int i, double v) {

        }
    };

    public void setBigGiftListener(BigGiftListener bigGiftListener) {
        mBigGiftListener = bigGiftListener;
    }


    public VoiceGiftUtil prepareStingAsync() {


        if (TextUtils.isEmpty(mResources)) return this;
        if (mResources.startsWith("http://")) {
            playHttpAnimation(mResources);
        } else {
            playLocalAnimation(mResources);
        }
        return this;
    }

    LinearLayout line_bottomgift;
    RelativeLayout rel_svg;

    public VoiceGiftUtil prepareStingAsync(RelativeLayout rel_svg, LinearLayout line_bottomgift, String res) {
        this.line_bottomgift = line_bottomgift;
        this.rel_svg = rel_svg;
        if (mSVGAImageView != null && mSVGAImageView.getCallback() == null) {
            mSVGAImageView.setCallback(svgaCallback);
        }
        mResources = res;
        prepareStingAsync();
        return this;
    }

    public VoiceGiftUtil setPlayCount(int count) {
        mCount = count;
        return this;
    }

    public VoiceGiftUtil setSVGAImageView(SVGAImageView sVGAImageView) {
        mSVGAImageView = sVGAImageView;
        return this;
    }

    public VoiceGiftUtil downLoadRes(String res) {
        mResources = res;
        prepareStingAsync();
        return this;
    }

    private void playHttpAnimation(String path) {
        try {
            URL url = new URL(path);
            prepareAsync(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void playLocalAnimation(String path) {
        prepareAsyncAnimation(path);
    }

    public VoiceGiftUtil prepareAsync(URL url) {
        if (mParser == null) {
            mParser = new SVGAParser(mContext);
        }
        SVGAParser mParser = new SVGAParser(mContext);
        mParser.decodeFromURL(url, new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(SVGAVideoEntity svgaVideoEntity) {
                SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
                setImageDrawable(drawable);
                play();
            }

            @Override
            public void onError() {

            }
        });
        return this;
    }

    public VoiceGiftUtil prepareAsyncAnimation(String path) {
        if (TextUtils.isEmpty(path)) return this;

        if (mParser == null) {
            mParser = new SVGAParser(mContext);
        }
        try {
            mParser.decodeFromURL(new URL(path), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(SVGAVideoEntity svgaVideoEntity) {
                    SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
                    setImageDrawable(drawable);
                    play();
                }

                @Override
                public void onError() {
                    Log.i("test","onError");
                }
            });


        } catch (Exception e) {

        }
        return this;
    }


    public VoiceGiftUtil play() {
        if (mSVGAImageView != null) {
            mSVGAImageView.setLoops(mCount);
            mSVGAImageView.startAnimation();
            if (line_bottomgift != null)
                line_bottomgift.setVisibility(View.VISIBLE);
            if (rel_svg != null)
                rel_svg.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public VoiceGiftUtil setImageDrawable(SVGADrawable drawable) {
        if (mSVGAImageView != null) {
            mSVGAImageView.setImageDrawable(drawable);
        }
        return this;
    }

    public VoiceGiftUtil pauseAnimation() {
        if (mSVGAImageView != null) {
            mSVGAImageView.pauseAnimation();
        }
        return this;
    }


    interface GiftParserListener {
        void onComplete(@NotNull SVGAVideoEntity videoItem);

        void onError();
    }

    interface GiftLoadListener {
        void onComplete(@NotNull SVGAVideoEntity videoItem);

        void onError();
    }
}
