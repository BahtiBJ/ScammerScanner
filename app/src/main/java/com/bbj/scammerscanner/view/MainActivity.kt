package com.bbj.scammerscanner.view

import android.Manifest
import android.app.NotificationManager
import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.CallLog
import android.provider.Telephony
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.bbj.scammerscanner.R
import com.bbj.scammerscanner.data.models.CallInfo
import com.bbj.scammerscanner.data.models.SMSModel
import pub.devrel.easypermissions.EasyPermissions


class MainActivity : AppCompatActivity(),EasyPermissions.PermissionCallbacks {

    val callPermission = Manifest.permission.READ_CALL_LOG
    val smsPermission = Manifest.permission.READ_SMS
    val phoneStatePermission = Manifest.permission.READ_PHONE_STATE
    val requestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.text)
        requestRole()

        notify(this,"FROM NAINACTIVITY")

        ActivityCompat.requestPermissions(this, arrayOf(phoneStatePermission), requestCode)

//        if (ContextCompat.checkSelfPermission(
//                this,
//                permission
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
//        } else {
//            val callArray = readCallLog()
//            if (callArray.isNotEmpty()) {
//                textView.setText(callArray.toString())
//            } else {
//                Toast.makeText(this, "Emptyyyyyyyyyy", Toast.LENGTH_LONG).show()
//            }
//        }

//        if (ContextCompat.checkSelfPermission(
//                this,
//                smsPermission
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(this, arrayOf(smsPermission), requestCode)
//        } else {
//            val smsArray = readSMS()
//            if (smsArray.isNotEmpty()) {
//                textView.setText(smsArray.toString())
//            } else {
//                Toast.makeText(this, "Emptyyyyyyyyyy", Toast.LENGTH_LONG).show()
//            }
//        }


    }

    fun notify(context: Context, text: String) {
        Log.d("RECIEWVERAAAA","Notify")

//        val manager = NotificationManagerCompat.from(context)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        manager.cancelAll()
        val builder = NotificationCompat.Builder(context, "0")
            .setSmallIcon(android.R.drawable.ic_dialog_email)
            .setContentTitle("Напоминание")
            .setTicker("TICKER")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentText(text)
        manager.notify(0, builder.build())
    }

    fun readCallLog(): ArrayList<CallInfo> {
        val numberConst = CallLog.Calls.NUMBER
        val durationConst = CallLog.Calls.DURATION

        val resultArray: ArrayList<CallInfo> = arrayListOf()
        val callCursor = contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            arrayOf(numberConst, durationConst),
            null,
            null,
            CallLog.Calls.DEFAULT_SORT_ORDER,
            null
        )
        val numberId = callCursor!!.getColumnIndex(numberConst)
        val durationId = callCursor.getColumnIndex(durationConst)

        var callCount = 0
        while (callCursor.moveToNext() && callCount < 20) {
            val duration = callCursor.getString(durationId)
            val number = callCursor.getString(numberId)
            resultArray.add(CallInfo(number, duration))
            callCount++
        }

        callCursor.close()
        return resultArray
    }

    fun readSMS(): ArrayList<SMSModel> {
        val addressCol = Telephony.Sms.ADDRESS
        val bodyConst = Telephony.Sms.BODY
        val typeConst = Telephony.Sms.TYPE

        val resultArray: ArrayList<SMSModel> = arrayListOf()
        val smsCursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(addressCol, bodyConst, typeConst),
            null,
            null,
            CallLog.Calls.DEFAULT_SORT_ORDER,
            null
        )
        val addressId = smsCursor!!.getColumnIndex(addressCol)
        val bodyId = smsCursor.getColumnIndex(bodyConst)
        val typeId = smsCursor.getColumnIndex(typeConst)

        var callCount = 0
        while (smsCursor.moveToNext() && callCount < 20) {
            val body = smsCursor.getString(bodyId)
            val address = smsCursor.getString(addressId)
            val type = smsCursor.getString(typeId)
            resultArray.add(SMSModel(address,body,type))
            callCount++
        }

        smsCursor.close()
        return resultArray
    }

    val REQUEST_ID = 1

    fun requestRole(){
        val roleManager = getSystemService(ROLE_SERVICE) as RoleManager
        val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
        startActivityForResult(intent, REQUEST_ID)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        Toast.makeText(this,"AAAAAAAAAAAAAAAAAAAAAA",Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        arrayOf(callPermission,smsPermission,phoneStatePermission)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }
}