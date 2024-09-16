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
import com.garmin.flashchat.Constants
import com.garmin.flashchat.R
import com.garmin.flashchat.model.Message
import com.garmin.flashchat.view.adapter.MessageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatFragment : Fragment() {

    private var messagesRecyclerView: RecyclerView? = null
    private var messageEditText: EditText? = null
    private var sendMessageButton: Button? = null
    private var listener: IMainActivity? = null
    private val db = Firebase.firestore

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
        messagesRecyclerView = view.findViewById(R.id.messages_recycler_view)
        messagesRecyclerView?.layoutManager = LinearLayoutManager(activity)

        loadMessages()

        messageEditText = view.findViewById(R.id.message_edit_text)
        sendMessageButton = view.findViewById(R.id.send_message_button)
        sendMessageButton?.setOnClickListener {
            val body = messageEditText?.text.toString()
            val sender = Firebase.auth.currentUser?.email
            if (body.isNotEmpty() && !sender.isNullOrEmpty()) {
                val message = Message(sender, body)
                saveMessage(message)
            } else {
                Log.e(TAG, "Message should not be empty!")
            }
            messageEditText?.setText("")
        }
    }

    private fun updateRecyclerView(messages: List<Message>) {
        val adapter = MessageAdapter(activity, messages)
        messagesRecyclerView?.adapter = adapter
    }

    private fun saveMessage(message: Message) {
        db.collection(Constants.collectionName).add(message)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    private fun loadMessages() {
        db.collection(Constants.collectionName)
            .orderBy(Constants.dateField)
            .addSnapshotListener { value, error ->
                val messages = mutableListOf<Message>()
                error?.let { e ->
                    Log.e(TAG, "Error retrieving data from Firestore: $e")
                } ?: run {
                    value?.let { snapshotDocuments ->
                        snapshotDocuments.documents.forEach {
                            val data = it.data
                            val messageSender = data?.get(Constants.senderField) as? String
                            val messageBody = data?.get(Constants.bodyField) as? String
                            if (!messageSender.isNullOrEmpty() && !messageBody.isNullOrEmpty()) {
                                val isMeAvatar = messageSender == Firebase.auth.currentUser?.email
                                val newMessage = Message(messageSender, messageBody, isMeAvatar = isMeAvatar)
                                messages.add(newMessage)
                                updateRecyclerView(messages)
                            }
                        }
                    }
                }
            }
    }

    companion object {
        const val TAG = "ChatFragment"
    }
}