package chun.com.tw.goodeat.Dialog

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import chun.com.tw.goodeat.R

class BaseDialog {
    //跳出提示框
    fun showDialog(msg: String, context: Context?, permission: String) {
        val alertBuilder = AlertDialog.Builder(context!!)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle(R.string.username_title)
        alertBuilder.setMessage("$msg " + R.string.username_title)
        alertBuilder.setPositiveButton(android.R.string.yes) { dialog, which ->
            ActivityCompat.requestPermissions((context as Activity?)!!, arrayOf(permission), 123)
        }
        val alert = alertBuilder.create()
        alert.show()
    }
}