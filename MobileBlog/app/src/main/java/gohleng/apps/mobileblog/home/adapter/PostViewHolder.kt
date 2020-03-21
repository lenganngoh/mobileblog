package gohleng.apps.mobileblog.home.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gohleng.apps.mobileblog.R
import gohleng.apps.mobileblog.db.DatabaseManager
import gohleng.apps.mobileblog.db.Post
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.viewholder_post, parent, false)) {

    private var txtName: TextView? = null
    private var txtMessage: TextView? = null
    private var imgAttachment: ImageView? = null
    private var txtTitle: TextView? = null

    init {
        txtName = itemView.findViewById(R.id.txtName)
        txtMessage = itemView.findViewById(R.id.txtMessage)
        imgAttachment = itemView.findViewById(R.id.imgAttachment)
        txtTitle = itemView.findViewById(R.id.txtTitle)
    }

    fun bind(post: Post) {
        fillUserName(post.uid ?: -1)
        txtTitle?.text = post.title
        txtMessage?.text = post.message
        if (post.photo != null) {
            imgAttachment?.setImageURI(Uri.parse(post.photo))
        }
    }

    private fun fillUserName(userId: Long) {
        GlobalScope.launch {
            val db = DatabaseManager.getDatabase()
            val user = db?.userAccess()?.findByUserId(userId)

            txtName?.text = user?.userName
        }
    }
}