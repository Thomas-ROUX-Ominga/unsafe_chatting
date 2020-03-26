package fr.ominga.mayday_firebase;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> implements Filterable {

    private List<Messages> mMessageList;
    //private List<Messages> mMessageListFull;

    private FirebaseAuth mAuth;

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView messageText;

        public MessageViewHolder(View view){
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text_layout);
        }
    }

    MessageAdapter(List<Messages> mMessageList){
        this.mMessageList = mMessageList;
        //this.mMessageListFull = new ArrayList<>(mMessageList);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout, parent, false);

        return new MessageViewHolder(v);
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

    @Override
    public Filter getFilter() {
        return messageFilter;
    }

    private Filter messageFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Messages> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mMessageList);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Messages message : mMessageList){
                    if (message.getMessage().toLowerCase().contains(filterPattern)){
                        filteredList.add(message);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mMessageList.clear();
            mMessageList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}