package fr.ominga.mayday_firebase;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Messages> mMessageList;
    private FirebaseAuth mAuth;

    public MessageAdapter(List<Messages> mMessageList){
        this.mMessageList = mMessageList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout, parent, false);

        return new MessageViewHolder(v);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView messageText;

        public MessageViewHolder(View view){
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text_layout);
        }
    }

    @Override
    public void onBindViewHolder(MessageViewHolder viewHolder, int i){
        String current_user_id = mAuth.getInstance().getCurrentUser().getUid();
        Messages c = mMessageList.get(i);
        String from_user = c.getFrom();
        if(from_user.equals(current_user_id)){
            viewHolder.messageText.setBackgroundResource(R.drawable.message_text_background_bis);
            viewHolder.messageText.setTextColor(Color.BLACK);
        }
        else{
            viewHolder.messageText.setBackgroundResource(R.drawable.message_text_background);
            viewHolder.messageText.setTextColor(Color.WHITE);
        }
        viewHolder.messageText.setText(c.getMessage());
    }

    @Override
    public int getItemCount(){
        return mMessageList.size();
    }
}