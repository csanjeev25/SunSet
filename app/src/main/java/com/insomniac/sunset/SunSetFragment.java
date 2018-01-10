package com.insomniac.sunset;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Sanjeev on 1/10/2018.
 */

public class SunSetFragment extends Fragment{

    private View mSceneView;
    private View mSunView;
    private View mSkyView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    private boolean clicked;

    public static Fragment newInstance(){
        return new SunSetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sun_set,container,false);

        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mSkyView = view.findViewById(R.id.sky);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBlueSkyColor = getResources().getColor(R.color.blue_sky, getActivity().getTheme());
            mSunsetSkyColor = getResources().getColor(R.color.sunset_sky, getActivity().getTheme());
            mNightSkyColor = getResources().getColor(R.color.night_sky, getActivity().getTheme());
        } else {
            mBlueSkyColor = getResources().getColor(R.color.blue_sky);
            mSunsetSkyColor = getResources().getColor(R.color.sunset_sky);
            mNightSkyColor = getResources().getColor(R.color.night_sky);
        }

        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked == true) {
                    startAnimation();
                    clicked = false;
                } else {
                    sunRise();
                    clicked = true;
                }
            }
        });

        return view;
    }

    private void startAnimation(){

        float sunYStart = mSunView.getTop();
        float sunYEnd = mSunView.getHeight();

        Animation heatPulse = AnimationUtils.loadAnimation(getActivity(),R.anim.heat_rays);
        mSunView.startAnimation(heatPulse);

        ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(mSunView,"y",sunYStart,sunYEnd);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator.ofInt(mSkyView,"backgroundColor",mBlueSkyColor,mSunsetSkyColor).setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightSkyAnimator = ObjectAnimator.ofInt(mSunView,"backgroundColor",mSunsetSkyColor,mNightSkyColor);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(heightAnimator).with(sunsetSkyAnimator).after(nightSkyAnimator);
        animatorSet.start();

        while (!animatorSet.isRunning())
            mSunView.clearAnimation();
    }

    private void sunRise(){
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSunView.getHeight();

        Animation heatPulse = AnimationUtils.loadAnimation(getActivity(),R.anim.heat_rays);

        ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(mSunView, "y",sunYStart, sunYEnd).setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunRiseAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor).
                setDuration(3000);
        sunRiseAnimator.setEvaluator(new ArgbEvaluator());
        ObjectAnimator blueSkyAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mBlueSkyColor).
                setDuration(1500);
        blueSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(heightAnimator).with(sunRiseAnimator).before(blueSkyAnimator);
        animatorSet.start();

        while (animatorSet.isRunning())
            mSunView.startAnimation(heatPulse);

    }


}
