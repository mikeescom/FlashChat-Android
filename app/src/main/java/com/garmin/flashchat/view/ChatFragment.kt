package com.garmin.flashchat.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.garmin.flashchat.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChatFragment : Fragment() {

    private var listener: IMainActivity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = (activity as MainActivity)

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

    }

    companion object {
        const val TAG = "ChatFragment"
    }
}