package com.sanicorporation.therealsocialnetwork.activities.main


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sanicorporation.therealsocialnetwork.R
import com.sanicorporation.therealsocialnetwork.models.Post
import kotlinx.android.synthetic.main.post_item.view.*

class PostAdapter(private var posts: ArrayList<Post>) :
    RecyclerView.Adapter<PostAdapter.MyViewHolder>() {


    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val title = view.post_title as TextView
        val message  = view.post_message as TextView
        val image = view.post_image as ImageView
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(layoutInflater.inflate(R.layout.post_item, parent, false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = posts[position].title
        holder.message.text = posts[position].message

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = posts.size

    fun setPosts(posts: ArrayList<Post>){
        this.posts = posts
        notifyDataSetChanged()
    }
}
