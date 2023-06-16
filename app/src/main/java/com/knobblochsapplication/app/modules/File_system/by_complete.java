package com.knobblochsapplication.app.modules.File_system;

import android.content.Context;

import java.io.IOException;
import java.util.Comparator;

public class by_complete implements Comparator<Integer> {

    Integer goal_id;
    public by_complete(int _goal_id) {
        goal_id=_goal_id;
    }

    public int compare(Integer o1, Integer o2) {
        try {
            if(File_Manager.Find_task_by_id(goal_id,o1).isComplete()==File_Manager.Find_task_by_id(goal_id,o2).isComplete()){
                return 0;
            }
            if(File_Manager.Find_task_by_id(goal_id, o1).isComplete() && !File_Manager.Find_task_by_id(goal_id, o2).isComplete()){
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
