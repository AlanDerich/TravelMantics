package com.derich.travelmantics;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.DealViewHolder> {
    ArrayList<TravelDeal> mDeals;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    public DealAdapter(){
       // FirebaseUtils.openFbReference("traveldeals",this);
        mFirebaseDatabase = FirebaseUtils.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtils.mDatabaseReference;
        this.mDeals = FirebaseUtils.mDeals;
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded( DataSnapshot dataSnapshot,  String s) {
                TravelDeal td = dataSnapshot.getValue(TravelDeal.class);
                Log.d("Deal: " , td.getTitle());
                td.setId(dataSnapshot.getKey());
                mDeals.add(td);
                notifyItemInserted(mDeals.size()-1);
            }

            @Override
            public void onChildChanged( DataSnapshot dataSnapshot,  String s) {

            }

            @Override
            public void onChildRemoved( DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved( DataSnapshot dataSnapshot,  String s) {

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);

    }

    @Override
    public DealViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.rv_row,parent,false);

        return new DealViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( DealViewHolder holder, int position) {
        TravelDeal deal = mDeals.get(position);
        holder.bind(deal);
    }

    @Override
    public int getItemCount() {
        return mDeals.size();
    }

    public class DealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTitle,tvDescription,tvPrice;
        public DealViewHolder( View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            itemView.setOnClickListener(this);
        }
        public void bind(TravelDeal deal){
            tvTitle.setText(deal.getTitle());
            tvDescription.setText(deal.getDescription());
            tvPrice.setText(deal.getPrice());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d("Click",String.valueOf(position));
            TravelDeal selectedDeal = mDeals.get(position);
            Intent intent = new Intent(view.getContext(),DealActivity.class);
            intent.putExtra("Deal", selectedDeal);
            view.getContext().startActivity(intent);
        }
    }
}
