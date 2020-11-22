package com.cocooncreations.topstories.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cocooncreations.topstories.R
import com.cocooncreations.topstories.data.model.ResultEntity
import com.cocooncreations.topstories.delegate.ItemClickDelegate
import com.cocooncreations.topstories.utils.DateUtil
import kotlinx.android.synthetic.main.item_top_stories_layout.view.*


class TopStoriesAdapter(var context: Context, var listener: ItemClickDelegate) :
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
        holder.bind(getItem(position), this.context, this.listener)
    }

    /*
    View Holder Class to load result data on view
    */
    class TopStoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(resultEntity: ResultEntity, context: Context, listener: ItemClickDelegate) {
            itemView.title.text = resultEntity.title
            itemView.publishDate.text = DateUtil.changeDateFormat(resultEntity.publishedDate)
            Glide.with(context)
                .load(resultEntity.multimedia?.get(0)?.url)
                .placeholder(R.drawable.ic_dashboard_black_24dp).
                    apply(
                        RequestOptions().override(
                            200,
                            200
                        )
                    ).into(itemView.articleImage)

            /*
            pass Entity object for detail screen
            */

            itemView.mainLayout.setOnClickListener {
                listener.onClick(resultEntity)
            }
        }
    }

    /*
    Using diffutil for efficient views update
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