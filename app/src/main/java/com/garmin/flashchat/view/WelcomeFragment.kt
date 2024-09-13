package com.garmin.flashchat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.garmin.flashchat.R

class WelcomeFragment : Fragment() {

    private lateinit var registerButton: Button
    private lateinit var loginButton: Button
    private var listener: IMainActivity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = (activity as MainActivity)
        initViews(view)
    }

    private fun initViews(view: View) {
        registerButton = view.findViewById(R.id.register_button)
        registerButton.setOnClickListener {
            listener?.navigateToRegister()
        }
        loginButton = view.findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            listener?.navigateToLogin()
        }
    }
}