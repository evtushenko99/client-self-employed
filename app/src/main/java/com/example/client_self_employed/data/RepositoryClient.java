package com.example.client_self_employed.data;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.client_self_employed.data.model.FirebaseClient;
import com.example.client_self_employed.domain.IClientCallback;
import com.example.client_self_employed.domain.model.Client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RepositoryClient implements IClientRepository {
    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mDatabaseReferenceClient = mReference.child("clients");
    private StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();


    @Override
    public void loadClient(@NonNull long clientId, IClientCallback callback) {
        mDatabaseReferenceClient.orderByChild(FirebaseClient.Fields.ID)
                .equalTo(clientId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Client client = null;
                        for (DataSnapshot keyClient : dataSnapshot.getChildren()) {
                            client = keyClient.getValue(Client.class);
                        }
                        callback.clientIsLoaded(client);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void updateClientBirthday(@NonNull long clientId, int dayOfBirth, int monthOfBirth, int yearOfBirth, IClientCallback callback) {
        mDatabaseReferenceClient.orderByChild(FirebaseClient.Fields.ID)
                .equalTo(clientId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot keyClient : dataSnapshot.getChildren()) {
                            Client client = keyClient.getValue(Client.class);
                            if (client != null) {
                                client.setDayOfBirth(dayOfBirth);
                                client.setMonthOfBirth(monthOfBirth);
                                client.setYearOfBirth(yearOfBirth);
                                String clientId = String.valueOf(client.getId());
                                mDatabaseReferenceClient.child(clientId)
                                        .setValue(client)
                                        .addOnCompleteListener(task -> callback.clientsChanged(true));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
                    }
                });
    }

    @Override
    public void loadNewClientPhoto(@NonNull long clientId, String newClientPhoto, IClientCallback callback) {
        File file = new File(String.valueOf((newClientPhoto)));
        String fileName = file.getName();
        StorageReference reference = mStorageReference.child(fileName);
        reference.putFile(Uri.parse(newClientPhoto))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (taskSnapshot.getMetadata() != null) {
                            if (taskSnapshot.getMetadata().getReference() != null) {
                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        updateClientPhotoUrl(clientId, uri.toString(), callback);
                                    }
                                });
                            }
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.clientsChanged(false);
                    }
                });
    }

    @Override
    public void updateFullName(long clientId, String lastName, String name, String secondName, IClientCallback callback) {
        mDatabaseReferenceClient.orderByChild(FirebaseClient.Fields.ID)
                .equalTo(clientId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot keyClient : dataSnapshot.getChildren()) {
                            Client client = keyClient.getValue(Client.class);
                            if (client != null) {
                                client.setLastName(lastName);
                                client.setFirstName(name);
                                client.setSecondName(secondName);
                                String clientId = String.valueOf(client.getId());
                                mDatabaseReferenceClient.child(clientId)
                                        .setValue(client)
                                        .addOnCompleteListener(task -> callback.clientsChanged(true));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
                    }
                });
    }

    private void updateClientPhotoUrl(long clientId, String newUri, IClientCallback callback) {
        mDatabaseReferenceClient.orderByChild(FirebaseClient.Fields.ID)
                .equalTo(clientId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot keyClient : dataSnapshot.getChildren()) {
                            Client client = keyClient.getValue(Client.class);
                            if (client != null) {
                                client.setClientPhotoUri(newUri);
                                setNewClient(client, callback);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.clientsChanged(false);
                    }
                });
    }

    public void setNewClient(Client client, IClientCallback callback) {
        mDatabaseReferenceClient.child(String.valueOf(client.getId()))
                .setValue(client)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        callback.clientsChanged(true);
                    }
                });
    }

    public void loadClientPhoto() {
    }


}
