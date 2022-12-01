package com.example.firestore3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button Save,Show,Update,Delete;
    private EditText Fullname,phoneno;
    private TextView tv1,tv2;

    public static final String KEY_NAME = "Full Name";
    public static final String KEY_CONTACT = "Contact no.";

    private DocumentReference Book = db.collection("Symbol").document("Con Book");
    private CollectionReference collectionReference = db.collection("Symbol");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Save = findViewById(R.id.Save_buttobn);
        Show = findViewById(R.id.Show_button);
        Update = findViewById(R.id.Update_button);
        Delete = findViewById(R.id.Delete_button);
        Fullname = findViewById(R.id.name);
        phoneno = findViewById(R.id.Contactno0);
        tv1 = findViewById(R.id.nameview);
//        tv2 = findViewById(R.id.Conatctview);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    addContact();
//                String name = Fullname.getText().toString().trim();
//                String num = phoneno.getText().toString().trim();
//                Contacts cn = new Contacts();
//                cn.setName(name);
//                cn.setPh_no(num);
//                Book.set(cn).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(MainActivity.this, "Added to Database", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String no = phoneno.getText().toString().trim();
               String name = Fullname.getText().toString().trim();

               Map<String,Object> data = new HashMap<>();
               data.put(KEY_NAME,name);
               data.put(KEY_CONTACT,no);


                Book.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContacts();
//                Book.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            Contacts contacts = documentSnapshot.toObject(Contacts.class);
////                            String title = documentSnapshot.getString(KEY_NAME);
////                            String thought = documentSnapshot.getString(KEY_CONTACT);
//
//                            if (contacts != null) {
//                                tv1.setText(contacts.getName());
//                                tv2.setText(contacts.getPh_no());
//                            }
//                        }else {
//                            Toast.makeText(MainActivity.this, "No data", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("TAG", "onFailure: " + e.toString());
//                    }
//                });
            }
        });


    }

    private void showContacts(){
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data = "";
              for(QueryDocumentSnapshot snapshot: queryDocumentSnapshots){

                  Contacts contacts = snapshot.toObject(Contacts.class);

                  data += "Name: " + contacts.getName() +"\n" +"Contact Number: " + contacts.getPh_no() + "\n";

//                  Log.d("SuperV", "onSuccess: " + snapshot.getId());
              }
                tv1.setText(data);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void addContact(){
        String name = Fullname.getText().toString().trim();
        String num = phoneno.getText().toString().trim();
        Contacts cn = new Contacts();
        cn.setName(name);
        cn.setPh_no(num);

        collectionReference.add(cn);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Book.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(MainActivity.this, "Error hai boss", Toast.LENGTH_SHORT).show();
                }
                if(value.exists() && value != null){
                    Contacts contacts = value.toObject(Contacts.class);
                    String data = "";
//                            String title = documentSnapshot.getString(KEY_NAME);
//                            String thought = documentSnapshot.getString(KEY_CONTACT);

                    data += "Name: " + contacts.getName() +"\n" +"Contact Number: " + contacts.getPh_no();
                    if (contacts != null) {
                        tv1.setText(data);

                    }
                }
            }
        });
    }

}