package com.bignerdranch.photogallery

import android.app.DownloadManager.Query
import android.content.Context
import androidx.preference.PreferenceManager

private const val PREF_SEARCH_QUERY = "serarchQuery"

class QueryReferences {

    object QueeryReference{
        fun getStoredQuery(context : Context) : String{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(PREF_SEARCH_QUERY,"")!!
        }

        fun setStoredQuery(context: Context, query: String){
            PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY,query)
                .apply()
        }
    }
}