package com.zhangyf.localebook.util

import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

//从assets中读取txt
fun AssetManager.loadText(path: String): String? {
    var inputStream: InputStream? = null
    var reader: BufferedReader? = null
    val stringBuilder = StringBuilder()
    try {
        inputStream = this.open(path)
        reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            stringBuilder.append(line+"\n")
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        if (reader != null) {
            try {
                reader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return stringBuilder.toString()
}

//从assets中读取doc
fun AssetManager.loadDoc(path: String): String? {
    var inputStream: InputStream? = null
    var reader: BufferedReader? = null
    val stringBuilder = StringBuilder()
    try {
        inputStream = this.open(path)
        reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            stringBuilder.append(line+"\n")
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        if (reader != null) {
            try {
                reader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return stringBuilder.toString()
}