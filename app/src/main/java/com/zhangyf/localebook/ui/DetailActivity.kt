package com.zhangyf.localebook.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.zhangyf.localebook.R
import com.zhangyf.localebook.databinding.ActivityDetailBinding
import com.zhangyf.localebook.util.loadText
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

const val KEY_PATH = "key_path"

class DetailActivity : AppCompatActivity() {

    val bookPath by lazy { intent.getStringExtra(KEY_PATH) }
    var text: String? = ""


    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
    }

    private fun initData() {
        text = assets.loadText(bookPath)
        binding.tvContent.text = text
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
