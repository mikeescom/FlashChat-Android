package com.garmin.flashchat.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.garmin.flashchat.R
import com.garmin.flashchat.model.Message

class MessageAdapter(context: Context?, data: List<Message>?): RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    private var mData: List<Message>? = null
    private var mInflater: LayoutInflater? = null
    private var mClickListener: ItemClickListener? = null

    init {
        this.mInflater = LayoutInflater.from(context)
        this.mData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater?.inflate(R.layout.message_box_item, parent, false)
        return view?.let { ViewHolder(it) } ?: throw Exception("Unable to inflate view!")
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = mData!![position]
        holder.messageTextView.text = message.body
    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData!!.size
    }

    // stores and recycles views as they are scrolled off screen
    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var messageTextView: TextView = itemView.findViewById(R.id.message_text_view)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            //if (mClickListener != null) mClickListener.onItemClick(view, adapterPosition)
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): Message {
        return mData?.get(id)!!
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        this.mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}