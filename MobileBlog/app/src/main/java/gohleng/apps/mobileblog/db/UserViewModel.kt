package gohleng.apps.mobileblog.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: UserRepository = UserRepository()

    fun insert(user: User) {
        repository.insert(user)
    }

    fun findByUserName(username: String): User? {
        return repository.findByUserName(username)
    }

    fun findByUserId(uid: Long): User? {
        return repository.findByUserId(uid)
    }
}