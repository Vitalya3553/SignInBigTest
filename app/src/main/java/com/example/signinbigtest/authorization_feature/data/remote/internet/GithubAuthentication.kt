package com.example.signinbigtest.authorization_feature.data.remote.internet

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.signinbigtest.R
import com.example.signinbigtest.authorization_feature.presentation.MainActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider


class GithubAuthentication(private val fragment: Fragment, private val context: Context) {
    val auth = FirebaseAuth.getInstance()
    var provider = OAuthProvider.newBuilder("github.com")


    fun signIn(){

        val provider = OAuthProvider.newBuilder("github.com")

        val scopes: ArrayList<String?> = object : ArrayList<String?>() {
            init {
                add("user:email")
            }
        }
        provider.scopes = scopes


        val pendingResultTask: Task<AuthResult>? = auth.pendingAuthResult
        if (pendingResultTask != null) {
            Log.e("TAG","pendingResultTask != null")
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                .addOnSuccessListener(
                    OnSuccessListener<AuthResult?> {
                        Log.e("TAG","pendingResultTask is success")
                        // User is signed in.
                        // IdP data available in
                        // authResult.getAdditionalUserInfo().getProfile().
                        // The OAuth access token can also be retrieved:
                        // ((OAuthCredential)authResult.getCredential()).getAccessToken().
                    })
                .addOnFailureListener(
                    OnFailureListener {
                        Log.e("TAG","pendingResultTask is not success")

                        // Handle failure.
                    })
        } else {
            Log.e("TAG","pendingResultTask == null")

            // There's no pending result so you need to start the sign-in flow.
            // See below.
            auth
                .startActivityForSignInWithProvider( /* activity= */fragment.requireActivity(), provider.build())
                .addOnSuccessListener(
                    OnSuccessListener<AuthResult?> {
                        Log.e("TAG","startActivityForSignInWithProvider is success")

                        auth.signInWithCredential(it.credential!!).addOnCompleteListener {
                            Log.e("TAG","go to basic fr")
                            (fragment.activity as MainActivity).navController.navigate(R.id.action_authorizationFragment_to_basicFragment)
                        }
                    })
                .addOnFailureListener(
                    OnFailureListener {
                        Log.e("TAG","startActivityForSignInWithProvider is not success")
                        // Handle failure.
                    })
        }

    }


    /*private fun toDO1(){
        val reqScopes = listOf<String>("user:email")
        provider.scopes = reqScopes
        Log.e("TAG","Todo 1 was worked")
    }

    private fun toDo2(){
        Log.e("TAG","Todo 2 ...")

        val pendingResultTask = auth.pendingAuthResult;
        if (pendingResultTask != null) {
            Log.e("TAG","pendingResultTask != null")

            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                .addOnSuccessListener(OnSuccessListener<AuthResult> {
                    Log.e("TAG","pendingResultTask is complete")
                    // User is signed in.
                    // IdP data available in
                    // authResult.getAdditionalUserInfo().getProfile().
                    // The OAuth access token can also be retrieved:
                    // ((OAuthCredential)authResult.getCredential()).getAccessToken().
                })
                .addOnFailureListener(OnFailureListener{
                    Log.e("TAG","pendingResultTask is failure")

                    // Handle failure.
                })
//
        } else {
            Log.e("TAG","pendingResultTask == null -_-")

            // There's no pending result so you need to start the sign-in flow.
            // See below.
         }
    }
    private fun showSignIn(){
        Log.e("TAG","showSignIn ...")

        auth
            .startActivityForSignInWithProvider( *//* activity= *//*fragment.requireActivity(), provider.build())
            .addOnSuccessListener(
                OnSuccessListener<AuthResult?> {
                    Log.e("TAG","success start activity")

                    it.credential?.let { it1 ->
                        Log.e("TAG","credential != null , signInWithCredential...")

                        auth.signInWithCredential(it1).addOnCompleteListener {
                            (fragment.activity as MainActivity).navController.navigate(R.id.action_authorizationFragment_to_basicFragment)
                        }
                    }

                    // User is signed in.
                    // IdP data available in
                    // authResult.getAdditionalUserInfo().getProfile().
                    // The OAuth access token can also be retrieved:
                    // ((OAuthCredential)authResult.getCredential()).getAccessToken().
                })
            .addOnFailureListener(
                OnFailureListener {
                    // Handle failure.
                    Log.e("TAG","failure start activity")
                })
    }


*/

}