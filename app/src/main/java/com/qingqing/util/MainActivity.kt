package com.qingqing.util

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.content.pm.PackageManager
import android.graphics.Color
import com.qingqing.util.util.AppUtils
import com.qingqing.util.rv.MainAdapter
import com.qingqing.util.ui.AppListActivity
import com.qingqing.util.util.RecycleViewDivider


class MainActivity : AppCompatActivity() {
    private var adapter: MainAdapter =
        MainAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launcherMainCtl.setExpandedTitleColor(Color.TRANSPARENT)
        setSupportActionBar(launcherMainToolbar)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
        rv.addItemDecoration(
            RecycleViewDivider(
                this,
                RecycleViewDivider.VERTICAL,
                R.drawable.common_item_divider
            )
        )
        adapter.setOnItemClickListener(object : MainAdapter.OnItemClickListener {
            override fun onItemClickListener(index: Int) {
                if (index == 0) {
                    startActivity(Intent(this@MainActivity, AppListActivity::class.java))
                }
            }

        })
    }
}
