package com.knobblochsapplication.app.modules.deletebranches.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.deletebranches.`data`.model.DeleteBranchesModel
import org.koin.core.KoinComponent

class DeleteBranchesVM : ViewModel(), KoinComponent {
  val deleteBranchesModel: MutableLiveData<DeleteBranchesModel> =
      MutableLiveData(DeleteBranchesModel())

  var navArguments: Bundle? = null
}
