package gohleng.apps.mobileblog.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostAccess {
    @Query("SELECT * FROM post WHERE isPublic = 1")
    fun getAllPublicPosts() : LiveData<List<Post>>

    @Query("SELECT * FROM post WHERE userId = :userId")
    fun getAllPostsByUser(userId: Long) : LiveData<List<Post>>

    @Query("SELECT * FROM post WHERE userId = :userId AND isPublic = 1")
    fun getAllPublicPostsByUser(userId: Long) : LiveData<List<Post>>

    @Insert
    fun insert(post: Post): Long
}