package gohleng.apps.mobileblog.db

import android.os.AsyncTask
import androidx.lifecycle.LiveData

class PostRepository {

    private var postAccess: PostAccess

    private var allPublicPosts: LiveData<List<Post>>

    init {
        val db: AppDatabase? = DatabaseManager.getDatabase()

        postAccess = db?.postAccess()!!
        allPublicPosts = postAccess.getAllPublicPosts()
    }

    fun insert(post: Post) {
        InsertPostAsyncTask(postAccess).execute(post)
    }

    fun getAllPublicPosts(): LiveData<List<Post>> {
        return allPublicPosts
    }

    fun getAllPublicPostsByUser(uid: Long): LiveData<List<Post>> {
        return postAccess.getAllPublicPostsByUser(uid)
    }

    fun getAllPostsByUser(uid: Long): LiveData<List<Post>> {
        return postAccess.getAllPostsByUser(uid)
    }

    private class InsertPostAsyncTask(val postAccess: PostAccess) : AsyncTask<Post, Unit, Unit>() {

        override fun doInBackground(vararg p0: Post?) {
            postAccess.insert(p0[0]!!)
        }
    }
}