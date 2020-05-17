package com.qingqing.util.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.qingqing.util.R
import com.qingqing.util.util.AppUtils
import com.qingqing.util.MainActivity
import com.qingqing.util.util.AppUtils.scanLocalInstallAppList
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeremyliao.liveeventbus.LiveEventBus
import com.qingqing.util.dto.AppInfo
import com.qingqing.util.rv.AppAdapter
import com.qingqing.util.rv.MainAdapter
import com.qingqing.util.util.RecycleViewDivider
import kotlinx.android.synthetic.main.activity_main.*


class AppListActivity : AppCompatActivity() {
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
        rv.addItemDecoration(
            RecycleViewDivider(
                this,
                RecycleViewDivider.VERTICAL,
                R.drawable.common_item_divider
            )
        )
        adapter.setOnItemClickListener(object : AppAdapter.OnItemClickListener {
            override fun onItemClickListener(index: Int, dto: AppInfo) {
                var intent = Intent(this@AppListActivity, LogActivity::class.java)
                intent.putExtra("appInfo", dto)
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
