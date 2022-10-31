package com.nishit.nishtrack

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.nishit.nishtrack.model.enums.DataType
import kotlinx.android.synthetic.main.simple_appbar.*

class AppBarGlanceFragment(val dataType: DataType) : Fragment(R.layout.simple_appbar) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topText = "Add ${dataType.name}"
        appBarText.text = topText

        leftClickableRegion.setOnClickListener {
            requireActivity().finish()
        }
    }
}