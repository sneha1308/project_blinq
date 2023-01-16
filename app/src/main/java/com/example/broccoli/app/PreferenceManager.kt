package com.example.broccoli.app

import android.content.Context
import android.content.SharedPreferences


const val IS_USER_REGISTERED_FOR_INVITATION = "isUserRegisteredForInvitation"
const val IS_USER_INVITATION_CANCELLED = "isUserInvitationCancelled"
const val USER_NAME = "userName"
const val MY_PREFS_NAME = "pref_name"

class PreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(MY_PREFS_NAME,Context.MODE_PRIVATE)
    var isRegisteredForInvitation: Boolean? = null
    var isInvitationCancelled: Boolean? = null
    var userName: String? = null

    companion object {
        private var instance: PreferenceManager? = null
        fun getInstance(context: Context): PreferenceManager {
            if (instance == null) {
                instance = PreferenceManager(context)
            }
            return instance as PreferenceManager
        }
    }


    init {
        isRegisteredForInvitation = sharedPreferences.getBoolean(IS_USER_REGISTERED_FOR_INVITATION, false)
        isInvitationCancelled = sharedPreferences.getBoolean(IS_USER_INVITATION_CANCELLED, false)
        userName = sharedPreferences.getString(USER_NAME, null)
    }

    fun setIsUserRegisteredForInvitation(isUserRegisteredForInvitation: Boolean) {
        val prefs = sharedPreferences.edit()
        prefs.putBoolean(IS_USER_REGISTERED_FOR_INVITATION, isUserRegisteredForInvitation)
        prefs.apply()
        this.isRegisteredForInvitation = isUserRegisteredForInvitation
    }

    fun setIsUserInvitationCancelled(isUserInvitationCancelled: Boolean) {
        val prefs = sharedPreferences.edit()
        prefs.putBoolean(IS_USER_REGISTERED_FOR_INVITATION, isUserInvitationCancelled)
        prefs.apply()
        this.isInvitationCancelled = isUserInvitationCancelled
    }

    fun setUserId(userName: String) {
        val prefs = sharedPreferences.edit()
        prefs.putString(USER_NAME, userName)
        prefs.apply()
        this.userName = userName
    }
}