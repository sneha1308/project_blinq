package com.example.broccoli.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.broccoli.R
import com.example.broccoli.app.PreferenceManager
import com.example.broccoli.utils.Utility
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        "${resources.getString(R.string.text_hey_mate)}${" "}${Utility.getGreetingsMsg()}".also { tvGreetings.text = it }
        checkUserRegistration()
       tvInvite.setOnClickListener(this)
        tvCancelInvitation.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tvInvite ->
                startActivity(Intent(applicationContext, UserDetailsToInviteActivity::class.java))

            R.id.tvCancelInvitation -> {
                dialogCancelInvitation()
            }
        }
    }

    private fun checkUserRegistration() {
        if (PreferenceManager.getInstance(applicationContext).isRegisteredForInvitation == true) {
            clGetInvitation.visibility = View.GONE
            clSuccessfullyRegisteredLayout.visibility = View.VISIBLE
            tvUserName.text = resources.getString(R.string.text_congrats_msg, PreferenceManager.getInstance(applicationContext).userName)
        } else {
            clGetInvitation.visibility = View.VISIBLE
            clSuccessfullyRegisteredLayout.visibility = View.GONE
        }

    }

    private fun dialogCancelInvitation() {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle(R.string.dialog_title_cancel_invitation)
            setMessage(R.string.text_cancel_invitation_confirmation)
            builder.setPositiveButton(resources.getString(R.string.dialog_positive_button)) { dialogInterface, i ->
                Toast.makeText(
                    applicationContext,
                    R.string.text_successfully_cancelled, Toast.LENGTH_SHORT
                ).show()
                updateData()
                PreferenceManager.getInstance(applicationContext).setIsUserInvitationCancelled(true)
                PreferenceManager.getInstance(applicationContext)
                    .setIsUserRegisteredForInvitation(false)
                PreferenceManager.getInstance(applicationContext).setUserId("")
            }
            builder.setNegativeButton(resources.getString(R.string.dialog_negative_button)){ dialogInterface, i ->
                dialogInterface.dismiss()
            }
            show()
        }
    }

    private fun updateData() {
        clSuccessfullyRegisteredLayout.visibility = View.GONE
        clGetInvitation.visibility = View.VISIBLE
    }
}