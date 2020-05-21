package com.qingqing.util.ui

import android.os.Bundle
import android.widget.Toast
import com.qingqing.util.BaseActivity
import com.qingqing.util.R
import com.qingqing.util.constants.Extra
import com.qingqing.util.dto.AppInfo
import com.qingqing.util.util.AppUtils
import com.qingqing.util.util.FileUtil
import kotlinx.android.synthetic.main.activity_log.*
import java.io.File

class LogActivity : BaseActivity() {

    var info: AppInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        if (intent != null) {
            info = intent.getParcelableExtra<AppInfo>(Extra.APPINFO)
            if (false) {
                sc_log.isChecked = true
            } else {
                sc_log.isChecked = info?.packageName?.let { exist(it) }!!
            }
        }
        sc_log.setOnCheckedChangeListener { buttonView, isChecked ->
            if (debug()) {
                sc_log.isChecked = !isChecked
                Toast.makeText(this, "DEBUG模式下已经开启LOG日志", Toast.LENGTH_LONG).show()
            } else {

            }
        }
    }

    private fun debug(): Boolean {
        return AppUtils.isApkDebugable(this, info?.packageName)
    }

    private fun exist(packageName: String): Boolean {
        val showCfgFile = File(
            FileUtil.getExternalRootCacheFile(this, packageName),
            packageName
        )
        var bool = showCfgFile.exists() && showCfgFile.isFile()
        return bool
    }
}
