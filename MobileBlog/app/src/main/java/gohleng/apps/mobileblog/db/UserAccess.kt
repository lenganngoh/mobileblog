package gohleng.apps.mobileblog.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserAccess {
    @Query("SELECT * FROM user WHERE username LIKE :username LIMIT 1")
    fun findByUserName(username: String): User

    @Query("SELECT * FROM user WHERE uid = :uid LIMIT 1")
    fun findByUserId(uid: Long): User

    @Insert
    fun insert(user: User): Long
}