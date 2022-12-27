package com.example.signinbigtest.authorization_feature.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.signinbigtest.R
import com.example.signinbigtest.authorization_feature.data.remote.internet.FacebookAuthentication
import com.example.signinbigtest.authorization_feature.data.remote.internet.GithubAuthentication
import com.example.signinbigtest.authorization_feature.data.remote.internet.GoogleAuthentication


class AuthServicesFragment : Fragment() {

    private lateinit var googleImageButton : ImageView
    private lateinit var facebookImageButton : ImageView
    private lateinit var gitHubImageButton : ImageView

    private lateinit var googleAuthorizationHandler : GoogleAuthentication
    private lateinit var facebookAuthorizationHandler : FacebookAuthentication
    private lateinit var gitHubAuthorizationHandler : GithubAuthentication

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth_services, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        googleImageButton = view.findViewById(R.id.authorization_google_icon)
        facebookImageButton = view.findViewById(R.id.authorization_facebook_icon)
        gitHubImageButton = view.findViewById(R.id.authorization_gitHub_icon)

        googleAuthorizationHandler = context?.let { GoogleAuthentication(this, it) }!!
        facebookAuthorizationHandler = context?.let { FacebookAuthentication(this, it) }!!
        gitHubAuthorizationHandler = context?.let { GithubAuthentication(this, it) }!!

        googleImageButton.setOnClickListener() {
            googleAuthorizationHandler.signIn()
        }

        facebookImageButton.setOnClickListener() {
            facebookAuthorizationHandler.signIn()
        }

        gitHubImageButton.setOnClickListener() {

            gitHubAuthorizationHandler.signIn()
        }

    }


}