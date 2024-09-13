package com.garmin.flashchat.view

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.garmin.flashchat.R
import com.garmin.flashchat.model.Message
import com.garmin.flashchat.view.adapter.MessageAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ChatFragment : Fragment() {

    private var messagesRecyclerView: RecyclerView? = null
    private var messageEditText: EditText? = null
    private var sendMessageButton: Button? = null
    private var listener: IMainActivity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = (activity as MainActivity)

        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.purple_200, null)))

        setupMenu(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    val menuId = R.menu.logout_menu
                    menuInflater.inflate(menuId, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.logout_menu_item -> {
                            Log.d(TAG,"onOptionsItemSelected: saving reminder")
                            Firebase.auth.signOut()
                            true
                        }
                        else -> {
                            Log.d(TAG, "onOptionsItemSelected: returning super ")
                            false
                        }
                    }
                }
            },
        )

        initViews(view)
    }

    private fun Fragment.setupMenu(menuProvider: MenuProvider) {
        (requireActivity() as MenuHost).addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initViews(view: View) {
        val messages = ArrayList<Message>()
        messages.add(Message("12@34.com", "Hi"))
        messages.add(Message("1@2.com", "Hola"))
        messages.add(Message("12@34.com", "Ciao"))
        messages.add(Message("1@2.com", "Hi"))
        messages.add(Message("12@34.com", "Hello"))

        messagesRecyclerView = view.findViewById(R.id.messages_recycler_view)
        messagesRecyclerView?.layoutManager = LinearLayoutManager(activity)
        var adapter = MessageAdapter(activity, messages)
        messagesRecyclerView?.adapter = adapter

        messageEditText = view.findViewById(R.id.message_edit_text)
        sendMessageButton = view.findViewById(R.id.send_message_button)
    }

    companion object {
        const val TAG = "ChatFragment"
    }
}