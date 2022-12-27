package com.example.signinbigtest.authorization_feature.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.signinbigtest.R

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var navFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)

        /*receiver = InternetConnectionReceiver()
        IntentFilter(ConnectivityManager.****).also {
            registerReceiver(receiver,it)
        }*/

        navController = Navigation.findNavController(this,R.id.nav_host) /*Navigation.findNavController(this,R.id.nav_host)*/
        navFragment = supportFragmentManager.findFragmentById(R.id.nav_host)!!
    }
}