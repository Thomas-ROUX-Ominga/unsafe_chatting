package fr.ominga.mayday_firebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class PokemonActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        toolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        toolbar.setTitle("POKEMON LIST");
        setSupportActionBar(toolbar);

    }
}
