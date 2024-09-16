package com.garmin.flashchat.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.garmin.flashchat.R
import com.garmin.flashchat.model.Message

class MessageAdapter(context: Context?, data: List<Message>?): RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    private var mContext: Context? = null
    private var mData: List<Message>? = null
    private var mInflater: LayoutInflater? = null

    init {
        mContext = context
        this.mInflater = LayoutInflater.from(context)
        this.mData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater?.inflate(R.layout.message_box_item, parent, false)
        return view?.let { ViewHolder(it) } ?: throw Exception("Unable to inflate view!")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = mData!![position]
        if(message.isMeAvatar) {
            holder.youImageView.visibility = View.GONE
            holder.avatarImageView.visibility = View.VISIBLE
            mContext?.resources?.getColor(R.color.light_purple)
                ?.let { holder.messageTextView.setBackgroundColor(it) }
            mContext?.resources?.getColor(R.color.purple_200)
                ?.let { holder.messageTextView.setTextColor(it) }
        } else {
            holder.youImageView.visibility = View.VISIBLE
            holder.avatarImageView.visibility = View.GONE
            mContext?.resources?.getColor(R.color.purple_200)
                ?.let { holder.messageTextView.setBackgroundColor(it) }
            mContext?.resources?.getColor(R.color.light_purple)
                ?.let { holder.messageTextView.setTextColor(it) }
        }

        holder.messageTextView.text = message.body
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var youImageView: ImageView = itemView.findViewById(R.id.you_image_view)
        var messageTextView: TextView = itemView.findViewById(R.id.message_text_view)
        var avatarImageView: ImageView = itemView.findViewById(R.id.avatar_image_view)
    }
}