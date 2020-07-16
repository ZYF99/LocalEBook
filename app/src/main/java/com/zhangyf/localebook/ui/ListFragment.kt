package com.zhangyf.localebook.ui

import android.content.Context
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
import com.hanlyjiang.library.fileviewer.tbs.TBSFileViewActivity
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
        FileViewer.viewFile(context,filePath)
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