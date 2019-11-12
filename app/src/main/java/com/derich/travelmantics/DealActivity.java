package com.derich.travelmantics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DealActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    EditText txtTitle,txtPrice,txtDescription;
    TravelDeal deal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUtils.openFbReference("traveldeals",this);
        mFirebaseDatabase= FirebaseUtils.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtils.mDatabaseReference;
        txtTitle = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        Intent intent = getIntent();
        TravelDeal deal =(TravelDeal) intent.getSerializableExtra("Deal");
        if (deal==null)
        {
            deal = new TravelDeal();
        }
        this.deal = deal;
        txtTitle.setText(deal.getTitle());
        txtDescription.setText(deal.getDescription());
        txtPrice.setText(deal.getPrice());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_menu:
                if (txtTitle.getText().toString().isEmpty() || txtDescription.getText().toString().isEmpty() || txtPrice.getText().toString().isEmpty())
                {
                    Toast.makeText(this, "The fields cannot be null", Toast.LENGTH_SHORT).show();
                }
                else {
                saveDeal();
                Toast.makeText(this,"Deal Saved!",Toast.LENGTH_LONG).show();
                clean();
                backToList();
                }
                return true;
            case R.id.delete_menu:
            {
                deleteDeal();
                Toast.makeText(this, "Deal Deleted successfully!", Toast.LENGTH_SHORT).show();
                backToList();
            return true;
            }

                default:
                    return super.onOptionsItemSelected(item);


        }
    }

    private void clean() {
        txtDescription.setText("");
        txtPrice.setText("");
        txtTitle.setText("");
        txtTitle.requestFocus();
    }

    private void saveDeal() {
        deal.setTitle(txtTitle.getText().toString());
        deal.setPrice(txtPrice.getText().toString());
        deal.setDescription(txtDescription.getText().toString());
       if (deal.getId()== null){
           mDatabaseReference.push().setValue(deal);
       }
       else {
           mDatabaseReference.child(deal.getId()).setValue(deal);
       }
    }
    private void deleteDeal() {
        if (deal == null){
            Toast.makeText(this,"The deal you're trying to delete does not exist",Toast.LENGTH_LONG).show();
        }
        else {
            mDatabaseReference.child(deal.getId()).removeValue();
        }
    }
    private void backToList(){
        Intent intent = new Intent(this,ListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= new MenuInflater(this);
        menuInflater.inflate(R.menu.save_menu,menu);
        return true;
    }
}
