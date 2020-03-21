package gohleng.apps.mobileblog.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey(autoGenerate = true) var pid: Long? = null,
    @ColumnInfo(name = "userId") var uid: Long?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "message") var message: String?,
    @ColumnInfo(name = "photo") var photo: String?, //In Base64 Format
    @ColumnInfo(name = "isPublic") var isPublic: Boolean
)