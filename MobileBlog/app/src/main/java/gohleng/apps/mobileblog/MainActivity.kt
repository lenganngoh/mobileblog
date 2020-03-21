package gohleng.apps.mobileblog

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gohleng.apps.mobileblog.db.DatabaseManager
import gohleng.apps.mobileblog.login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DatabaseManager.initDatabase(this)

        setContentView(R.layout.activity_main)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
