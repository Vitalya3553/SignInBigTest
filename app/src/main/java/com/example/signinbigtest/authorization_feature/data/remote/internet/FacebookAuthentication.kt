package com.example.signinbigtest.authorization_feature.data.remote.internet

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.signinbigtest.R
import com.example.signinbigtest.authorization_feature.presentation.MainActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class FacebookAuthentication(private val fragment: Fragment, private val context: Context) {

    private val TAG = "TAG"
    private lateinit var mGoogleSignInClient : GoogleSignInClient
    private  var auth: FirebaseAuth = FirebaseAuth.getInstance()

    val callbackManager = CallbackManager.Factory.create();



    fun signIn(){
        facebookRequest()

    }
    private fun facebookRequest(){
        LoginManager.getInstance().logInWithReadPermissions(fragment.requireActivity(), listOf("public_profile"))

        LoginManager.getInstance().registerCallback(callbackManager, object  : FacebookCallback<LoginResult>{

            override fun onSuccess(loginResult: LoginResult) {
                Log.e(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.e(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.e(TAG, "facebook:onError", error)
            }
        })

    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.e(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    (fragment.activity as MainActivity).navController.navigate(R.id.action_authorizationFragment_to_basicFragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(fragment.activity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }




    }





    /*init {

        createRequest()
    }

    private fun createRequest() {

        Log.e(TAG, "create request..")

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(fragment.requireContext().getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(fragment.requireContext(), gso)
        mGoogleSignInClient.signOut()
        Log.e(TAG, "create request complete..")

    }
    private var activityResultLauncher = fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        Log.e( TAG ,"requestCode == RC_SIGN_IN..")

        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {

            if(e.statusCode == 12501){
                Log.e(TAG,"cancelable error")
            }else{
                Log.e(TAG, "api ex.. ${e.message}")
                Toast.makeText(context, "Api error :( ${e.message}", Toast.LENGTH_SHORT).show()
            }
            // Google Sign In failed, update UI appropriately
        }

    }

    fun signIn() {

        Log.e(TAG, "sign in..")

        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        Log.e(TAG, "intent was created")
        activityResultLauncher.launch(signInIntent)
        //startActivityForResult(fragment.activity as MainActivity,signInIntent, RC_SIGN_IN,null)
        Log.e(TAG, "was goes to new activity")

    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.e(TAG, "firebase auth....")

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential).
        addOnCompleteListener { task ->
            Log.e("TAG", "OnCompleteListener")
            if (task.isSuccessful) {

                Log.e("TAG", " Task is Success")

                Log.e(TAG, "sign in..")
                // Sign in success, update UI with the signed-in user's information
                val user = mAuth.currentUser
                (fragment.activity as MainActivity).navController.navigate(R.id.action_authorizationFragment_to_basicFragment)

            } else {
                Log.e(TAG, "sign in is not complete..")
                Toast.makeText(context, "Sorry auth failed.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }*/
}




