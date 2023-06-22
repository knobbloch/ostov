package com.knobblochsapplication.app.modules.diagramview.data.downloadlist.ui

import android.app.Activity
import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.appcomponents.utility.Node
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFRun
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class Doxc_file {
    companion object {
        @Throws(IOException::class)
        fun make_docx(context: Context?, goal_uid: String) {
            if(context!=null) {
                val app_storage: AppStorage = AppStorage(context)

                var goal = app_storage.loadByUid(goal_uid)


                val PERMISSION_STORAGE = 101
                if (PermissionUtilits.hasPermissions(context)) {

                    val xwpfDocument = XWPFDocument()
                    val xwpfParagraph = xwpfDocument.createParagraph()
                    var xwpfRun = xwpfParagraph.createRun()
                    xwpfRun.fontSize = 14
                    xwpfRun.fontFamily = "Times New Roman"
                    xwpfRun = goal?.let { collecting_information(context, it, "1.", xwpfRun) }
                    if (goal != null) {
                        goal.name?.let {
                            writeing(
                                context,
                                xwpfDocument,
                                it
                            )
                        }
                    }

                } else {
                    PermissionUtilits.requestPermissions(context as Activity, PERMISSION_STORAGE)
                }
            }
        }

    @Throws(IOException::class)
    fun collecting_information(
        context: Context?,
        node: Node,
        nomber: String,
        xwpfRun: XWPFRun
    ): XWPFRun {
        var xwpfRun = xwpfRun
        xwpfRun.setText(nomber + " " + node.name)
        xwpfRun.addBreak()
        xwpfRun.setText("ранг важности:" + node.priority)
        xwpfRun.addBreak()
        xwpfRun.setText("дата дедлайна:" + node.deadline)
        xwpfRun.addBreak()
        if (node.isDone) {
            xwpfRun.setText("задача выполнена")
            xwpfRun.addBreak()
        } else {
            xwpfRun.setText("задача не выполнена")
            xwpfRun.addBreak()
        }
        xwpfRun.setText(node.description)
        xwpfRun.addBreak()
        xwpfRun.addBreak()
        if (node.tasks.size !== 0) {
            for (i in 0 until node.tasks.size) {
                xwpfRun = collecting_information(
                    context,
                    node.tasks.get(i),
                    nomber + (i + 1) + ".",
                    xwpfRun
                )
            }
        }
        return xwpfRun
    }

        @Throws(IOException::class)
        fun writeing(context: Context?, xwpfDocument: XWPFDocument, name: String) {
            var filePath: File? = null
            filePath = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "$name.docx"
            )
            try {
                if (!filePath.exists()) {
                    filePath.createNewFile()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val fileOutputStream = FileOutputStream(filePath)
            xwpfDocument.write(fileOutputStream)
            if (fileOutputStream != null) {
                fileOutputStream.flush()
                fileOutputStream.close()
            }
            xwpfDocument.close()
            Toast.makeText(
                context,
                "Документ был успешно создан в папке " + Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS
                ).name,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}