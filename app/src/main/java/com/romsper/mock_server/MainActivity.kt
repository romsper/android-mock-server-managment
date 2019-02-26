package com.romsper.mock_server

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.SwitchCompat
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.romsper.mock_server.adapter.PagerAdapter
import com.romsper.mock_server.api.MockServerApi
import com.romsper.mock_server.fragment.FragmentOne
import com.romsper.mock_server.fragment.FragmentTwo
import com.romsper.mock_server.network.Retrofit
import com.romsper.mock_server.pojo.BodySend
import com.romsper.mock_server.pojo.CreateMock
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_fragment_one.*
import kotlinx.android.synthetic.main.fragment_fragment_two.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentOne(), "MOCK")
        adapter.addFragment(FragmentTwo(), "UTILS")
        view_pager.adapter = adapter
        tab_list.setupWithViewPager(view_pager)

        val server_input: TextInputEditText = findViewById(R.id.server_input)

        val secure_switch = findViewById<SwitchCompat>(R.id.secure_switch)
        val method_spinner = findViewById<AppCompatSpinner>(R.id.method_spinner)
        val path_input = findViewById<TextInputEditText>(R.id.path_input)
        val json_input_mock = findViewById<TextInputEditText>(R.id.json_input_mock)
        val submit_button = findViewById<Button>(R.id.send_button)

        val util_spinner = findViewById<AppCompatSpinner>(R.id.util_spinner)
        val util_button = findViewById<Button>(R.id.util_button)
        val param_block = findViewById<LinearLayout>(R.id.param_block)
        val format_spinner = findViewById<AppCompatSpinner>(R.id.format_spinner)
        val type_spinner = findViewById<AppCompatSpinner>(R.id.type_spinner)
        val body_block = findViewById<LinearLayout>(R.id.body_block)
        val path_editText = findViewById<TextInputEditText>(R.id.path_editText)
        val method_spinner_util = findViewById<AppCompatSpinner>(R.id.method_spinner_util)
        val json_textView_response = findViewById<TextView>(R.id.json_textView_response)

    }

    fun wtf() {
        when(util_spinner.selectedItem.toString()) {
            "VERIFY" -> {}
            "RETRIEVE" -> {
                param_block.visibility = View.VISIBLE
                body_block.visibility = View.VISIBLE
            }
        }
    }

    fun onClickMock(view: View) {
        val server_host = server_input.text.toString()
        val mock = Retrofit.getInstance(server_host).create(MockServerApi::class.java).createMock(
            CreateMock().apply {
                httpRequest.secure = secure_switch.isChecked
                httpRequest.method = method_spinner.selectedItem.toString()
                httpRequest.path = path_input.text.toString()
                httpResponse.body = json_input_mock.text.toString()
            })

        mock.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                when (response.code()) {
                    in 200..299 -> Toast.makeText(applicationContext, "Success: ${response.code()}", Toast.LENGTH_SHORT).show()
                    in 400..499 -> Toast.makeText(applicationContext, "Failure: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(applicationContext, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun onClickUtil(view: View) {
        val server_host = server_input.text.toString()
        clickRetrieve(server_host)
    }

    fun clickRetrieve(server_host: String) {
        val retrieve = Retrofit.getInstance(server_host).create(MockServerApi::class.java).retrieve(
            body = BodySend().apply {
                method = method_spinner_util.selectedItem.toString()
                path = path_editText.text.toString()
            },
            format = format_spinner.selectedItem.toString(),
            type = type_spinner.selectedItem.toString())

        retrieve.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                when (response.code()) {
                    in 200..299 -> {
                        json_textView_response.text = response.body().toString()
                        Toast.makeText(applicationContext, "Success: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                    in 400..499 -> Toast.makeText(applicationContext, "Failure: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(applicationContext, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
