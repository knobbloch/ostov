package com.knobblochsapplication.app.modules.File_system;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class File_Manager {

    public static void Write_goal_by_list(Context context, List<Goal> goal_list, int goal_id) throws IOException {
        Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting() // можно генерировать красивый жсон этим параметром
                .create();

        String jsonContent = prettyGson.toJson(goal_list); // просто суем гсону джава объект и получаем жсон контент

        //Записываем файл
        File file = new File(context.getFilesDir(), goal_id + ".json");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(jsonContent);
        bufferedWriter.close();
    }

    public static void Write_goal(Context context, int _id, String _name, String _description, long _datetime, int _rang) throws IOException {
        // Создадим гсон объект
        Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting() // можно генерировать красивый жсон этим параметром
                .create();

        //Создаём объект типа цель
        Goal goal = new Goal(_id, _name, _description, _rang, _datetime);
        //Создаём список чтобы упаковать его
        List<Goal> goal_list = new ArrayList<>();
        goal_list.add(goal);

        String jsonContent = prettyGson.toJson(goal_list); // просто суем гсону джава объект и получаем жсон контент

        //Записываем файл
        File file = new File(context.getFilesDir(), _id + ".json");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(jsonContent);
        bufferedWriter.close();
    }

    public static List<Goal> Find_goal_by_id(Context context, int id) throws IOException {
        Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting() // можно генерировать красивый жсон этим параметром
                .create();
        //Находим нужный файл
        File new_file = new File(context.getFilesDir(), id + ".json");

        //Читаем его и преобразовываем в json
        FileReader fileReader = new FileReader(new_file);
        BufferedReader reader = new BufferedReader(fileReader);

        // Передаём гсону тип, который хотим получить из жсон контента
        // Поскольку мы получаем List<Goal>, параметризованный тип, с ним посложнее:
        Type type = TypeToken.getParameterized(List.class, Goal.class).getType();
        // Без параметризациии можно передавать просто Goal.class
        // В случае со словарём Map<K,V> будет:
        // Type mapType = TypeToken.getParameterized(Map.class, K.class, V.class).getType();

        List<Goal> readData = prettyGson.fromJson(reader, type);
        return readData;
    }

    public static void Write_task(Context context, int goal_id, int _id, String _name, String _description, long _datetime, int _rang) throws IOException {
        Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting() // можно генерировать красивый жсон этим параметром
                .create();

        //Создаём объект типа цель
        Goal goal = new Goal(_id, _name, _description, _rang, _datetime);
        //Находим цель и упаковываем задачу туда
        List<Goal> goal_list = File_Manager.Find_goal_by_id(context, goal_id);
        goal_list.add(goal);

        //Удаляем файл, чтобы потом перезаписать его
        File_Manager.Delete_goal(context, goal_id);

        String jsonContent = prettyGson.toJson(goal_list); // просто суем гсону джава объект и получаем жсон контент

        //Записываем файл
        File file = new File(context.getFilesDir(), goal_id + ".json");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(jsonContent);
        bufferedWriter.close();
    }

    public static Goal Find_task_by_id(Context context, int goal_id, int id) throws IOException {
        Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting() // можно генерировать красивый жсон этим параметром
                .create();

        //Находим цель
        List<Goal> goal_list = File_Manager.Find_goal_by_id(context, goal_id);

        for (int i = 0; i < goal_list.size(); i++) {
            if (goal_list.get(i).getId() == id) {
                Goal readData = goal_list.get(i);
                return readData;
            }
        }
        return null;
    }

    public static void Delete_goal(Context context, int id) {
        //Находим файл,удаляем файл...
        File file = new File(context.getFilesDir(), id + ".json");
        if (file.exists()) {
            file.delete();
        }
    }

    public static void Delete_task(Context context, int goal_id, int id) throws IOException {
        //Находим задачу по id
        Goal task = File_Manager.Find_task_by_id(context, goal_id, id);

        //Удалим ребёнка из списка детей родителя
        if (task.getParentId() != 0) {
            File_Manager.remove_children(context, goal_id, task.getParentId(), id);
        }

        //Также удаляем всех детей этой задачи
        for (int i = 0; i < task.getChildren().size(); i++) {
            File_Manager.Delete_task(context, goal_id, task.getChildren().get(i));
        }

        //Находим файл цели
        List<Goal> goal_list = File_Manager.Find_goal_by_id(context, goal_id);

        //Сохраняем индекс
        int task_ind = 0;
        for (int i = 0; i < goal_list.size(); i++) {
            if (goal_list.get(i).getId() == task.getId()) {
                task_ind = i;
            }
        }


        //Удаляем из списка цели и перезаписываем файл
        goal_list.remove(task_ind);

        File_Manager.Write_goal_by_list(context, goal_list, goal_id);

        if (task.getParentId() != 0) {
            //Проверяем родителя удалённой подзадачи на выполненность
            if (File_Manager.give_percent(context, goal_id, task.getParentId()) == 100) {
                File_Manager.change_complete(context, goal_id, task.getParentId(), true);
            }
        }
    }

    public static void change_rang(Context context, int goal_id, int id, int change) throws IOException {
        //Находим файл цели
        List<Goal> goal_list = File_Manager.Find_goal_by_id(context, goal_id);

        //Находим задачу по id
        Goal task = File_Manager.Find_task_by_id(context, goal_id, id);

        //Сохраняем индекс
        int task_ind = 0;
        for (int i = 0; i < goal_list.size(); i++) {
            if (goal_list.get(i).getId() == task.getId()) {
                task_ind = i;
            }
        }

        //Меняем ранг
        task.setRank(change);

        //Добавляем изменения и записываем в файл
        goal_list.set(task_ind, task);

        File_Manager.Write_goal_by_list(context, goal_list, goal_id);
    }

    public static void change_info(Context context, int goal_id, int id, int param_nomber, String change) throws IOException {
        //Находим файл цели
        List<Goal> goal_list = File_Manager.Find_goal_by_id(context, goal_id);


        //Находим задачу по id и меняем информацию в зависимости от заданного параметра
        Goal task = File_Manager.Find_task_by_id(context, goal_id, id);

        //Сохраняем индекс
        int task_ind = 0;
        for (int i = 0; i < goal_list.size(); i++) {
            if (goal_list.get(i).getId() == task.getId()) {
                task_ind = i;
            }
        }

        if (param_nomber == 1) {
            task.setName(change);
        }
        if (param_nomber == 2) {
            task.setDescription(change);
        }

        //Добавляем изменения и записываем в файл
        goal_list.set(task_ind, task);

        File_Manager.Write_goal_by_list(context, goal_list, goal_id);
    }

    public static void change_complete(Context context, int goal_id, int id, boolean change) throws IOException {
        //Находим задачу по id и менеяем значение выполненности
        Goal task = File_Manager.Find_task_by_id(context, goal_id, id);

        //Если задача выполнена, все её подзадачи автаматически выполняются
        if (change) {
            for (int i = 0; i < task.getChildren().size(); i++) {
                File_Manager.change_complete(context, goal_id, task.getChildren().get(i), change);
            }
        }

        //Находим файл цели
        List<Goal> goal_list = File_Manager.Find_goal_by_id(context, goal_id);

        //Сохраняем индекс
        int task_ind = 0;
        for (int i = 0; i < goal_list.size(); i++) {
            if (goal_list.get(i).getId() == task.getId()) {
                task_ind = i;
            }
        }


        task.setComplete(change);

        //Добавляем изменения и записываем в файл
        goal_list.set(task_ind, task);


        File_Manager.Write_goal_by_list(context, goal_list, goal_id);
        //Автоматически меняем значение выполненности родителея, если все его дети выполненны
//        if(task.getParentId()!=0) {
//            if (File_Manager.give_percent(context, goal_id, task.getParentId()) == 100) {
//                File_Manager.change_complete(context, goal_id, task.getParentId(), true);
//            }
//            else {
//                File_Manager.change_complete(context, goal_id, task.getParentId(), false);
//            }
//        }
    }

    public static void remove_children(Context context, int goal_id, int parent_id, int children_id) throws IOException {
        List<Goal> goal_list = File_Manager.Find_goal_by_id(context, goal_id);
        //Находим ребёнка и родителя
        Goal parent = Find_task_by_id(context, goal_id, parent_id);
        Goal children = Find_task_by_id(context, goal_id, children_id);

        //Сохраняем индексы родителя и ребёнка
        int parent_ind = 0;
        int children_ind = 0;
        for (int i = 0; i < goal_list.size(); i++) {
            if (goal_list.get(i).getId() == parent_id) {
                parent_ind = i;
            }
            if (goal_list.get(i).getId() == children_id) {
                children_ind = i;
            }
        }

        //Удаляем ребёнка из списка детей родителя
        int remem = 0;
        for (int i = 0; i < parent.getChildren().size(); i++) {
            if (parent.getChildren().get(i) == children_id) {
                remem = i;
            }
        }
        parent.getChildren().remove(remem);


        //Меняем id родителя у ребёнка
        children.setParentId(0);

        //Добавляем изменения и записываем в файл
        goal_list.set(parent_ind, parent);
        goal_list.set(children_ind, children);


        File_Manager.Write_goal_by_list(context, goal_list, goal_id);

        //Проверяем родителя удалённой подзадачи на выполненность
        if (File_Manager.give_percent(context, goal_id, parent_id) == 100) {
            File_Manager.change_complete(context, goal_id, parent_id, true);
        }
    }

    public static boolean ask_complete(Context context, int goal_id, int id) throws IOException {
        //Находим задачу по id
        Goal task = File_Manager.Find_task_by_id(context, goal_id, id);

        //Выводим её выполненность
        return task.isComplete();
    }

    public static int give_percent(Context context, int goal_id, int id) throws IOException {
        //Достаём задачу из файла
        Goal task = File_Manager.Find_task_by_id(context, goal_id, id);

        //Рассчитываем сколько процентов даётся за выполнение 1 задания
        if (task.getChildren().size() != 0) {
            int one_task = 100 / task.getChildren().size();
            int count_completed = 0;
            //Считаем сколько заданий выполнено
            for (int i = 0; i < task.getChildren().size(); i++) {
                if (File_Manager.ask_complete(context, goal_id, task.getChildren().get(i))) {
                    count_completed += 1;
                }
            }
            //Возвращаем получившееся число
            if (count_completed == task.getChildren().size()) {
                return 100;
            } else {
                return one_task * count_completed;
            }
        } else {
            return 0;
        }
    }

    public static void connect_children_to_parent(Context context, int goal_id, int children_id, int parent_id) throws IOException {
        //Находим ребёнка
        Goal children = Find_task_by_id(context, goal_id, children_id);

        //Если родитель есть в детях ребёнка, то убираем его оттуда
        if (children.getChildren().contains(parent_id)) {
            File_Manager.remove_children(context, goal_id, children_id, parent_id);
            children = Find_task_by_id(context, goal_id, children_id);
        }

        //находим родителя
        Goal parent = Find_task_by_id(context, goal_id, parent_id);

        //если у ребёнка есть родитель,удаляем ребёнка из списка детей его родителя
        if (children.getParentId() != 0) {
            File_Manager.remove_children(context, goal_id, children.getParentId(), children_id);
        }

        //Находим файл цели
        List<Goal> goal_list = File_Manager.Find_goal_by_id(context, goal_id);

        //Сохраняем индексы родителя и ребёнка
        int parent_ind = 0;
        int children_ind = 0;
        for (int i = 0; i < goal_list.size(); i++) {
            if (goal_list.get(i).getId() == parent_id) {
                parent_ind = i;
            }
            if (goal_list.get(i).getId() == children_id) {
                children_ind = i;
            }
        }

        //Добавляем ребёнка в список детей родителя
        ArrayList<Integer> parent_list = parent.getChildren();
        parent_list.add(parent_list.size(), children_id);
        parent.setChildren(parent_list);


        //Меняем id родителя у ребёнка
        children.setParentId(parent_id);

        //Добавляем изменения и записываем в файл
        goal_list.set(parent_ind, parent);
        goal_list.set(children_ind, children);


        File_Manager.Write_goal_by_list(context, goal_list, goal_id);
        if (!children.isComplete()) {
            File_Manager.change_complete(context, goal_id, parent_id, false);
        }
    }

    public static void make_children_new_parent(Context context, int goal_id, int children_id, int parent_id) throws IOException {
        //Находим ребёнка и родителя
        Goal parent = Find_task_by_id(context, goal_id, parent_id);
        //Перекидываем детей родителя ребёнку
        for (int i = 0; i < parent.getChildren().size(); i++) {
            if (parent.getChildren().get(i) != children_id) {
                File_Manager.connect_children_to_parent(context, goal_id, parent.getChildren().get(i), children_id);
            }
        }

        //Присоеденяем ребёнка к родителю родителя
        if (parent.getParentId() != 0) {
            File_Manager.connect_children_to_parent(context, goal_id, children_id, parent.getParentId());
        } else {
            List<Goal> list_for_swap = File_Manager.Find_goal_by_id(context, goal_id);
            File_Manager.Delete_goal(context, goal_id);
            goal_id = children_id;
            File_Manager.Write_goal_by_list(context, list_for_swap, goal_id);
        }

        //Находим файл цели
        List<Goal> goal_list = File_Manager.Find_goal_by_id(context, goal_id);

        //Сохраняем индексы родителя и ребёнка
        int parent_ind = 0;
        for (int i = 0; i < goal_list.size(); i++) {
            if (goal_list.get(i).getId() == parent_id) {
                parent_ind = i;
            }
        }

        //Обнуляем список родителя
        parent.setChildren(new ArrayList<>());

        //Если очень надо будет,то родителя можно удалять!

        //Добавляем изменения и записываем в файл
        goal_list.set(parent_ind, parent);

        File_Manager.Write_goal_by_list(context, goal_list, goal_id);
    }

    public static boolean ask_priority_to_hight(Context context, int goal_id, int id) throws IOException {
        int count = 0;
        int par = id;

        while (count != 5 && par != 0) {
            Goal task = File_Manager.Find_task_by_id(context, goal_id, par);
            count += 1;
            par = task.getParentId();
        }
        return count == 5;
    }

    public static void sort_by_id(Context context, int goal_id) throws IOException {
        //Находим файл цели
        List<Goal> goal_list = File_Manager.Find_goal_by_id(context, goal_id);

        for (int i = 0; i < goal_list.size(); i++) {
            Collections.sort(goal_list.get(i).getChildren());
        }

        //Записываем в файл
        File_Manager.Write_goal_by_list(context, goal_list, goal_id);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void sort_by_rang(Context context, int goal_id) throws IOException {
        //Находим файл цели
        List<Goal> goal_list = File_Manager.Find_goal_by_id(context, goal_id);

        for (int i = 0; i < goal_list.size(); i++) {
            goal_list.get(i).getChildren().sort(new by_rang(context, goal_id));
        }

        //Записываем в файл
        File_Manager.Write_goal_by_list(context, goal_list, goal_id);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void sort_by_complete(Context context, int goal_id) throws IOException {
        //Находим файл цели
        List<Goal> goal_list = File_Manager.Find_goal_by_id(context, goal_id);

        for (int i = 0; i < goal_list.size(); i++) {
            goal_list.get(i).getChildren().sort(new by_complete(context, goal_id));
        }

        //Записываем в файл
        File_Manager.Write_goal_by_list(context, goal_list, goal_id);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void sort_by_expires(Context context, int goal_id) throws IOException {
        //Находим файл цели
        List<Goal> goal_list = File_Manager.Find_goal_by_id(context, goal_id);

        for (int i = 0; i < goal_list.size(); i++) {
            goal_list.get(i).getChildren().sort(new by_expires(context, goal_id));
        }

        //Записываем в файл
        File_Manager.Write_goal_by_list(context, goal_list, goal_id);
    }
}
