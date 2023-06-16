package com.knobblochsapplication.app.modules.Docx;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.knobblochsapplication.app.modules.File_system.File_Manager;
import com.knobblochsapplication.app.modules.File_system.Goal;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Doxc_File{
    public static void make_docx(Context context,int goal_id) throws IOException {
        int PERMISSION_STORAGE = 101;
        if (PermissionUtils.hasPermissions(context)) {
            XWPFDocument xwpfDocument = new XWPFDocument();
            XWPFParagraph xwpfParagraph = xwpfDocument.createParagraph();
            XWPFRun xwpfRun = xwpfParagraph.createRun();
            xwpfRun.setFontSize(14);
            xwpfRun.setFontFamily("Times New Roman");

            xwpfRun = Doxc_File.collecting_information(context, goal_id, goal_id, "1.", xwpfRun);

            Doxc_File.writeing(context, xwpfDocument, File_Manager.Find_task_by_id(goal_id,goal_id).getName());
        }else{
            PermissionUtils.requestPermissions((Activity) context, PERMISSION_STORAGE);
        }
    }

    public static XWPFRun collecting_information(Context context,int goal_id,int task_id,String nomber,XWPFRun xwpfRun ) throws IOException {
        Goal finder=File_Manager.Find_task_by_id(goal_id,task_id);

        xwpfRun.setText(nomber+" "+finder.getName());xwpfRun.addBreak();
        xwpfRun.setText("ранг важности:" +finder.getRank());xwpfRun.addBreak();
        xwpfRun.setText("дата дедлайна:" +finder.getExpiresAt());xwpfRun.addBreak();
        if (finder.isComplete()){
            xwpfRun.setText("задача выполнена");xwpfRun.addBreak();
        }else{
            xwpfRun.setText("задача не выполнена");xwpfRun.addBreak();
        }
        xwpfRun.setText(finder.getDescription());xwpfRun.addBreak();xwpfRun.addBreak();

        if (finder.getChildren().size()!=0){
            ArrayList<Integer> children= finder.getChildren();
            for (int i =0;i<finder.getChildren().size();i++){
                xwpfRun=Doxc_File.collecting_information(context,goal_id,children.get(i),nomber+(i+1)+".",xwpfRun);
            }
        }
        return xwpfRun;
    }

    public static void writeing(Context context, XWPFDocument xwpfDocument,String name) throws IOException {
        File filePath = null;
        filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name+".docx");


        try {
            if (!filePath.exists()){
                filePath.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        xwpfDocument.write(fileOutputStream);

        if (fileOutputStream!=null){
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        xwpfDocument.close();
        Toast.makeText(context, "Документ был успешно создан в папке "+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getName(), Toast.LENGTH_LONG).show();
    }
}
