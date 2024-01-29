package com.bitvolper.yogazzz.presentation.accountsetup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.bitvolper.yogazzz.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


class AccountSetupActivity : BaseActivity() {

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, AccountSetupActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatusBar()


    }
}