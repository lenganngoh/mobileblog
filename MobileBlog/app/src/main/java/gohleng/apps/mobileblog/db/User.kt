package gohleng.apps.mobileblog.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var uid: Long? = null,
    @ColumnInfo(name = "username") var userName: String?,
    @ColumnInfo(name = "password") var passWord: String?
)