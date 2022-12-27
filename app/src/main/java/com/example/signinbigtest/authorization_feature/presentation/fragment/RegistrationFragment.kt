package com.example.signinbigtest.authorization_feature.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.signinbigtest.R
import com.example.signinbigtest.authorization_feature.presentation.MainActivity
import com.example.signinbigtest.authorization_feature.presentation.handler.FieldsHandler
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class RegistrationFragment : Fragment() {

    private val TAG = "regTag"

    private lateinit var nameField : TextInputEditText
    private lateinit var emailField : TextInputEditText
    private lateinit var passwordField : TextInputEditText

    private lateinit var nameInputLayout : TextInputLayout
    private lateinit var passwordInputLayout : TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.registration_fragent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val auth = FirebaseAuth.getInstance()

        nameField = view.findViewById(R.id.registrationNameTextField)
        emailField = view.findViewById(R.id.registrationEmailTextField)
        passwordField = view.findViewById(R.id.registrationPasswordTextField)

        nameInputLayout = view.findViewById<TextInputLayout>(R.id.registrationNameTextInputLayout)
        emailInputLayout = view.findViewById<TextInputLayout>(R.id.registrationEmailTextInputLayout)
        passwordInputLayout = view.findViewById<TextInputLayout>(R.id.registrationPasswordTextInputLayout)


        val authorizeAccountTextView: TextView = view.findViewById(R.id.authorize_account_textView)
        val registrationButton: Button = view.findViewById(R.id.logOut_button)


        authorizeAccountTextView.setOnClickListener(){
            (activity as MainActivity).navController.navigate(R.id.action_registrationFragment_to_authorizationFragment)
        }

        val fields = FieldsHandler(
            FieldsHandler.FieldProperties(nameField, nameInputLayout,
                "This field cannot be empty"){ nameField.text.toString().isNotEmpty() },

            FieldsHandler.FieldProperties(emailField, emailInputLayout,
                "Invalid email") { android.util.Patterns.EMAIL_ADDRESS.matcher(emailField.text).matches() },

            FieldsHandler.FieldProperties(passwordField, passwordInputLayout,
                "Password must be at least 6 characters long") { passwordField.text.toString().length >= 6 },
        )

        registrationButton.setOnClickListener{

            if (fields.isAllCorrect(true)){

                CoroutineScope(Dispatchers.IO).launch {

                    try {

                        auth.createUserWithEmailAndPassword(emailField.text.toString(), passwordField.text.toString()).await()

                        if(auth.currentUser == null){
                            Log.e(TAG,"user == null")
                        }

                        Log.e("S EX","before change")
                        auth.currentUser?.let { user ->
                            UserProfileChangeRequest.Builder()
                                .setDisplayName(nameField.text.toString())
                                .build()
                                .let { userProfile ->
                                    CoroutineScope(Dispatchers.IO).launch{
                                        user.updateProfile(userProfile).addOnCompleteListener(){
                                            Log.e("S EX","navigate")
                                            (activity as MainActivity).navController.navigate(R.id.action_registrationFragment_to_basicFragment)
                                        }
                                    }
                                }
                        }

                    }catch (e: Exception){
                        withContext(Dispatchers.Main){
                            Toast.makeText(context,"Error", Toast.LENGTH_LONG).show()
                        }
                    }
                }

            }else{
                Toast.makeText(context,"Enter all fields", Toast.LENGTH_LONG).show()
            }
        }

    }







}