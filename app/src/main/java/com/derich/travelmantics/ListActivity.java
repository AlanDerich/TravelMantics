package com.derich.travelmantics;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

public class ListActivity extends AppCompatActivity {
    RecyclerView rvDeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        rvDeals = findViewById(R.id.tvDeals);
        final DealAdapter adapter = new DealAdapter();
        rvDeals.setAdapter(adapter);
        rvDeals.setHasFixedSize(true);
        rvDeals.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.list_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert_menu:
                Intent intent = new Intent(ListActivity.this, DealActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtils.attachListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtils.detachListener();
    }
}
