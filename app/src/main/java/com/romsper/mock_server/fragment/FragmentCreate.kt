package com.romsper.mock_server.fragment


import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import com.romsper.mock_server.R
import com.romsper.mock_server.`interface`.IResponseResult
import com.romsper.mock_server.`interface`.IServerInput
import com.romsper.mock_server.asyncTask.TaskSendMock
import com.romsper.mock_server.pojo.CreateMock


class FragmentCreate : Fragment(), IResponseResult {
    lateinit var sendMockAsyncTask: TaskSendMock

    lateinit var serverHost: String
    lateinit var switchSecure: Switch
    lateinit var spinnerMethod: Spinner
    lateinit var inputPath: TextInputEditText
    lateinit var inputMockBody: TextInputEditText
    lateinit var btnSendMock: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        switchSecure = view.findViewById(R.id.switch_secure)
        spinnerMethod = view.findViewById(R.id.spinner_method)
        inputPath = view.findViewById(R.id.input_path)
        inputMockBody = view.findViewById(R.id.input_mock_body)
        btnSendMock = view.findViewById(R.id.btn_send_mock)
    }

    override fun onStart() {
        super.onStart()

        btnSendMock.setOnClickListener {
            serverHost = (activity as? IServerInput)?.getInputServer() ?: "Error"
            val mock = CreateMock().apply {
                httpRequest.secure = switchSecure.isChecked
                httpRequest.method = spinnerMethod.selectedItem.toString()
                httpRequest.path = inputPath.text.toString()
                httpResponse.body = inputMockBody.text.toString()
            }
            val params = TaskSendMock.Params(mock, serverHost)

            sendMockAsyncTask = TaskSendMock(this)
            sendMockAsyncTask.execute(params)
        }
    }

    override fun onPause() {
        super.onPause()
        sendMockAsyncTask.cancel(true)
    }

    override fun onSuccess(code: Int, body: String) {
        super.onSuccess(code, body)

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
