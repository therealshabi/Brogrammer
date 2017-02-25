package com.technolifestyle.therealshabi.brogrammar;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.technolifestyle.therealshabi.brogrammar.DataSynchronization.LocalDataSync;
import com.technolifestyle.therealshabi.brogrammar.Models.MessageModel;
import com.technolifestyle.therealshabi.brogrammar.Models.UserModel;
import com.technolifestyle.therealshabi.brogrammar.RequestUtils.Requests;
import com.technolifestyle.therealshabi.brogrammar.SharedPreferenceUtils.SharedPreferenceStorage;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SlackActivity extends AppCompatActivity {

    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    TextView mToolbarTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slack);

        mToolbar = (Toolbar) findViewById(R.id.activity_slack_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_slack_recycler_view);
        mToolbarTextView = (TextView) findViewById(R.id.activity_slack_toolbar_text);

        setSupportActionBar(mToolbar);
        mToolbarTextView.setText(SharedPreferenceStorage.getTeamName(getBaseContext()).toUpperCase());

        LocalDataSync database = new LocalDataSync(getBaseContext());
        UpdateUsersList();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        ArrayList<MessageModel> messages = database.getMessagesList();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), messages);
        mRecyclerView.setAdapter(adapter);

    }

    private void UpdateUsersList() {
        Requests req = new Requests();
//        Toast.makeText(getBaseContext(), "Update Users", Toast.LENGTH_SHORT).show();
        req.getUsersList(getBaseContext());
        req.getChannelMessages(getBaseContext());
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

        Context context;
        ArrayList<MessageModel> messages = new ArrayList<>();

        public RecyclerViewAdapter(Context context, ArrayList<MessageModel> messages) {
            this.context = context;
            this.messages = messages;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(getResources().getLayout(R.layout.slack_card_item), parent, false);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            holder.mMessageTextView.setText(messages.get(position).getMessage());
            LocalDataSync database = new LocalDataSync(context);
            UserModel user = database.getUserData(messages.get(position).getUserId());
            holder.mUserNameTextView.setText(user.getName());
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView mMessageTextView;
        TextView mUserNameTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mUserNameTextView = (TextView) itemView.findViewById(R.id.slack_card_item_user_name);
            mMessageTextView = (TextView) itemView.findViewById(R.id.slack_card_item_message_text);
        }
    }
}
