package gohleng.apps.mobileblog.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: PostRepository = PostRepository()
    private var allPublicPosts: LiveData<List<Post>> = repository.getAllPublicPosts()

    fun insert(post: Post) {
        repository.insert(post)
    }

    fun getAllPublicPosts(): LiveData<List<Post>> {
        return allPublicPosts
    }

    fun getAllPublicPostsByUser(uid: Long): LiveData<List<Post>> {
        return repository.getAllPublicPostsByUser(uid)
    }

    fun getAllPostsByUser(uid: Long): LiveData<List<Post>> {
        return repository.getAllPostsByUser(uid)
    }

}