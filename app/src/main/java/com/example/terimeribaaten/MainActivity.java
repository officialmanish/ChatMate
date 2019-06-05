package com.example.terimeribaaten;
import android.content.Intent;
import android.os.Bundle;

import com.example.terimeribaaten.PojoModel.RecyclerPojo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.Sampler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText mTextMessage;
    Button button2;
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    List<RecyclerPojo> list = new ArrayList<>();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText("");
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText("");
                    return true;
                case R.id.navigation_notifications:
                    Intent intent=new Intent(MainActivity.this,Profile.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button2 = findViewById(R.id.send);
        getSupportActionBar().hide();
        mTextMessage = findViewById(R.id.message);
        recyclerView=findViewById(R.id.recyclerView);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final MyAdapter adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg= mTextMessage.getText().toString();
                myRef= database.getReference( "message");
                RecyclerPojo message = new RecyclerPojo();
                Toast.makeText(MainActivity.this, "message send", Toast.LENGTH_SHORT).show();
                message.setMessage(msg);
                myRef.push().setValue(message);
                mTextMessage.setText("");

            }
        });
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("messages",dataSnapshot.getValue().toString());
                RecyclerPojo message = dataSnapshot.getValue(RecyclerPojo.class);
                list.add(message);
//                Toast.makeText(MainActivity.this,dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                recyclerView.getLayoutManager().scrollToPosition(list.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.message,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            RecyclerPojo message = list.get(position);
            holder.message.setText(message.getMessage());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView message;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                message = itemView.findViewById(R.id.message);
            }
        }
    }
}