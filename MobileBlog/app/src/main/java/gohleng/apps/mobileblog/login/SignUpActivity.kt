package gohleng.apps.mobileblog.login

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import gohleng.apps.mobileblog.R
import gohleng.apps.mobileblog.db.DatabaseManager
import gohleng.apps.mobileblog.db.User
import gohleng.apps.mobileblog.db.UserViewModel
import gohleng.apps.mobileblog.widget.AppAlert
import kotlinx.android.synthetic.main.activity_login.etPass
import kotlinx.android.synthetic.main.activity_login.etUser
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initViewModel()

        btnSignUp.setOnClickListener {
            if (hasAllFields() && isPasswordCorrect()) {
                GlobalScope.launch {
                    val checkUser = userViewModel.findByUserName(etUser.text.toString())
                    if (checkUser != null) {
                        onUserNameAlreadyExists()
                        return@launch
                    }

                    val db = DatabaseManager.getDatabase()

                    val user = User(null, etUser.text.toString(), etPass.text.toString())
                    val uid = db?.userAccess()?.insert(user)

                    if (uid ?: -1 > 0) {
                        onSuccessfulSignUp(user)
                    }
                }
            } else {
                onUnsuccessfulSignUp()
            }
        }
    }

    private fun initViewModel() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    private fun hasAllFields(): Boolean {
        return etUser.text.isNotEmpty() &&
                etPass.text.isNotEmpty() &&
                etConfirmPass.text.isNotEmpty()
    }

    private fun isPasswordCorrect(): Boolean {
        return etPass.text.toString() == etConfirmPass.text.toString()
    }

    private fun onSuccessfulSignUp(user: User) {
        val listener = { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
            onBackPressed()
        }

        this@SignUpActivity.runOnUiThread {
            AppAlert.showAlertMessage(
                this, "Sign Up Successful!",
                user.userName + " has successfully signed up!",
                DialogInterface.OnClickListener(listener)
            )
        }
    }

    private fun onUnsuccessfulSignUp() {
        this@SignUpActivity.runOnUiThread {
            AppAlert.showAlertMessage(
                this, "Sign Up Failed!",
                "Please check all fields."
            )
        }
    }

    private fun onUserNameAlreadyExists() {
        this@SignUpActivity.runOnUiThread {
            AppAlert.showAlertMessage(
                this, "Sign Up Failed!",
                "Username already exists!"
            )
        }
    }
}