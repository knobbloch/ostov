package com.knobblochsapplication.app.appcomponents.utility

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Node(
    val uid: String,
    var name: String?,
    var deadline: String?,
    var priority: Int,
    var isDone: Boolean,
    var description: String?,
    var tasks: MutableList<Node>,
) : Parcelable {
    val left: MutableList<Node> = mutableListOf()
    val right: MutableList<Node> = mutableListOf()

    fun separete() {
        left.clear()
        right.clear()
        for ((index, item) in tasks.withIndex()) {
            if (index % 2 == 0) {
                left.add(item)
            } else {
                right.add(item)
            }
        }

    }

    fun getTaskByUid(uid: String): Node? {
        var goal = tasks.find {
            it.uid == uid
        }
        if (goal != null) {
            return goal
        }
        return null
    }
}