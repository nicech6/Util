package com.qingqing.util.ui

import android.os.Bundle
import android.os.Environment
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

    lateinit var info: AppInfo
    lateinit var dir: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        if (intent != null) {
            info = intent.getParcelableExtra<AppInfo>(Extra.APPINFO)
            sc_log.isChecked = isExistExternalRootCacheFile(info.packageName)
        }
        sc_log.setOnCheckedChangeListener { buttonView, isChecked ->
            //            if (debug()) {
//                sc_log.isChecked = !isChecked
//                Toast.makeText(this, "DEBUG模式下已经开启LOG日志", Toast.LENGTH_LONG).show()
//            } else {
//
//            }
            if (isChecked) {
                if (!isExistExternalRootCacheFile(info.packageName)) {
                    if (isSDCardExists()) {
                        var mkdir = dir.mkdirs()
                        if (mkdir) {
                            Toast.makeText(this, "已开启Log", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                var delete = AppUtils.delete(dir)
                if (delete) {
                    Toast.makeText(this, "已关闭Log", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun isSDCardExists(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun isExistExternalRootCacheFile(packageName: String): Boolean {
        return if (isSDCardExists()) {
            dir = File(
                Environment.getExternalStorageDirectory(),
                "Android/data/" + packageName + "/cache"
            )
            dir.exists() && dir.isDirectory()
        } else {
            dir = File(
                Environment.getDataDirectory(),
                "Android/data/" + packageName + "/cache"
            )
            dir.exists() && dir.isDirectory()
        }
        return false

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
