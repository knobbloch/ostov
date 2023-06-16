package com.knobblochsapplication.app.modules.menuone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseFragment
import com.knobblochsapplication.app.databinding.FragmentDiagramViewBinding

class DiagramViewFragment: BaseFragment<FragmentDiagramViewBinding>(R.layout.fragment_diagram_view) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setUpClicks() {
//        TODO("Not yet implemented")
    }
}