package com.zhangyf.localebook.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zhangyf.localebook.R
import com.zhangyf.localebook.databinding.ActivityDetailBinding
import com.zhangyf.localebook.util.CacheUtils
import com.zhangyf.localebook.util.WordToHtml
import java.io.File


const val KEY_PATH = "key_path"

class DetailActivity : AppCompatActivity() {

    var bookPath = ""
    var text: String? = ""
    var binding: ActivityDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        binding =
            DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
        bookPath = intent.getStringExtra(KEY_PATH)
        binding?.fabTag?.setOnClickListener {
            val scrollY: Int = binding?.twvWebview?.scrollY ?: 0
            CacheUtils.putInt(this, bookPath, scrollY) //保存访问的位置
            Toast.makeText(this,"书签保存成功！",Toast.LENGTH_SHORT).show()

        }
        binding?.fabSearch?.setOnClickListener {
            binding?.llSearch?.visibility = View.VISIBLE
        }
        binding?.btnHide?.setOnClickListener {
            binding?.llSearch?.visibility = View.GONE
        }

        //输入文本自动查询
        binding?.etSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            override fun afterTextChanged(key: Editable) {
                //搜索点击
/*                tv_position.setText(
                    "(位置：" + (position + 1) + "/" + all + ")"*/
                binding?.twvWebview?.findAllAsync(key.toString())
                binding?.twvWebview?.setFindListener { position, all, b ->

                }

            }
        })
        binding?.btnPre?.setOnClickListener {
            binding?.twvWebview?.findNext(true)
        }
        binding?.btnPre?.setOnClickListener {
            binding?.twvWebview?.findNext(false)
        }

    }

    private fun initData() {

/*        text = assets.loadText(bookPath)
        binding.tvContent.text = text*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding?.twvWebview?.apply {
                settings.builtInZoomControls = true
                settings.setSupportZoom(true)
                settings.displayZoomControls = true
                settings.javaScriptCanOpenWindowsAutomatically =
                    true//设置js可以直接打开窗口，如window.open()，默认为false
                settings.setJavaScriptEnabled(true)//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
                settings.useWideViewPort = true//设置此属性，可任意比例缩放。大视图模式
                settings.loadWithOverviewMode = true;//和setUseWideViewPort(true)一起解决网页自适应问题
                settings.setAppCacheEnabled(true)//是否使用缓存
                settings.domStorageEnabled = true//DOM Storage
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        //获取保存的位置position
                        val position = CacheUtils.getInt(this@DetailActivity, bookPath, 0)
                        view?.scrollTo(0, position);//webview加载完成后直接定位到上次访问的位置
                    }

                }
            }
        }

        Thread(Runnable {
            if (bookPath.endsWith(".doc")) {
                val wordToHtml = WordToHtml(bookPath, File(bookPath))
                text = wordToHtml.content
            }
            if (bookPath.endsWith(".docx")) {
                val wordToHtml = WordToHtml(bookPath, File(bookPath))
                text = wordToHtml.content
            }

            runOnUiThread {
                binding?.twvWebview?.loadData(
                    text,
                    "text/html",
                    "utf-8"
                )
            }
        }).start()


    }

    companion object {
        fun createIntent(context: Context) = Intent(context, DetailActivity::class.java)
    }

/*    fun readWord( filePath:String):String {
// 创建输入流读取doc文件
        var inputStream:InputStream
         val text:String? = null
        try {
inputStream =  FileInputStream( File(filePath))
WordExtractor extractor = null;
// 创建WordExtractor
extractor = new WordExtractor();
// 对doc文件进行提取
text = extractor.extractText(in);
        } catch (FileNotFoundException e) {
e.printStackTrace();
        } catch (Exception e) {
e.printStackTrace();
        }
        return text;
    }*/
}
