@file:Suppress("DEPRECATION")

package com.fankes.bitmaptobase64

import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DOCUMENTS
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.activity_main_button).setOnClickListener {
            try {
                val bitmap = BitmapFactory.decodeFile(
                    Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS)
                        .toString() + "/BitmapToBase64/orig.png"
                )
                val baos = ByteArrayOutputStream() // outputstream
                bitmap.compress(CompressFormat.PNG, 100, baos)
                Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT).writeToFile(
                    Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS)
                        .toString() + "/BitmapToBase64/out.txt"
                )
                Toast.makeText(this, "操作成功", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * 写入到文本文件
     * 若目标存在自动覆盖
     * @param path 路径
     */
    private fun String.writeToFile(path: String) {
        File(path).also { file ->
            file.delete()
            file.createNewFile()
            FileOutputStream(file).apply {
                write(toByteArray())
                flush()
                close()
            }
        }
    }
}