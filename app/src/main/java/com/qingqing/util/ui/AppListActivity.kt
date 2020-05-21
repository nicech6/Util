package com.qingqing.util.ui

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.qingqing.util.BaseActivity
import com.qingqing.util.R
import com.qingqing.util.constants.Extra.APPINFO
import com.qingqing.util.dto.AppInfo
import com.qingqing.util.rv.AppAdapter
import com.qingqing.util.util.AppUtils.scanLocalInstallAppList
import kotlinx.android.synthetic.main.activity_app_list.*


class AppListActivity : BaseActivity() {
    private var adapter: AppAdapter =
        AppAdapter(this, arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_list)
        initRv()
        initAppList()

    }

    private fun initRv() {

        rv.adapter = adapter
        rv.layoutManager = GridLayoutManager(this, 3)
        adapter.setOnItemClickListener(object : AppAdapter.OnItemClickListener {
            override fun onItemClickListener(index: Int, dto: AppInfo) {
                var intent = Intent(this@AppListActivity, LogActivity::class.java)
                intent.putExtra(APPINFO, dto)
                startActivity(intent)
            }
        })

    }

    private fun initAppList() {
        object : Thread() {
            override fun run() {
                super.run()
                val appInfos = scanLocalInstallAppList(
                    this@AppListActivity,
                    this@AppListActivity.packageManager
                )
                runOnUiThread {
                    adapter.setData(appInfos)
                }
            }
        }.start()
    }

}
