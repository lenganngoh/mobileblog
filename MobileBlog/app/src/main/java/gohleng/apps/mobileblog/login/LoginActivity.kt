package gohleng.apps.mobileblog.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import gohleng.apps.mobileblog.R
import gohleng.apps.mobileblog.db.User
import gohleng.apps.mobileblog.db.UserViewModel
import gohleng.apps.mobileblog.home.HomeActivity
import gohleng.apps.mobileblog.widget.AppAlert
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private var extraUsername: String = "USERNAME"
    private var extraUID: String = "UID"

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViewModel()

        txtSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        btnLogin.setOnClickListener {
            GlobalScope.launch {
                val user = userViewModel.findByUserName(etUser.text.toString())
                login(user)
            }
        }
    }

    private fun initViewModel() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    private fun login(user: User?) {
        if (user?.userName == etUser.text.toString()) {
            if (user.passWord == etPass.text.toString()) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra(extraUsername, user.userName)
                intent.putExtra(extraUID, user.uid)
                startActivity(intent)
            } else {
                this@LoginActivity.runOnUiThread {
                    AppAlert.showAlertMessage(
                        this,
                        "Login failed!",
                        "Incorrect password!"
                    )
                }
            }
        } else {
            this@LoginActivity.runOnUiThread {
                AppAlert.showAlertMessage(
                    this,
                    "Login failed!",
                    "Username not recognized."
                )
            }
        }
    }
}