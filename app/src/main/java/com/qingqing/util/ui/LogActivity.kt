package com.qingqing.util.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jeremyliao.liveeventbus.LiveEventBus

import com.qingqing.util.R
import com.qingqing.util.dto.AppInfo
import com.qingqing.util.util.AppUtils
import kotlinx.android.synthetic.main.activity_log.*

class LogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        if (intent != null) {
            var info = intent.getParcelableExtra<AppInfo>("appInfo")
            if (AppUtils.isApkDebugable(this, info.packageName)) {
                sc_log.isChecked = true
            } else {

            }
        }
        sc_log.setOnCheckedChangeListener { buttonView, isChecked ->
            LiveEventBus
                .get("log_switch")
                .postAcrossApp(isChecked)
        }

    }
}
