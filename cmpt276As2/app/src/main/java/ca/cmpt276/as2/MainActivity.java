package ca.cmpt276.as2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import ca.cmpt276.as2.databinding.ActivityMainBinding;
import ca.cmpt276.as2.model.Game;
import ca.cmpt276.as2.model.GameManager;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameManager.getInstance().load(this);
        setTitle("Games Played");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        listAdapter = new ArrayAdapter<Game>(this, android.R.layout.simple_list_item_1, new ArrayList<>(GameManager.getInstance().getGameList()));
        binding.listView.setAdapter(listAdapter);
        binding.listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, GameInfoActivity.class);
            intent.putExtra(GameInfoActivity.PARAM_GAME_INDEX, position);
            startActivity(intent);
        });

        binding.fab.setOnClickListener(view -> startActivity(new Intent(this, GameInfoActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        // it is a strange way to use notifyDataSetChanged,
        //if i don`t use this code,
        //there will thrown an Exception :"The content of the adapter has changed but ListView did not receive a notification"
        listAdapter.clear();
        listAdapter.addAll(new ArrayList<>(GameManager.getInstance().getGameList()));
        listAdapter.notifyDataSetChanged();
    }
}