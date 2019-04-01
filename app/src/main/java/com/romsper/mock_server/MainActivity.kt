package com.romsper.mock_server

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.romsper.mock_server.`interface`.IServerInput
import com.romsper.mock_server.adapter.PagerAdapter
import com.romsper.mock_server.fragment.FragmentCreate
import com.romsper.mock_server.fragment.FragmentControl
import com.romsper.mock_server.helper.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IServerInput {
    lateinit var input_server: TextInputEditText

    override fun getInputServer(): String {
        return input_server.text.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentCreate(), "MOCK")
        adapter.addFragment(FragmentControl(), "CONTROL")
        view_pager.adapter = adapter
        tab_list.setupWithViewPager(view_pager)

        input_server = findViewById(R.id.input_server)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val view = currentFocus
        if (view is TextInputEditText) {

            view.let {
                val screenCoordinates = IntArray(2)
                it.getLocationOnScreen(screenCoordinates)

                val x = event.rawX.plus(it.left).minus(screenCoordinates[0])
                val y = event.rawY.plus(it.top).minus(screenCoordinates[1])

                if (event.action == MotionEvent.ACTION_UP
                    && (x < it.left || x >= it.right || y < it.top || y > it.bottom)) {
                    hideKeyboard()
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}