package com.image.imagecloud;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class MainActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener {


   /* private StorageReference storageReference;

    private Uri filePath;
    private ImageView imageView;

    private FirebaseAuth mAuth;
*/

    private RecyclerView rvPictureList;
    private ImageAdapter adapter;

    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private List<Image> images;
    private ValueEventListener valueEventListener;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPictureList = (RecyclerView) findViewById(R.id.my_recycler_view);
        rvPictureList.setLayoutManager(new LinearLayoutManager(this));
        rvPictureList.setHasFixedSize(true);

        progressBar = (ProgressBar) findViewById(R.id.progress_circular);

        images = new ArrayList<>();
        adapter = new ImageAdapter(images, MainActivity.this);
        rvPictureList.setAdapter(adapter);
        adapter.setOnItemClickListener(MainActivity.this);

        firebaseStorage = FirebaseStorage.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("images");

        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                images.clear();

                for (DataSnapshot dataimage : dataSnapshot.getChildren()) {
                    Image image = dataimage.getValue(Image.class);
                    image.setKey(dataimage.getKey());
                    images.add(image);

                }

                adapter.notifyDataSetChanged();


                progressBar.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });


    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Normal " + position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onWhatEverClick(int position) {

        Toast.makeText(this, "What " + position, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onDeleteClick(int position) {

        Image selectedItem = images.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageref = firebaseStorage.getReferenceFromUrl(selectedItem.getUri());
        imageref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference.child(selectedKey).removeValue();
                Toast.makeText(MainActivity.this, "delete item " , Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        databaseReference.removeEventListener(valueEventListener);
    }
}


