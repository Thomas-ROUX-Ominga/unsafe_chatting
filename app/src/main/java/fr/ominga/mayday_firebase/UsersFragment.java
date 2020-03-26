package fr.ominga.mayday_firebase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class UsersFragment extends Fragment {

    private FirebaseAuth mAuth;

    private Toolbar mToolbar;

    private RecyclerView mUsersList;

    private DatabaseReference mUsersDatabase;

    public static String name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) view.findViewById(R.id.main_page_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).setTitle("USERS");
        setHasOptionsMenu(true);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mUsersList = (RecyclerView) view.findViewById(R.id.users_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager((AppCompatActivity)getActivity()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            sendToStart();
        }

        startListening();
    }

    private void sendToStart() {
        Intent startIntent = new Intent((AppCompatActivity)getActivity(), StartActivity.class);
        startActivity(startIntent);
        getActivity().finish();
    }

    private void startListening() {

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("status");
        mUsersDatabase.setValue("true");

        Query query = FirebaseDatabase.getInstance().getReference().child("Users");

        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>().setQuery(query, Users.class).build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Users, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(UserViewHolder holder, int position, final Users model) {

                final String recipent_id = getRef(position).getKey();

                if (mAuth.getCurrentUser().getUid().equals(recipent_id)){
                    holder.setName(model.name, true);
                    name = model.name;
                }
                else{
                    holder.setName(model.name, false);
                }

                if (model.status == "true"){
                    holder.setStatus("Online",true);
                }
                else{
                    holder.setStatus("Offline",false);
                }

                if(!mAuth.getCurrentUser().getUid().equals(recipent_id)) {
                    holder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent chatIntent = new Intent((AppCompatActivity)getActivity(), ChatActivity.class);
                            chatIntent.putExtra("recipent_id", recipent_id);
                            chatIntent.putExtra("recipent_name", model.name);
                            startActivity(chatIntent);

                        }
                    });
                }
            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_single_layout, parent, false);
                return new UserViewHolder(view);
            }
        };
        mUsersList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setName(String name, boolean isYou) {
            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);
            if (isYou){
                userNameView.setTextColor(Color.parseColor("#009cc8"));
            }
        }

        public void setStatus(String status, boolean isUp) {
            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_status);
            userNameView.setText(status);
            if (isUp){
                userNameView.setTextColor(Color.parseColor("#CD0150"));
            }
            else {
                userNameView.setTextColor(Color.parseColor("#d2ccbb"));
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        menu.getItem(0).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout_but){
            mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("status");
            mUsersDatabase.setValue("false");

            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }

        return true;
    }
}



