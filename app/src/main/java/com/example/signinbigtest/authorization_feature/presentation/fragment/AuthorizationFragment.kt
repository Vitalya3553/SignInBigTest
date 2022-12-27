package com.example.signinbigtest.authorization_feature.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.signinbigtest.R
import com.example.signinbigtest.authorization_feature.presentation.MainActivity
import com.example.signinbigtest.authorization_feature.presentation.handler.FieldsHandler
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class AuthorizationFragment : Fragment() {

    private val TAG = "authTag"

    private lateinit var emailField : TextInputEditText
    private lateinit var passwordField : TextInputEditText


    private lateinit var passwordInputLayout : TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout

    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private var user : FirebaseUser? = auth.currentUser


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_authorization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        emailField = view.findViewById(R.id.authorizationLoginTextField)
        passwordField = view.findViewById(R.id.authorizationPasswordTextField)

        emailInputLayout =
            view.findViewById<TextInputLayout>(R.id.authorizationEmailTextInputLayout)
        passwordInputLayout =
            view.findViewById<TextInputLayout>(R.id.authorizationPasswordTextInputLayout)


        val registrationAccountTextView: TextView =
            view.findViewById(R.id.registration_account_textView)
        val authorizationButton: Button = view.findViewById(R.id.authorization_button)


        // Initialize Facebook Login button
        val EMAIL = "email"


        registrationAccountTextView.setOnClickListener() {
            (activity as MainActivity).navController.navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }

        val fields = FieldsHandler(

            FieldsHandler.FieldProperties(
                emailField, emailInputLayout,
                "Invalid email"
            ) { Patterns.EMAIL_ADDRESS.matcher(emailField.text).matches() },

            FieldsHandler.FieldProperties(
                passwordField, passwordInputLayout,
                "Password must be at least 6 characters long"
            ) { passwordField.text.toString().length >= 6 },
        )

        authorizationButton.setOnClickListener {

            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (fields.isAllCorrect(true)) {
                CoroutineScope(Dispatchers.IO).launch {

                    try {

                        auth.signInWithEmailAndPassword(email, password).await()

                        if (auth.currentUser == null) {
                            Log.e(TAG, "user == null")
                        }

                        withContext(Dispatchers.Main) {
                            (activity as MainActivity).navController.navigate(R.id.action_authorizationFragment_to_basicFragment)
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Enter all fields", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        Log.e("A FR" , "On Start..")
        if(user != null){

            Log.e("A FR", " user != null")
            (activity as MainActivity).navController.navigate(R.id.action_authorizationFragment_to_basicFragment)
            Log.e("A FR", "transfer")

        }
    }
    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GoogleAuthorizationHandler.RC_SIGN_IN) {

        }else{
            Log.e(TAG,"requestCode != RC_SIGN_IN")
        }
    }
*/





}