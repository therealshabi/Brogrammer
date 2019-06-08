package com.technolifestyle.therealshabi.brogrammar

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technolifestyle.therealshabi.brogrammar.dataSynchronization.LocalDataSync
import com.technolifestyle.therealshabi.brogrammar.models.MessageModel
import com.technolifestyle.therealshabi.brogrammar.requestUtils.Requests
import com.technolifestyle.therealshabi.brogrammar.sharedPreferenceUtils.SharedPreferenceStorage
import java.util.*

class SlackActivity : AppCompatActivity() {

    private lateinit var mToolbar: Toolbar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mToolbarTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slack)

        mToolbar = findViewById(R.id.activity_slack_toolbar)
        mRecyclerView = findViewById(R.id.activity_slack_recycler_view)
        mToolbarTextView = findViewById(R.id.activity_slack_toolbar_text)

        setSupportActionBar(mToolbar)

        mToolbarTextView.text = SharedPreferenceStorage.getTeamName(baseContext)?.toUpperCase()

        val database = LocalDataSync(baseContext)
        updateUsersList()

        mRecyclerView.layoutManager = LinearLayoutManager(baseContext)
        val messages = database.messagesList
        val adapter = RecyclerViewAdapter(applicationContext, messages)
        mRecyclerView.adapter = adapter

    }

    private fun updateUsersList() {
        val req = Requests()
        req.getUsersList(baseContext)
        req.getChannelMessages(baseContext)
    }

    inner class RecyclerViewAdapter internal constructor(
            private var context: Context,
            private var messages: ArrayList<MessageModel>) :
            RecyclerView.Adapter<RecyclerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            val view = LayoutInflater.from(context).inflate(
                    resources.getLayout(R.layout.slack_card_item), parent, false)
            return RecyclerViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.mMessageTextView.text = messages[position].message
            val database = LocalDataSync(context)
            val (name) = database.getUserData(messages[position].userId)
            holder.mUserNameTextView.text = name
        }

        override fun getItemCount(): Int {
            return messages.size
        }
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mMessageTextView: TextView = itemView.findViewById(R.id.slack_card_item_message_text)
        var mUserNameTextView: TextView = itemView.findViewById(R.id.slack_card_item_user_name)
    }
}
