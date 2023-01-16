package com.example.broccoli.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.broccoli.R
import com.example.broccoli.app.ApiInterface
import com.example.broccoli.app.PreferenceManager
import com.example.broccoli.app.RetrofitClient
import com.example.broccoli.model.UserDetails
import com.example.broccoli.utils.Utility
import kotlinx.android.synthetic.main.activity_user_details_to_invite.*
import nl.dionsegijn.konfetti.xml.KonfettiView

class UserDetailsToInviteActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewConfetti: KonfettiView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details_to_invite)
        supportActionBar?.title = resources.getString(R.string.toolbar_title_user_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvSend.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tvSend -> {
                val userName: String = etUserId.text.toString()
                val emailId: String = etEmailId.text.toString()
                val confirmEmailId: String = etConfirmEmailId.text.toString()
                if (isValidUserDetails(userName, emailId, confirmEmailId)) {
                    sendUserDetailsToServer(userName, emailId)
                }
            }

        }
    }

    private fun isValidUserDetails(
        userName: String,
        emailId: String,
        confirmEmailId: String
    ): Boolean {
        if (isValidUserName(userName) && isValidEmailId(emailId, confirmEmailId) && validateEmailId(
                emailId,
                confirmEmailId
            )
        ) {
            return true
        }
        return false
    }

    private fun isValidUserName(userName: String): Boolean {
        if (TextUtils.isEmpty(userName) || userName.length < 3) {
            Toast.makeText(
                this@UserDetailsToInviteActivity,
                resources.getString(R.string.toast_msg_username),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    private fun isValidEmailId(emailId: String, confirmEmailId: String): Boolean {
        if (TextUtils.isEmpty(emailId) || !Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            Toast.makeText(
                this@UserDetailsToInviteActivity,
                resources.getString(R.string.toast_msg_valid_emailId),
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (TextUtils.isEmpty(confirmEmailId) || !Patterns.EMAIL_ADDRESS.matcher(
                confirmEmailId
            ).matches()
        ) {
            Toast.makeText(
                this@UserDetailsToInviteActivity,
                resources.getString(R.string.toast_msg_valid_confirmEmailId),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    private fun validateEmailId(emailId: String, confirmEmailId: String): Boolean {
        return if (emailId == confirmEmailId) {
            true
        } else {
            Toast.makeText(
                this@UserDetailsToInviteActivity,
                resources.getString(R.string.toast_msg_validate_emailId),
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    private fun sendUserDetailsToServer(userName: String, userEmailId: String) {
        val retrofit = RetrofitClient.getInstance()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        val userDate = UserDetails(userName, userEmailId)
        lifecycleScope.launchWhenCreated {
            try {
                val response = apiInterface.postUserDetails(userDate)
                if (response.isSuccessful) {
                    storeUserData(userName)
                    dialogCongratulations()
                } else {
                    Toast.makeText(
                        this@UserDetailsToInviteActivity,
                        resources.getString(R.string.something_went_wrong),
                        Toast.LENGTH_LONG
                    )
                        .show()

                }
            } catch (Ex: Exception) {
                Ex.localizedMessage?.let {
                    Toast.makeText(
                        this@UserDetailsToInviteActivity,
                        resources.getString(R.string.toast_internet_connection),
                        Toast.LENGTH_LONG
                    )
                    Log.e(resources.getString(R.string.log_error), it)
                }
            }
        }

    }

    private fun storeUserData(userName: String) {
        PreferenceManager.getInstance(applicationContext).setUserId(userName)
        PreferenceManager.getInstance(applicationContext).setIsUserRegisteredForInvitation(true)
    }

    private fun dialogCongratulations() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_congratulations_view, null)
        builder.setView(dialogLayout)
        builder.setCancelable(false)
        viewConfetti = dialogLayout.findViewById(R.id.confettiView)
        builder.also { explode() }
        dialogLayout.findViewById<TextView>(R.id.tvDismissDialog)
            .setOnClickListener(View.OnClickListener {
                startActivity(Intent(this@UserDetailsToInviteActivity, MainActivity::class.java))
            })
        builder.show()
    }

    private fun explode() {
        viewConfetti.start(Utility.rain())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}