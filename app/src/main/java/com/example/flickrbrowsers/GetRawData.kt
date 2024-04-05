package com.example.flickrbrowsers

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.lang.AssertionError
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus {
    OK, IDLE, NOT_INITIALISED, FAILED_OR_EMPTY, PERMISSION_ERROR, ERROR
}

class GetRawData(private val listner:OnDownloadComplete) : AsyncTask<String, Void, String>() {

    private val TAG = "GetRawData"
    private var downloadStatus = DownloadStatus.IDLE

    interface OnDownloadComplete{
        fun onDownloadComplete(data:String, status: DownloadStatus)
    }



    override fun onPostExecute(result: String?) {
        Log.d(TAG, "onPostExecute called, parameter is $result")
        if (result != null) {
            listner.onDownloadComplete(result,downloadStatus)
        }
    }


    override fun doInBackground(vararg params: String?): String {

        if (params[0] == null) {
            downloadStatus = DownloadStatus.NOT_INITIALISED
            return "no URL Specified"
        }
        try {
            downloadStatus = DownloadStatus.OK
            return URL(params[0]).readText()
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is MalformedURLException -> {
                    downloadStatus = DownloadStatus.NOT_INITIALISED
                    "doInBackground : Invalid URL ${e.message}"
                }

                is IOException -> {
                    downloadStatus = DownloadStatus.FAILED_OR_EMPTY
                    "doInBackground : IO Exception reading data ${e.message}"

                }

                is SecurityException -> {
                    downloadStatus = DownloadStatus.PERMISSION_ERROR
                    "doInBackground : Security Exception : Need Permission ${e.message}"
                }else->{
                    downloadStatus = DownloadStatus.ERROR
                    "doInBackground : Unkown Error :${e.message}"
                }
            }
            Log.e(TAG, errorMessage)
            return errorMessage
        }



    }
}