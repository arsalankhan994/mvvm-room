package com.cocooncreations.topstories.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cocooncreations.topstories.R
import com.cocooncreations.topstories.data.model.ResultEntity

class TopStoriesAdapter(var context: Context) :
    ListAdapter<ResultEntity, TopStoriesAdapter.TopStoriesViewHolder>(TopStoriesDiffCallback()) {

    /*
    Create Holder Class
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopStoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TopStoriesViewHolder(
            inflater.inflate(
                R.layout.item_top_stories_layout,
                parent,
                false
            )
        )
    }

    /*
    Bind view on recyclerview
    */
    override fun onBindViewHolder(holder: TopStoriesViewHolder, position: Int) {
        holder.bind(getItem(position), this.context)
    }

    /*
    View Holder Class to load result data on view
    */
    class TopStoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(resultEntity: ResultEntity, context: Context) {
//            itemView.contactName.text = resultEntity.phoneNumber.plus(" - ").plus(resultEntity.id)
        }
    }

    /*
    Using diffutil for efficient data notify
    */
    class TopStoriesDiffCallback : DiffUtil.ItemCallback<ResultEntity>() {

        override fun areItemsTheSame(oldItem: ResultEntity, newItem: ResultEntity): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ResultEntity, newItem: ResultEntity): Boolean {
            return oldItem.equals(newItem)
        }
    }
}