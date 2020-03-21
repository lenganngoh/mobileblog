package gohleng.apps.mobileblog.db

import android.os.AsyncTask

class UserRepository {

    private var userAccess: UserAccess

    init {
        val db: AppDatabase? = DatabaseManager.getDatabase()

        userAccess = db?.userAccess()!!
    }

    fun insert(user: User) {
        InsertPostAsyncTask(userAccess).execute(user)
    }

    fun findByUserName(username: String): User {
        return userAccess.findByUserName(username)
    }

    fun findByUserId(uid: Long): User {
        return userAccess.findByUserId(uid)
    }

    private class InsertPostAsyncTask(val userAccess: UserAccess) : AsyncTask<User, Unit, Unit>() {

        override fun doInBackground(vararg p0: User?) {
            userAccess.insert(p0[0]!!)
        }
    }
}