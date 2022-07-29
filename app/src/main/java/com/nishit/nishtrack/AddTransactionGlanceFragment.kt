package com.nishit.nishtrack

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.simple_appbar.*

class AddTransactionGlanceFragment : Fragment(R.layout.simple_appbar) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        leftClickableRegion.setOnClickListener {
            requireActivity().finish()
        }
    }
}