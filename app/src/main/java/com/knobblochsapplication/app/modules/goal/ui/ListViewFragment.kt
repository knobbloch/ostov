package com.knobblochsapplication.app.modules.goal.ui

import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseFragment
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.appcomponents.utility.Node
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.FragmentListViewBinding
import org.koin.android.ext.android.inject

class ListViewFragment: BaseFragment<FragmentListViewBinding>(R.layout.fragment_list_view) {
    private val preferenceHelper: PreferenceHelper by inject()
    private val appStorage: AppStorage by inject()
    lateinit var lastSelectedGoalUid: String

    override fun onResume() {
        super.onResume()
        val uid = preferenceHelper.getLastSelectedGoal()
        if (uid !== null) {
            lastSelectedGoalUid = uid
            updateTree()
        } else {
            binding.goalName.text = ""
        }
    }

    fun updateTree() {
        //todo debug
        val goal = appStorage.getGoalByUid(lastSelectedGoalUid)
        if (goal !== null) {
            binding.goalName.text = PrintTree(goal, "", false)
        }
    }

    fun PrintTree(
        tree: Node,
        indent: String,
        last: Boolean
    ):String {
        //todo debug
        var indent = indent
        var s = ""
        s+= indent + "+- p:" + tree.priority+" "+tree.name+"\n"
        indent += if (last) "   " else "|  "
        for (i in 0 until tree.tasks.count()) {
            s+=PrintTree(tree.tasks[i], indent, i == tree.tasks.count() - 1)
        }
        return s
    }

    override fun setUpClicks() {
//        TODO("Not yet implemented")
    }
}