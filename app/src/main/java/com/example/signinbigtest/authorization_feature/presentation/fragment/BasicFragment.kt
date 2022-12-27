package com.example.signinbigtest.authorization_feature.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.signinbigtest.R
import com.example.signinbigtest.authorization_feature.presentation.MainActivity
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class BasicFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val logOutButton = view.findViewById<Button>(R.id.logOut_button)
        val name = view.findViewById<TextView>(R.id.basic_name_TextView)
        val email = view.findViewById<TextView>(R.id.basic_email_TextView)

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        email.text = user?.email.toString()
        name.text = user?.displayName

        logOutButton.setOnClickListener(){
            auth.signOut()
            (activity as MainActivity).navController.navigate(R.id.action_basicFragment_to_authorizationFragment)

        }

    }


}