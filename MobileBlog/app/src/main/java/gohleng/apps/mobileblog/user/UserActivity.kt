package gohleng.apps.mobileblog.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import gohleng.apps.mobileblog.R
import gohleng.apps.mobileblog.db.Post
import gohleng.apps.mobileblog.db.PostViewModel
import gohleng.apps.mobileblog.db.UserViewModel
import gohleng.apps.mobileblog.home.adapter.PostListAdapter
import gohleng.apps.mobileblog.home.listener.RecyclerViewListener
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserActivity : AppCompatActivity(), RecyclerViewListener {

    private var extraUID: String = "UID"
    private var extraSelectedUID: String = "SELECTED_UID"

    private lateinit var postViewModel: PostViewModel
    private lateinit var userViewModel: UserViewModel

    private val adapter = PostListAdapter(this)

    override fun onClick(position: Int) {
        /* do nothing */
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        initViewModel()
        initUI()
        initPostRecyclerView()
    }

    private fun getCurrentUID(): Long {
        return intent.getLongExtra(extraUID, -1L)
    }

    private fun getSelectedUID(): Long {
        return intent.getLongExtra(extraSelectedUID, -1L)
    }

    private fun initUI() {
        GlobalScope.launch {
            val user = userViewModel.findByUserId(getSelectedUID())
            if (user != null) {
                lblTitle.text = String.format(getString(R.string.str_posts_by), user.userName)
            }
        }
    }

    private fun initViewModel() {
        postViewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    private fun initPostRecyclerView() {
        rvPosts.layoutManager = LinearLayoutManager(this)
        rvPosts.adapter = adapter

        if (getSelectedUID() == getCurrentUID()) {
            postViewModel.getAllPostsByUser(getSelectedUID()).observe(this,
                Observer<List<Post>> { t ->
                    adapter.setPosts(t!!)
                })
        } else {
            postViewModel.getAllPublicPostsByUser(getSelectedUID()).observe(this,
                Observer<List<Post>> { t ->
                    adapter.setPosts(t!!)
                })
        }
    }
}