package com.romsper.mock_server.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.romsper.mock_server.R


class FragmentOne : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_fragment_one, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }
}
