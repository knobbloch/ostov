package com.knobblochsapplication.app.appcomponents.utility.Docx;

import android.os.Environment;

import com.knobblochsapplication.app.appcomponents.utility.Node;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DocxFile {
    public static void make_docx(Node goal) throws IOException {
        XWPFDocument xwpfDocument = new XWPFDocument();
        XWPFParagraph xwpfParagraph = xwpfDocument.createParagraph();
        XWPFRun xwpfRun = xwpfParagraph.createRun();
        xwpfRun.setFontSize(14);
        xwpfRun.setFontFamily("Times New Roman");

        xwpfRun.setText("Название цели: " + goal.getName());
        xwpfRun.addBreak();
        xwpfRun.setText("Ранг важности: " + goal.getPriority());
        xwpfRun.addBreak();
        xwpfRun.setText("Дата дедлайна: " + goal.getDeadline());
        xwpfRun.addBreak();
        if (goal.isDone()) {
            xwpfRun.setText("Цель выполнена");
            xwpfRun.addBreak();
        } else {
            xwpfRun.setText("Цель не выполнена");
            xwpfRun.addBreak();
        }
        xwpfRun.setText(goal.getDescription());

        xwpfRun.addBreak();

        int index = 1;
        for (Node item : goal.getTasks()) {
            xwpfRun = DocxFile.collecting_information(Integer.toString(index), item, xwpfRun);
            index++;
        }
        DocxFile.writeing(xwpfDocument, goal.getName());
    }

    public static XWPFRun collecting_information(String index, Node goal, XWPFRun xwpfRun) throws IOException {
        xwpfRun.setText(index);
        xwpfRun.addBreak();
        xwpfRun.setText("Название задачи: " + goal.getName());
        xwpfRun.addBreak();
        xwpfRun.setText("Ранг важности: " + goal.getPriority());
        xwpfRun.addBreak();
        xwpfRun.setText("Дата дедлайна: " + goal.getDeadline());
        xwpfRun.addBreak();
        if (goal.isDone()) {
            xwpfRun.setText("Задача выполнена");
            xwpfRun.addBreak();
        } else {
            xwpfRun.setText("Задача не выполнена");
            xwpfRun.addBreak();
        }
        xwpfRun.setText(goal.getDescription());
        xwpfRun.addBreak();

        if (goal.getTasks().size() == 0) {
            return xwpfRun;
        }
        int i = 1;
        for (Node item : goal.getTasks()) {
            xwpfRun = collecting_information(index + "." + i, item, xwpfRun);
            i++;
        }
        xwpfRun.addBreak();
        return xwpfRun;
    }

    public static void writeing(XWPFDocument xwpfDocument, String name) throws IOException {
        File filePath;
        filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name + ".docx");

        try {
            if (!filePath.exists()) {
                filePath.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        xwpfDocument.write(fileOutputStream);

        fileOutputStream.flush();
        fileOutputStream.close();
        xwpfDocument.close();
    }
}
