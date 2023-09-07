package com.johan.salsasurvivor

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager

class MainActivity : AppCompatActivity() {

    private lateinit var game : Game

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity.java", "onCreate()")

        super.onCreate(savedInstanceState)

       /*val window : Window = window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )*/

        //hideSystemUI()

       /* @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }*/

        game = Game(this)
        setContentView(game)
    }

    override fun onStart() {
        Log.d("MainActivity.java", "onStart()")
        super.onStart()
        hideSystemUI()
    }

    override fun onResume() {
        Log.d("MainActivity.java", "onResume()")
        super.onResume()
        hideSystemUI()
    }


    override fun onPause() {
        Log.d("MainActivity.java", "onPause()")
        game.pause()
        super.onPause()
    }

    override fun onStop() {
        Log.d("MainActivity.java", "onStop()")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("MainActivity.java", "onDestroy()")
        super.onDestroy()
    }

    override fun onBackPressed() {
        Log.d("MainActivity.java", "onBackPressed()")
        //super.onBackPressed()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}