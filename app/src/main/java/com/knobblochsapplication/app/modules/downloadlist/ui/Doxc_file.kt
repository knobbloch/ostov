package com.knobblochsapplication.app.modules.downloadlist.ui

import android.app.Activity
import android.content.Context
import android.os.Environment
import android.widget.Toast
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class Doxc_file {
    companion object {
        @Throws(IOException::class)
        fun make_docx(context: Context?, goal_id: Int) {
            val PERMISSION_STORAGE = 101
            if (PermissionUtilits.hasPermissions(context)) {
//            val xwpfDocument = XWPFDocument()
//            val xwpfParagraph = xwpfDocument.createParagraph()
//            var xwpfRun = xwpfParagraph.createRun()
//            xwpfRun.fontSize = 14
//            xwpfRun.fontFamily = "Times New Roman"
//            xwpfRun = collecting_information(context, goal_id, goal_id, "1.", xwpfRun)
//            writeing(
//                context,
//                xwpfDocument,
//                File_Manager.Find_task_by_id(goal_id, goal_id).getName()
//            )
            } else {
                PermissionUtilits.requestPermissions(context as Activity, PERMISSION_STORAGE)
            }
        }

//    @Throws(IOException::class)
//    fun collecting_information(
//        context: Context?,
//        goal_id: Int,
//        task_id: Int,
//        nomber: String,
//        xwpfRun: XWPFRun
//    ): XWPFRun {
//        var xwpfRun = xwpfRun
//        val finder: Goal = File_Manager.Find_task_by_id(goal_id, task_id)
//        xwpfRun.setText(nomber + " " + finder.getName())
//        xwpfRun.addBreak()
//        xwpfRun.setText("ранг важности:" + finder.getRank())
//        xwpfRun.addBreak()
//        xwpfRun.setText("дата дедлайна:" + finder.getExpiresAt())
//        xwpfRun.addBreak()
//        if (finder.isComplete()) {
//            xwpfRun.setText("задача выполнена")
//            xwpfRun.addBreak()
//        } else {
//            xwpfRun.setText("задача не выполнена")
//            xwpfRun.addBreak()
//        }
//        xwpfRun.setText(finder.getDescription())
//        xwpfRun.addBreak()
//        xwpfRun.addBreak()
//        if (finder.getChildren().size() !== 0) {
//            val children: ArrayList<Int> = finder.getChildren()
//            for (i in 0 until finder.getChildren().size()) {
//                xwpfRun = collecting_information(
//                    context, goal_id,
//                    children[i], nomber + (i + 1) + ".", xwpfRun
//                )
//            }
//        }
//        return xwpfRun
//    }

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