package com.sanicorporation.therealsocialnetwork.activities.main


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.sanicorporation.therealsocialnetwork.R
import com.sanicorporation.therealsocialnetwork.models.Post
import kotlinx.android.synthetic.main.post_item.view.*

class PostAdapter(
    private var posts: ArrayList<Post>,
    val likedHandler: (likedId: Long, liked: Boolean) -> Unit,
    val selectedHandler: (view: View, post: Post) -> Unit,
    val getImageHandler: (imageUri: String, imageView: ImageView) -> Unit) :
    RecyclerView.Adapter<PostAdapter.MyViewHolder>() {


    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val title = view.post_title as TextView
        val message  = view.post_message as TextView
        val image = view.post_image as ImageView
        val like = view.post_like as ToggleButton
        val likeCount = view.like_counter as TextView
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(layoutInflater.inflate(R.layout.post_item, parent, false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = posts[position]
        holder.like.isChecked = post.isFavourite

        holder.like.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                post.likeCount += 1
                likedHandler(post.postId!!, true)
            } else {
                post.likeCount -= 1
                likedHandler(post.postId!!, false)
            }
            holder.likeCount.text = (post.likeCount).toString()

        }

        post.imageUrl?.let {
            getImageHandler(it, holder.image)
        }?:run {
            holder.image.visibility  = View.GONE
        }


        holder.itemView.setOnClickListener { selectedHandler(holder.itemView, post) }
        holder.title.text = post.title
        holder.message.text = post.description
        holder.likeCount.text = post.likeCount.toString()
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.like.setOnCheckedChangeListener(null)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = posts.size

    fun setPosts(posts: ArrayList<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }
}
