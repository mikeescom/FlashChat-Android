package com.garmin.flashchat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.garmin.flashchat.R

class LoginFragment : Fragment() {

    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var registerButton: Button? = null
    private var listener: IMainActivity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = (activity as MainActivity)
        initViews(view)
    }

    private fun initViews(view: View) {
        emailEditText = view.findViewById(R.id.email_edit_text)
        emailEditText?.setText("23@34.com")
        passwordEditText = view.findViewById(R.id.password_edit_text)
        passwordEditText?.setText("123456")
        registerButton = view.findViewById(R.id.login_button)
        registerButton?.setOnClickListener {
            listener?.loginUser(emailEditText?.text.toString(), passwordEditText?.text.toString())
        }
    }
}