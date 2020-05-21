package com.qingqing.util;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;

/**
 * @author cuihai on 2020/5/20
 * @description
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).barColor(R.color.config_color_white).autoDarkModeEnable(true).fitsSystemWindows(true).init();
    }
}
