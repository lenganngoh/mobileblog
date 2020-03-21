package gohleng.apps.mobileblog.widget

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import androidx.core.content.ContextCompat
import gohleng.apps.mobileblog.R

class AppAlert {

    companion object {
        fun showAlertMessage(context: Context, title: String, message: String,
            listener: DialogInterface.OnClickListener) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setNeutralButton(context.getString(R.string.str_ok), listener)

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }

        fun showAlertMessage(context: Context, title: String, message: String) {
            val listener = { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }

            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setNeutralButton(context.getString(R.string.str_ok),
                DialogInterface.OnClickListener(listener))

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }
}