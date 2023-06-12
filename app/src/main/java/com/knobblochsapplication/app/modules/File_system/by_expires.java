package com.knobblochsapplication.app.modules.File_system;

import android.content.Context;

import java.io.IOException;
import java.util.Comparator;

public class by_expires implements Comparator<Integer>{

    Context context;
    Integer goal_id;
    public by_expires(Context _context, int _goal_id) {
        context=_context;
        goal_id=_goal_id;
    }

    public int compare(Integer o1, Integer o2){
        try {
            if(File_Manager.Find_task_by_id(context,goal_id,o1).getExpiresAt()==File_Manager.Find_task_by_id(context,goal_id,o2).getExpiresAt()){
                return 0;
            }
            if(File_Manager.Find_task_by_id(context,goal_id,o1).getExpiresAt()<File_Manager.Find_task_by_id(context,goal_id,o2).getExpiresAt()){
                return -1;
            }
            else{
                return 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
