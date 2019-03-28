package com.romsper.mock_server

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import com.romsper.mock_server.`interface`.IServerInput
import com.romsper.mock_server.adapter.PagerAdapter
import com.romsper.mock_server.fragment.FragmentCreate
import com.romsper.mock_server.fragment.FragmentTool
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
        adapter.addFragment(FragmentTool(), "TOOLS")
        view_pager.adapter = adapter
        tab_list.setupWithViewPager(view_pager)

        input_server = findViewById(R.id.input_server)
    }
}



















//    fun wtf() {
//        when(util_spinner.selectedItem.toString()) {
//            "VERIFY" -> {}
//            "RETRIEVE" -> {
//                param_block.visibility = View.VISIBLE
//                body_block.visibility = View.VISIBLE
//            }
//        }
//    }
//
//    fun onClickUtil(view: View) {
//        val server_host = input_server.text.toString()
//        clickRetrieve(server_host)
//    }
//
//    fun clickRetrieve(server_host: String) {
//        val retrieve = Retrofit.getInstance(server_host).create(MockServerApi::class.java).retrieve(
//            body = BodySend().apply {
//                method = method_spinner_util.selectedItem.toString()
//                path = path_editText.text.toString()
//            },
//            format = format_spinner.selectedItem.toString(),
//            type = type_spinner.selectedItem.toString())
//
//        retrieve.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                when (response.code()) {
//                    in 200..299 -> {
//                        json_textView_response.text = response.body().toString()
//                        Toast.makeText(applicationContext, "Success: ${response.code()}", Toast.LENGTH_SHORT).show()
//                    }
//                    in 400..499 -> Toast.makeText(applicationContext, "Failure: ${response.code()}", Toast.LENGTH_SHORT).show()
//                }
//            }
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Toast.makeText(applicationContext, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
