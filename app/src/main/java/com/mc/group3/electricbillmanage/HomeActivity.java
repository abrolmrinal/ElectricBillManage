package com.mc.group3.electricbillmanage;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private SliderAdapter sliderAdapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private LinearLayout mDotLayout;
    private TextView[] mDots;

    private Button nxbt;
    private Button prvbt;
    private FrameLayout nxtFL;
    private FrameLayout prevFL;
    private int currentPage;


    @Override
    protected void onStart(){
        super.onStart();
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            Intent intentToLauncher = new Intent(getApplicationContext(), LauncherActivity.class);
            startActivity(intentToLauncher);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        mDotLayout = (LinearLayout)findViewById(R.id.dotsLayout);
        mSlideViewPager = findViewById(R.id.slideviewpager);

        nxbt = (Button) findViewById(R.id.nxtbt);
        prvbt = (Button) findViewById((R.id.prevbt));
        nxtFL = findViewById(R.id.nextButtonFrameLayout);
        prevFL = findViewById(R.id.prevButtonFrameLayout);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        nxbt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(currentPage+1);

            }
        });

        prvbt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(currentPage - 1);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        mSlideViewPager = findViewById(R.id.slideviewpager);
        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
    }

    public void LoginFunction(View view)
    {
        Intent intentToLogin = new Intent(this, LoginActivity.class);
        startActivity(intentToLogin);
    }

    public void addDotsIndicator(int position)
    {
        mDots = new TextView[3];
        mDotLayout.removeAllViews();
        for(int i=0 ; i<mDots.length ;i++)
        {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotLayout.addView(mDots[i]);


        }

        if (mDots.length > 0)
        {
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);
            currentPage = i ;
            if (i==0)
            {
                nxbt.setEnabled(true);
                prvbt.setEnabled(false);
                prvbt.setVisibility(View.INVISIBLE);
                prevFL.setVisibility(View.INVISIBLE);
                nxbt.setBackground(getResources().getDrawable(R.drawable.ic_arrow_forward_white_24dp));
//                nxbt.setText("Next");
//                prvbt.setText("");
            }else if(i==mDots.length-1)
            {
                nxbt.setEnabled(true);
                prvbt.setEnabled(true);
                prvbt.setVisibility(View.VISIBLE);
                prevFL.setVisibility(View.VISIBLE);
//                nxbt.setText("");
//                prvbt.setText("Back");
                nxbt.setBackground(getResources().getDrawable(R.drawable.ic_check_white_24dp));
            }
            else
            {
                nxbt.setEnabled(true);
                prvbt.setEnabled(true);
                prvbt.setVisibility(View.VISIBLE);
                prevFL.setVisibility(View.VISIBLE);
                nxbt.setBackground(getResources().getDrawable(R.drawable.ic_arrow_forward_white_24dp));
//                nxbt.setText("Next");
//                prvbt.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
