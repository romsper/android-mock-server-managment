package com.romsper.mock_server.fragment


import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson

import com.romsper.mock_server.R
import com.romsper.mock_server.`interface`.IResponseResult
import com.romsper.mock_server.`interface`.IServerInput
import com.romsper.mock_server.asyncTask.GetToolResultAsyncTask

class FragmentTool : Fragment(), IResponseResult {
    lateinit var getToolResultAsyncTask: GetToolResultAsyncTask

    lateinit var serverHost: String
    lateinit var spinnerTool: Spinner
    lateinit var btnSendTool: Button
    lateinit var spinnerFormatTool: Spinner
    lateinit var spinnerTypeTool: Spinner
    lateinit var inputPathTool: TextInputEditText
    lateinit var spinnerMethodTool: Spinner
    lateinit var txtResponseBody: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tool, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        spinnerTool = view.findViewById(R.id.spinner_tool)
        btnSendTool = view.findViewById(R.id.btn_send_tool)
        spinnerFormatTool = view.findViewById(R.id.spinner_format_tool)
        spinnerTypeTool = view.findViewById(R.id.spinner_type_tool)
        inputPathTool = view.findViewById(R.id.input_path_tool)
        spinnerMethodTool = view.findViewById(R.id.spinner_method_tool)
        txtResponseBody = view.findViewById(R.id.txt_response_body)
    }

    override fun onStart() {
        super.onStart()

        btnSendTool.setOnClickListener {
            serverHost = (activity as? IServerInput)?.getInputServer() ?: "Error"

            val params = GetToolResultAsyncTask.Params(
                host = serverHost,
                tool = spinnerTool.selectedItem.toString(),
                format = spinnerFormatTool.selectedItem.toString(),
                type = spinnerTypeTool.selectedItem.toString(),
                path = inputPathTool.text.toString(),
                method = spinnerMethodTool.selectedItem.toString()
            )

            Log.i("[TAG]", params.toString())

            getToolResultAsyncTask = GetToolResultAsyncTask(this)
            getToolResultAsyncTask.execute(params)



            txtResponseBody.text
        }
    }

    override fun onSuccess(code: Int) {
        super.onSuccess(code)

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
