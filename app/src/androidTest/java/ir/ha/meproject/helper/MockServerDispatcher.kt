package ir.ha.meproject.helper

import android.util.Log
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockWebServerDispatcher {

    private val TAG = this::class.java.simpleName

    internal inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            Log.i(TAG, "RequestDispatcher - dispatch: ${request.path} ")
            return when (request.path) {
                "/5be5839a-d088-483f-9270-33df02550b0c" ->
                    MockResponse().setResponseCode(200)
                        .setBody(FileReader.readStringFromFile("result.json"))
                else -> MockResponse().setResponseCode(520)
            }
        }
    }

    internal inner class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            Log.i(TAG, "ErrorDispatcher dispatch: ${request.path} ")
            return when (request.path) {
                "/5be5839a-d088-483f-9270-33df02550b0c" ->
                    MockResponse().setResponseCode(404)
                        .setBody("")
                else -> MockResponse().setResponseCode(400)
            }
        }
    }
}
