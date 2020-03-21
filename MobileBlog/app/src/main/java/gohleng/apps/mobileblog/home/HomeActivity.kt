package gohleng.apps.mobileblog.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import gohleng.apps.mobileblog.R
import gohleng.apps.mobileblog.db.Post
import gohleng.apps.mobileblog.db.PostViewModel
import gohleng.apps.mobileblog.home.adapter.PostListAdapter
import gohleng.apps.mobileblog.home.listener.RecyclerViewListener
import gohleng.apps.mobileblog.user.UserActivity
import gohleng.apps.mobileblog.widget.AppAlert
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(), RecyclerViewListener {

    private var extraUsername: String = "USERNAME"
    private var extraUID: String = "UID"
    private var extraSelectedUID: String = "SELECTED_UID"

    private val permissionCode: Int = 1001
    private val imagePickCode: Int = 1000

    private lateinit var postViewModel: PostViewModel
    private var cachedImage: String? = null

    private val adapter = PostListAdapter(this)
    override fun onClick(position: Int) {
        onRecyclerViewItemClicked(position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initUI()
        initViewModel()
        initPostRecyclerView()
        initPosts()
    }

    private fun getCurrentUserName(): String {
        return intent.getStringExtra(extraUsername)
    }

    private fun getCurrentUID(): Long {
        return intent.getLongExtra(extraUID, -1L)
    }

    private fun initViewModel() {
        postViewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)
    }

    private fun initUI() {
        txtWelcome.text = String.format(getString(R.string.str_welcome), getCurrentUserName())

        btnPost.setOnClickListener {
            if (isPostValid()) {
                startPostingState()
                GlobalScope.launch {
                    val post = Post(
                        null,
                        getCurrentUID(),
                        etHomeTitle.text.toString(),
                        etHomePost.text.toString(),
                        cachedImage,
                        !cbPrivate.isChecked
                    )
                    postViewModel.insert(post)
                    cachedImage = null
                }
            }
        }

        btnAttach.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, permissionCode)
                } else {
                    pickImageFromGallery()
                }
            } else {
                pickImageFromGallery()
            }
        }
    }

    private fun initPosts() {
        this@HomeActivity.runOnUiThread {
            resetUI()
        }
    }

    private fun resetUI() {
        etHomePost.setText("")
        etHomeTitle.setText("")
    }

    private fun initPostRecyclerView() {
        rvPosts.layoutManager = LinearLayoutManager(this)
        rvPosts.adapter = adapter

        postViewModel.getAllPublicPosts().observe(this,
            Observer<List<Post>> { t ->
                adapter.setPosts(t!!)
                stopPostingState()
            })


    }

    private fun startPostingState() {
        btnAttach.isEnabled = false

        btnPost.isEnabled = false
        btnPost.text = getString(R.string.str_posting)

        etHomePost.isEnabled = false
        etHomeTitle.isEnabled = false

        cbPrivate.isEnabled = false
    }

    private fun stopPostingState() {
        if (etHomePost.text.toString().isNotEmpty()) {
            AppAlert.showAlertMessage(
                this, "Post successful!",
                "Your message is successfully posted!"
            )
        }

        btnAttach.isEnabled = true

        btnPost.isEnabled = true
        btnPost.text = getString(R.string.str_post)

        etHomePost.isEnabled = true
        etHomePost.text?.clear()

        etHomeTitle.isEnabled = true
        etHomeTitle.text?.clear()

        cbPrivate.isEnabled = true
    }

    private fun onRecyclerViewItemClicked(position: Int) {
        val intent = Intent(this, UserActivity::class.java)
        intent.putExtra(extraUID, getCurrentUID())
        intent.putExtra(extraSelectedUID, adapter.getUserId(position))
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            permissionCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == imagePickCode) {
            txtAttachment.text = data?.dataString
            cachedImage = data?.data!!.toString()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, imagePickCode)
    }

    private fun isPostValid(): Boolean {
        return etHomePost.text.toString().isNotEmpty() && etHomeTitle.text.toString().isNotEmpty()
    }
}