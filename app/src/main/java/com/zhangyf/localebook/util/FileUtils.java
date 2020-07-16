package com.zhangyf.localebook.util;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class FileUtils {

    /**
     * 拷贝Asset文件夹到sd卡
     *
     * @param context
     * @param fromDir 如果拷贝assets ，则传入 ""
     * @param destDir 目的地
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void copyAssetsDir(Context context, String fromDir, String destDir) throws IOException {
        String[] files = context.getAssets().list(fromDir);
        for (String r : files) {
            String a = new File(context.getExternalCacheDir().getPath() + "book", r).getAbsolutePath();
            String[] typefiles = context.getAssets().list(fromDir + "/" + r);
            for (String f : typefiles) {
                copyFile(context.getAssets().open(fromDir + File.separator + r + File.separator + f),
                        destDir + File.separator + r + File.separator + f);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void copyFile(InputStream in, String newPath) {
        Objects.requireNonNull(
                new File(newPath).getParentFile()
        ).mkdirs();
        try (
                InputStream inStream = in;
                FileOutputStream fs = new FileOutputStream(newPath)
        ) {
            int byteread;
            byte[] buffer = new byte[4096];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
                fs.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
