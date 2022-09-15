package com.bbj.scammerscanner.view

import android.telecom.Call
import android.telecom.CallScreeningService

class FilterCallScreeningService : CallScreeningService() {

    override fun onScreenCall(details: Call.Details) {
        val response = handleCall(getNumber(details))

        respondToCall(details,response)
    }

    private fun handleCall(number : String) : CallResponse{
        val resultResponse = CallResponse.Builder()

//        Looper.prepare()
//        Looper.loop()
//        Thread {Looper.prepare()
//            Handler(Looper.myLooper()!!).post {
//                Log.d("CALLSERVICE", "WORK")
//                Toast.makeText(applicationContext, "number = $number", Toast.LENGTH_LONG).show()
//                Looper.myLooper()!!.quit()
//            }
//            Looper.loop()
//        }.start()

        if (number == "1")
        resultResponse.apply {
            setDisallowCall(true)
            setRejectCall(true)
            setSkipCallLog(true)
            setSkipNotification(true)
        }

        return resultResponse.build()
    }

    private fun getNumber(details: Call.Details) : String{
        return details.handle.toString().split(":")[1]
    }
}