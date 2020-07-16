package com.zhangyf.localebook

import android.app.Application
import android.util.Log
import com.tencent.smtt.sdk.QbSdk

class MainApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        QbSdk.setDownloadWithoutWifi(true)
        QbSdk.initX5Environment(applicationContext, object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {
                Log.d("QbSdk~~", "onCoreInitFinished")
            }

            override fun onViewInitFinished(initResult: Boolean) {
                Log.e("QbSdk~~", "onViewInitFinished$initResult")
            }
        })
    }
}