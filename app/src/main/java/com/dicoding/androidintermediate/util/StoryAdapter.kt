package com.dicoding.androidintermediate.util

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.androidintermediate.R
import com.dicoding.androidintermediate.databinding.ItemStoryBinding
import com.dicoding.androidintermediate.response.Story
import com.dicoding.androidintermediate.ui.detail.DetailActivity

class StoryAdapter :
    PagingDataAdapter<Story, StoryAdapter.ViewHolder>(DiffUtilCallback)
{
    private var listStory = ArrayList<Story>()

    class ViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            binding.apply {
                val date = itemView.context.getString(
                    R.string.created_at,
                    story.createdAt?.split("T")?.get(0) ?: ""
                )
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .into(ivStory)

                tvName.text = story.name
                tvDate.text = date

                cardView.setOnClickListener {

                    val intent = Intent(itemView.context, DetailActivity::class.java)

                    intent.putExtra(EXTRA_IMAGE, story.photoUrl)
                    intent.putExtra(EXTRA_DESCRIPTION, story.description)
                    intent.putExtra(STORY_NAME, story.name)
                    intent.putExtra(STORY_DATE, story.createdAt)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(ivStory, "photo"),
                            Pair(tvName, "name"),
                            Pair(tvDate, "date")
                        )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemStoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = listStory.size

    fun setData(newStoryList: List<Story>) {
        val diffUtil = DiffUtil(listStory, newStoryList)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        listStory = newStoryList as ArrayList<Story>
        diffResult.dispatchUpdatesTo(this)
    }

    companion object {
        val DiffUtilCallback = object : androidx.recyclerview.widget.DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean =
                oldItem == newItem
        }
    }
}