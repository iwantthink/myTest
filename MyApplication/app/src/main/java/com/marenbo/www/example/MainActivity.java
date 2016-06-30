package com.marenbo.www.example;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            Log.d("MainActivity", "msg receive");

            switch (msg.what) {

                case START:

                    Log.d("MainActivity", "START ");

                    mCurrentItem++;

                    mVp.setCurrentItem(mCurrentItem);

                    mHandler.sendEmptyMessageDelayed(START, 2000);

                    break;
                case STOP:

                    Log.d("MainActivity", "STOP ");

                    mHandler.removeMessages(START);


                    break;
                default:
                    break;

            }

            return false;
        }
    });

    private static final int START = 1;

    private static final int STOP = 2;

    private int mCurrentItem = 0;

    private boolean mIsTouching = false;

    private ViewPager mVp;

    private ArrayList<ImageView> mImageViews = new ArrayList<>();

    private int[] mIds = new int[]{R.mipmap.img01, R.mipmap.img02, R.mipmap.img03, R.mipmap.img04, R.mipmap.img05};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        mVp = (ViewPager) findViewById(R.id.viewpager);

        mVp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {

                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {

                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                int pos = position % mImageViews.size();

                if (pos < 0) {

                    pos = mImageViews.size() + pos;
                }
                ImageView view = mImageViews.get(pos);

                ViewParent parent = view.getParent();

                if (null != parent) {

                    ViewGroup vp = (ViewGroup) parent;

                    vp.removeView(view);

                }

                container.addView(view);

                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }
        });

        mCurrentItem = mImageViews.size() * 1000;

        mVp.setCurrentItem(mImageViews.size() * 1000);

        mHandler.sendEmptyMessage(START);

        mVp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        mHandler.sendEmptyMessage(STOP);

                        break;

                    case MotionEvent.ACTION_UP:

                        mHandler.sendEmptyMessageDelayed(START, 2000);

                        break;

                }


                return false;
            }
        });

        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                mCurrentItem = i;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initData() {

        for (int mId : mIds) {

            ImageView imageView = new ImageView(MainActivity.this);

            imageView.setImageResource(mId);

            mImageViews.add(imageView);
        }

    }
}
