package fr.ominga.mayday_firebase;

import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfilFragment extends Fragment {

    private Toolbar mToolbar;

    private FirebaseAuth mAuth;

    private DatabaseReference mUsersDatabase;

    private TextView pseudo, email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        mToolbar = (Toolbar) view.findViewById(R.id.main_page_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).setTitle("PROFILE");
        setHasOptionsMenu(true);

        pseudo = (TextView) view.findViewById(R.id.pseudo);
        email = (TextView) view.findViewById(R.id.email);

        mAuth = FirebaseAuth.getInstance();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child((mAuth.getCurrentUser().getUid()));

        email.setText(mAuth.getCurrentUser().getEmail());
        pseudo.setText(UsersFragment.name);

        return view;
    }

    private void sendToStart() {
        Intent startIntent = new Intent((AppCompatActivity)getActivity(), StartActivity.class);
        startActivity(startIntent);
        getActivity().finish();
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
