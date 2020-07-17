package com.zhangyf.localebook.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.hanlyjiang.library.fileviewer.FileViewer
import com.zhangyf.localebook.R
import com.zhangyf.localebook.adapter.BookListRecyclerViewAdapter
import com.zhangyf.localebook.databinding.FragmentListBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * A fragment representing a list of Items.
 */
class ListFragment(val bookType: String) : Fragment() {

    val binding by lazy {
        DataBindingUtil.inflate<FragmentListBinding>(
            LayoutInflater.from(context),
            R.layout.fragment_list,
            null,
            false
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initData()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun initView() {
        binding.rvBook.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = BookListRecyclerViewAdapter { jumpToDeatil(it) }
        }
    }

    private fun initData() {
        val bookNameList = context?.assets?.list("book/${bookType}")?.map { it } ?: emptyList()
        (binding.rvBook.adapter as BookListRecyclerViewAdapter).replaceData(bookNameList)
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun jumpToDeatil(bookName: String) {

        val filePath = "${context?.externalCacheDir?.path}/book/${bookType}/${bookName}"
        context?.let {
            startActivity(DetailActivity.createIntent(it).putExtra(KEY_PATH, filePath))
        }

/*        AlertDialog.Builder(context)
            .setPositiveButton("其他软件打开") { _, _ ->
                startActivity(openAndroidFile(filePath))
            }
            .setNegativeButton("APP内打开") { _, _ ->
                context?.let {
                    startActivity(DetailActivity.createIntent(it).putExtra(KEY_PATH, filePath))
                }
                //FileViewer.viewFile(context,filePath)
            }.create().show()*/

    }

    companion object {
        fun newInstance(bookType: String) = ListFragment(bookType)
    }

    private fun Context.copyAssetAndWrite(fileName: String): String {
        try {
            val cacheDir = cacheDir
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
            val outFile = File(cacheDir, fileName)
            if (!outFile.exists()) {
                val res = outFile.createNewFile()
                if (!res) {
                    return ""
                }
            } else {
                if (outFile.length() > 10) {
                    //表示已经写入一次
                    return outFile.path
                }
            }
            val inputStream = assets.open(fileName)
            val fos = FileOutputStream(outFile)
            val buffer = ByteArray(1024)
            var byteCount: Int;
            inputStream.read(buffer)
            while (inputStream.read(buffer).also { byteCount = it } != -1) {
                fos.write(buffer, 0, byteCount)
            }
            fos.flush()
            inputStream.close()
            fos.close()
            return outFile.path
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

    fun openAndroidFile(filepath: String?): Intent? {
        val intent = Intent()
        val file = File(filepath)
        //        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//设置标记
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.action = Intent.ACTION_VIEW //动作，查看
        intent.setDataAndType(Uri.fromFile(file), getMIMEType(file)) //设置类型
        return intent
    }

    private val MIME_MapTable =
        arrayOf(
            arrayOf(".3gp", "video/3gpp"),
            arrayOf(".apk", "application/vnd.android.package-archive"),
            arrayOf(".asf", "video/x-ms-asf"),
            arrayOf(".avi", "video/x-msvideo"),
            arrayOf(".bin", "application/octet-stream"),
            arrayOf(".bmp", "image/bmp"),
            arrayOf(".c", "text/plain"),
            arrayOf(".class", "application/octet-stream"),
            arrayOf(".conf", "text/plain"),
            arrayOf(".cpp", "text/plain"),
            arrayOf(".doc", "application/msword"),
            arrayOf(".docx", "application/msword"),
            arrayOf(".exe", "application/octet-stream"),
            arrayOf(".gif", "image/gif"),
            arrayOf(".gtar", "application/x-gtar"),
            arrayOf(".gz", "application/x-gzip"),
            arrayOf(".h", "text/plain"),
            arrayOf(".htm", "text/html"),
            arrayOf(".html", "text/html"),
            arrayOf(".jar", "application/java-archive"),
            arrayOf(".java", "text/plain"),
            arrayOf(".jpeg", "image/jpeg"),
            arrayOf(".JPEG", "image/jpeg"),
            arrayOf(".jpg", "image/jpeg"),
            arrayOf(".js", "application/x-javascript"),
            arrayOf(".log", "text/plain"),
            arrayOf(".m3u", "audio/x-mpegurl"),
            arrayOf(".m4a", "audio/mp4a-latm"),
            arrayOf(".m4b", "audio/mp4a-latm"),
            arrayOf(".m4p", "audio/mp4a-latm"),
            arrayOf(".m4u", "video/vnd.mpegurl"),
            arrayOf(".m4v", "video/x-m4v"),
            arrayOf(".mov", "video/quicktime"),
            arrayOf(".mp2", "audio/x-mpeg"),
            arrayOf(".mp3", "audio/x-mpeg"),
            arrayOf(".mp4", "video/mp4"),
            arrayOf(".mpc", "application/vnd.mpohun.certificate"),
            arrayOf(".mpe", "video/mpeg"),
            arrayOf(".mpeg", "video/mpeg"),
            arrayOf(".mpg", "video/mpeg"),
            arrayOf(".mpg4", "video/mp4"),
            arrayOf(".mpga", "audio/mpeg"),
            arrayOf(".msg", "application/vnd.ms-outlook"),
            arrayOf(".ogg", "audio/ogg"),
            arrayOf(".pdf", "application/pdf"),
            arrayOf(".png", "image/png"),
            arrayOf(".pps", "application/vnd.ms-powerpoint"),
            arrayOf(".ppt", "application/vnd.ms-powerpoint"),
            arrayOf(".pptx", "application/vnd.ms-powerpoint"),
            arrayOf(".prop", "text/plain"),
            arrayOf(".rar", "application/x-rar-compressed"),
            arrayOf(".rc", "text/plain"),
            arrayOf(".rmvb", "audio/x-pn-realaudio"),
            arrayOf(".rtf", "application/rtf"),
            arrayOf(".sh", "text/plain"),
            arrayOf(".tar", "application/x-tar"),
            arrayOf(".tgz", "application/x-compressed"),
            arrayOf(".txt", "text/plain"),
            arrayOf(".wav", "audio/x-wav"),
            arrayOf(".wma", "audio/x-ms-wma"),
            arrayOf(".wmv", "audio/x-ms-wmv"),
            arrayOf(".wps", "application/vnd.ms-works"),
            arrayOf(".xml", "text/plain"),
            arrayOf(".z", "application/x-compress"),
            arrayOf(".zip", "application/zip"),
            arrayOf("", "*/*")
        )

    private fun getMIMEType(file: File): String? {
        var type = "*/*"
        val fName = file.name
        //获取后缀名前的分隔符"."在fName中的位置。
        val dotIndex = fName.lastIndexOf(".")
        if (dotIndex < 0) return type
        /* 获取文件的后缀名 */
        val fileType = fName.substring(dotIndex, fName.length).toLowerCase()
        if (fileType == null || "" == fileType) return type
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (i in MIME_MapTable.indices) {
            if (fileType == MIME_MapTable[i][0]) type = MIME_MapTable[i][1]
        }
        return type
    }

}

//val filePath = "book/${bookType}/${bookName}"
//val uri = Uri.fromFile(File(filePath))
//Log.d("!!!!!", uri.path)
//FileViewer.viewPDFWithMuPDFByPath( context,  filePath)
//val a = context?.copyAssetAndWrite(filePath)

//TBSFileViewActivity.viewFile(context, filePath)

//FileViewer.viewFile(context,filePath)

/*        context?.let {
            startActivity(DetailActivity.createIntent(it).putExtra(KEY_PATH,filePath))
        }*/