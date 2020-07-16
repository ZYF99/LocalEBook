package com.zhangyf.localebook.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tencent.smtt.sdk.TbsConfig
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