package com.insomniac.sunset;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SunSetActivity extends SingleFragmentActivity{

    public Fragment createFragment(){
     return SunSetFragment.newInstance();
    }
}
