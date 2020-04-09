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
    val likedHandler: (likedId: String, liked: Boolean) -> Unit,
    val selectedHandler: (post: Post) -> Unit,
    val verifyLikedPost: (postId: String, setLikeHandler: (liked: Boolean) -> Unit ) -> Unit,
    val getImageHandler: (imageUri: String, imageView: ImageView) -> Unit) :
    RecyclerView.Adapter<PostAdapter.MyViewHolder>() {


    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val title = view.post_title as TextView
        val message  = view.post_message as TextView
        val image = view.post_image as ImageView
        val like = view.post_like as ToggleButton
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(layoutInflater.inflate(R.layout.post_item, parent, false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = posts[position]
        val setLikeHandler: (isSelected: Boolean) -> Unit = {
            holder.like.isChecked = it
            holder.like.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    likedHandler(post.postId, true)
                } else {
                    likedHandler(post.postId, false)
                }
            }
        }
        verifyLikedPost(post.postId, setLikeHandler)
        post.imageUri?.also {
            getImageHandler(it, holder.image)
        }


        holder.itemView.setOnClickListener { selectedHandler(post) }
        holder.title.text = post.title
        holder.message.text = post.message




    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = posts.size

    fun setPosts(posts: ArrayList<Post>){
        this.posts = posts
        notifyDataSetChanged()
    }
}
