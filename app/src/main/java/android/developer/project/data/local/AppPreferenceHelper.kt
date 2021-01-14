package android.developer.project.data.local

import android.content.Context
import android.content.SharedPreferences
import android.developer.project.data.model.ui.User
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import javax.inject.Inject

class AppPreferenceHelper @Inject constructor(val context: Context, private val gson: Gson) : PreferenceHelper {

    companion object {
        private const val SHARED_PREFERENCES = "SHARED_PREFERENCES_WAARBORG"

        private const val PREF_KEY_CURRENT_USER = "PREF_KEY_CURRENT_USER"
    }

    private val mPrefs: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    override fun getCurrentUser(): User? {
        val userString = mPrefs.getString(PREF_KEY_CURRENT_USER, null)
        if (userString != null) {
            try {
                return gson.fromJson(userString, User::class.java)
            } catch (e: JsonSyntaxException) {
                Log.e("AppPreferenceHelper: ", "Error getting user")
            }
        }
        return null
    }

    override fun setCurrentUser(user: User?) {
        user?.let {
            mPrefs.edit().putString(PREF_KEY_CURRENT_USER, gson.toJson(user)).apply()
        } ?: run {
            mPrefs.edit().remove(PREF_KEY_CURRENT_USER).apply()
        }
    }
}