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
    var tasks: MutableList<Node>
) : Parcelable