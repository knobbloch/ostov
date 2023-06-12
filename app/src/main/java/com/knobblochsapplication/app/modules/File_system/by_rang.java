package com.knobblochsapplication.app.modules.File_system;

import android.content.Context;

import java.io.IOException;
import java.util.Comparator;

public class by_rang implements Comparator<Integer>{

    Context context;
    Integer goal_id;
    public by_rang(Context _context, int _goal_id) {
        context=_context;
        goal_id=_goal_id;
    }

    public int compare(Integer o1, Integer o2){
        try {
            return Integer.compare(File_Manager.Find_task_by_id(context,goal_id,o1).getRank(), File_Manager.Find_task_by_id(context,goal_id,o2).getRank());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
