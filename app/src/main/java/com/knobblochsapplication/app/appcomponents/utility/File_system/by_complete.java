package com.knobblochsapplication.app.appcomponents.utility.File_system;

import android.content.Context;

import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;

public class by_complete implements Comparator<Integer> {

    Context context;
    Integer goal_id;
    public by_complete(Context _context, int _goal_id) {
        context=_context;
        goal_id=_goal_id;
    }

    public int compare(Integer o1, Integer o2) {
        try {
            if(Objects.requireNonNull(File_Manager.Find_task_by_id(context, goal_id, o1)).isComplete()==File_Manager.Find_task_by_id(context,goal_id,o2).isComplete()){
                return 0;
            }
            if(File_Manager.Find_task_by_id(context, goal_id, o1).isComplete() && !File_Manager.Find_task_by_id(context, goal_id, o2).isComplete()){
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
