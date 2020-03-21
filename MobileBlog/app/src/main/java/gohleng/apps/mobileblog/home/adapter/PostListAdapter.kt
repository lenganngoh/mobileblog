package gohleng.apps.mobileblog.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gohleng.apps.mobileblog.db.Post
import gohleng.apps.mobileblog.home.listener.RecyclerViewListener

class PostListAdapter(private val recyclerViewListener: RecyclerViewListener) : RecyclerView.Adapter<PostViewHolder>() {

    private var posts: List<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PostViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun getItemId(position: Int): Long {
        return posts[position].pid!!
    }

    fun getUserId(position: Int): Long {
        return posts[position].uid!!
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post: Post = posts[position]
        holder.itemView.setOnClickListener{
            recyclerViewListener.onClick(position)
        }
        holder.bind(post)
    }

    fun setPosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }
}