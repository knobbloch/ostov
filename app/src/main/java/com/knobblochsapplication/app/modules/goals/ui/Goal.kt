package com.knobblochsapplication.app.modules.goals.ui

import android.os.Parcel
import android.os.Parcelable


data class Goal (
    val goalName: String?,
    val goalDeadline: String?,
    val goalPriority: Int,
    val goalIsDone: Boolean,
    val goalDescription: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(goalName)
        parcel.writeString(goalDeadline)
        parcel.writeInt(goalPriority)
        parcel.writeByte(if (goalIsDone) 1 else 0)
        parcel.writeString(goalDescription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Goal> {
        override fun createFromParcel(parcel: Parcel): Goal {
            return Goal(parcel)
        }

        override fun newArray(size: Int): Array<Goal?> {
            return arrayOfNulls(size)
        }
    }

}
