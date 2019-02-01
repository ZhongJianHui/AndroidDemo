package com.mainli.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.mainli.dialog.AppDialogFragment

/**
 * Created by mobimagic on 2018/3/2.
 */
class TestPostCallMethodActivity : AppCompatActivity() {
    companion object {
        val TAG = "TestPostCallMethod"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = View(this)
        view.setBackgroundColor(0xffc7edcc.toInt())
        setContentView(view)
        Log.e(TAG, "onCreate: ");
        AppDialogFragment.Build(this).message(TAG)
                .addButton("ok") { finish() }
                .addButton("cancel")
                .activitySafetyShow(TAG)
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume: ");
    }

    override fun onPostResume() {
        super.onPostResume()
        Log.e(TAG, "onPostResume: ");
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.e(TAG, "onPostCreate: ");
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause: $isFinishing");
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "onStop: $isFinishing");
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart: ");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: ");
    }
}