package com.knobblochsapplication.app

import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.LayoutProgressDialogBinding
import org.w3c.dom.Text

class MainActivity : BaseActivity<LayoutProgressDialogBinding>(R.layout.layout_progress_dialog) {

    override fun onInitialized() {

    }

    override fun setUpClicks() {

    }
}

interface File_Manager{
    fun make_goal()
    fun del_goal()
    fun make_task()
    fun del_task()
    fun change_goal()
    fun change_task()
    fun give_percent_of_goal()
    fun give_percent_of_task()
    fun find_goal_id()
    fun find_all_children()
    fun print_goal_name()
    fun print_goal_description()
    fun print_goal_datetime()
    fun print_goal_rang()
    fun print_goal_complete()
    fun print_task_name()
    fun print_task_description()
    fun print_task_datetime()
    fun print_task_rang()
    fun print_task_complete()
    fun is_priority_to_hight()
    fun make_children_new_parent()
    fun sort_by_rang()
    fun sort_by_datetime()
    fun sort_by_complete()
}


open class Goal (_id: String, _name: String, _description: String, _datetime: String, _rang: Int){
    var _id_: String = _id
    var _name_: String = _name
    var _description_: String =_description
    var _datetime_: String=_datetime
    var _rang_: Int=_rang
    var _complete_: Boolean=false
    //val _children_: Array<Int> Надо понять как создать массив детей внутри класса
}

class Task(_id: String,_name: String,_description: String,_datetime: String,_rang: Int):Goal(_id,_name,_description,_datetime,_rang){
    //По идее теперь в нутри задачи есть всё то,что есть и в цели
}