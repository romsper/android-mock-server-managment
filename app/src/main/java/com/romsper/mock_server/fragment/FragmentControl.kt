package com.romsper.mock_server.fragment


import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.romsper.mock_server.R
import com.romsper.mock_server.`interface`.IResponseResult
import com.romsper.mock_server.`interface`.IServerInput
import com.romsper.mock_server.asyncTask.TaskGetControlResult


class FragmentControl : Fragment(), IResponseResult {
    lateinit var taskGetControlResult: TaskGetControlResult

    lateinit var serverHost: String
    lateinit var spinnerControl: Spinner
    lateinit var btnSendControl: Button
    lateinit var spinnerFormatControl: Spinner
    lateinit var spinnerTypeControl: Spinner
    lateinit var inputPathControl: TextInputEditText
    lateinit var spinnerMethodControl: Spinner
    lateinit var txtResponseBody: TextView

    lateinit var blockParamControl: LinearLayout
    lateinit var blockBody: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_control, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        spinnerControl = view.findViewById(R.id.spinner_control)
        btnSendControl = view.findViewById(R.id.btn_send_control)
        spinnerFormatControl = view.findViewById(R.id.spinner_format_control)
        spinnerTypeControl = view.findViewById(R.id.spinner_type_control)
        inputPathControl = view.findViewById(R.id.input_path_control)
        spinnerMethodControl = view.findViewById(R.id.spinner_method_control)
        txtResponseBody = view.findViewById(R.id.txt_response_body)

        blockParamControl = view.findViewById(R.id.block_param_control)
        blockBody = view.findViewById(R.id.block_body_control)
    }

    override fun onStart() {
        super.onStart()

        spinnerControl.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when(parent.selectedItem) {
                    "RETRIEVE" -> {
                        blockParamControl.visibility = View.VISIBLE
                        blockBody.visibility = View.VISIBLE
                    }
                    "CLEAR", "RESET", "STATUS", "STOP" -> {
                        blockParamControl.visibility = View.GONE
                        blockBody.visibility = View.GONE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(activity!!.applicationContext, "Nothing", Toast.LENGTH_SHORT).show()
            }
        }

        btnSendControl.setOnClickListener {
            serverHost = (activity as? IServerInput)?.getInputServer() ?: "Error"

            val params = TaskGetControlResult.Params(
                host = serverHost,
                control = spinnerControl.selectedItem.toString(),
                format = spinnerFormatControl.selectedItem.toString(),
                type = spinnerTypeControl.selectedItem.toString(),
                path = inputPathControl.text.toString(),
                method = spinnerMethodControl.selectedItem.toString()
            )

            Log.i("[TAG]", params.toString())

            taskGetControlResult = TaskGetControlResult(this)
            taskGetControlResult.execute(params)
        }
    }

    override fun onSuccess(code: Int, body: String) {
        super.onSuccess(code, body)

        txtResponseBody.text = body

            Toast.makeText(
            activity!!.applicationContext,
            "Success: $code",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onError(code: Int) {
        super.onError(code)

        Toast.makeText(
            activity!!.applicationContext,
            "Failure: $code",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onFailure(localizedMessage: String) {
        super.onFailure(localizedMessage)

        Toast.makeText(
            activity!!.applicationContext,
            "Error: $localizedMessage",
            Toast.LENGTH_SHORT
        ).show()
    }
}
