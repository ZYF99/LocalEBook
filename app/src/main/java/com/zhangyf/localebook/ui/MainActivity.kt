package com.zhangyf.localebook.ui

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tencent.smtt.sdk.QbSdk
import com.zhangyf.localebook.R
import com.zhangyf.localebook.adapter.MainPagerAdapter
import com.zhangyf.localebook.databinding.ActivityMainBinding
import com.zhangyf.localebook.util.FileUtils
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        val fragmentList = assets.list("book")?.map { ListFragment.newInstance(it) } ?: emptyList()
        binding.vpMain.adapter = MainPagerAdapter(supportFragmentManager, fragmentList)
        binding.tlMain.setupWithViewPager(binding.vpMain)

    }

    private fun initData() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val builder =  StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy( builder.build() )
        }


        QbSdk.initX5Environment(applicationContext, object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {
                Log.d("QbSdk~~", "onCoreInitFinished")
            }

            override fun onViewInitFinished(initResult: Boolean) {
                Log.e("QbSdk~~", "onViewInitFinished$initResult")
            }
        })

        try {
            val DILE_DIR = File("${externalCacheDir?.path}","book").absolutePath+File.separator
            FileUtils.copyAssetsDir(
                this,
                "book",
                DILE_DIR
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}