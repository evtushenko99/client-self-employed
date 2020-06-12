package com.example.client_self_employed.data;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.client_self_employed.R;
import com.example.client_self_employed.data.model.FirebaseClient;
import com.example.client_self_employed.domain.IAllUsersCallback;
import com.example.client_self_employed.domain.IClientCallback;
import com.example.client_self_employed.domain.ICreateClientCallback;
import com.example.client_self_employed.domain.model.Client;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RepositoryClient implements IClientRepository {
    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mDatabaseReferenceClient = mReference.child("clients");
    private StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();
    private ResourceWrapper mResourceWrapper;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public RepositoryClient(ResourceWrapper resourceWrapper) {
        mResourceWrapper = resourceWrapper;
    }


    @Override
    public void createClient(@NonNull Client newClient, String password, ICreateClientCallback createClientCallback) {
        mAuth.createUserWithEmailAndPassword(newClient.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            newClient.setId(uid);
                            mDatabaseReferenceClient.child(uid)
                                    .setValue(newClient)
                                    .addOnCompleteListener(task1 -> createClientCallback.clientIsCreated(true, uid))
                                    .addOnFailureListener(e -> createClientCallback.clientIsCreated(false, null));
                        } else {
                            createClientCallback.clientIsCreated(false, null);
                        }
                    }
                });
    }

    @Override
    public void loadClient(@NonNull String clientId, IClientCallback callback) {
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
                        String error = "onCancelled loadClient: " + databaseError.getMessage();
                        callback.messageWorkOnClient(error);
                        Log.d(TAG, error);

                    }
                });
    }

    public void findClient(@NotNull String clientId, IAllUsersCallback callback) {
        mDatabaseReferenceClient.orderByChild(FirebaseClient.Fields.ID)
                .equalTo(clientId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot keyClient : dataSnapshot.getChildren()) {
                            Client client = keyClient.getValue(Client.class);
                            if (client.getId().equals(clientId)) {
                                callback.onClientCallBack(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onExpertCallBack(false);
                    }
                });
    }

    @Override
    public void updateClientBirthday(@NonNull String clientId, int dayOfBirth, int monthOfBirth, int yearOfBirth, IClientCallback callback) {
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
                                callback.messageWorkOnClient(mResourceWrapper.getString(R.string.successful_update));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String error = "onCancelled updateClientBirthday: " + databaseError.getMessage();
                        callback.messageWorkOnClient(error);
                        Log.d(TAG, error);
                    }
                });
    }

    @Override
    public void updateClientEmail(@NonNull String clientId, String newEmail, IClientCallback callback) {
        mDatabaseReferenceClient.orderByChild(FirebaseClient.Fields.ID)
                .equalTo(clientId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot keyClient : dataSnapshot.getChildren()) {
                            Client client = keyClient.getValue(Client.class);
                            if (client != null) {
                                client.setEmail(newEmail);
                                String clientId = String.valueOf(client.getId());
                                mDatabaseReferenceClient.child(clientId)
                                        .setValue(client)
                                        .addOnCompleteListener(task -> callback.clientsChanged(true));
                                callback.messageWorkOnClient(mResourceWrapper.getString(R.string.successful_update));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String error = "onCancelled updateClientEmail: " + databaseError.getMessage();
                        callback.messageWorkOnClient(error);
                        Log.d(TAG, error);
                    }
                });
    }

    @Override
    public void updateClientGender(@NonNull String clientId, String newGender, IClientCallback callback) {
        mDatabaseReferenceClient.orderByChild(FirebaseClient.Fields.ID)
                .equalTo(clientId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot keyClient : dataSnapshot.getChildren()) {
                            Client client = keyClient.getValue(Client.class);
                            if (client != null) {
                                client.setGender(newGender);
                                String clientId = String.valueOf(client.getId());
                                mDatabaseReferenceClient.child(clientId)
                                        .setValue(client)
                                        .addOnCompleteListener(task -> callback.clientsChanged(true));
                                callback.messageWorkOnClient(mResourceWrapper.getString(R.string.successful_update));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String error = "onCancelled updateAppointmentRating: " + databaseError.getMessage();
                        callback.messageWorkOnClient(error);
                        Log.d(TAG, error);
                    }
                });
    }

    @Override
    public void updateClientPhoneNumber(@NonNull String clientId, String newPhoneNumber, IClientCallback callback) {
        mDatabaseReferenceClient.orderByChild(FirebaseClient.Fields.ID)
                .equalTo(clientId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot keyClient : dataSnapshot.getChildren()) {
                            Client client = keyClient.getValue(Client.class);
                            if (client != null) {
                                client.setPhoneNumber(newPhoneNumber);
                                String clientId = String.valueOf(client.getId());
                                mDatabaseReferenceClient.child(clientId)
                                        .setValue(client)
                                        .addOnCompleteListener(task -> callback.clientsChanged(true));
                                callback.messageWorkOnClient(mResourceWrapper.getString(R.string.successful_update));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String error = "onCancelled updateClientPhoneNumber: " + databaseError.getMessage();
                        callback.messageWorkOnClient(error);
                        Log.d(TAG, error);
                    }
                });
    }

    @Override
    public void loadNewClientPhoto(@NonNull String clientId, String newClientPhoto, IClientCallback callback) {
        File file = new File(String.valueOf((newClientPhoto)));
        String fileName = file.getName();
        Uri uriFile = Uri.fromFile(new File(String.valueOf((newClientPhoto))));
        StorageReference reference = mStorageReference.child(fileName);
        reference.putFile(uriFile)
                .addOnSuccessListener(taskSnapshot -> {
                    if (taskSnapshot.getMetadata() != null) {
                        if (taskSnapshot.getMetadata().getReference() != null) {
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(uri -> updateClientPhotoUrl(clientId, uri.toString(), callback));
                        }
                    }

                })
                .addOnFailureListener(e -> callback.clientsChanged(false));
    }

    @Override
    public void updateFullName(@NonNull String clientId, String lastName, String name, String secondName, IClientCallback callback) {
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
                                callback.messageWorkOnClient(mResourceWrapper.getString(R.string.successful_update));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String error = "onCancelled updateFullName = [" + databaseError.getMessage() + "]";
                        callback.messageWorkOnClient(error);
                        Log.d(TAG, error);
                    }
                });
    }

    private void updateClientPhotoUrl(String clientId, String newUri, IClientCallback callback) {
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

    private void setNewClient(Client client, IClientCallback callback) {
        mDatabaseReferenceClient.child(String.valueOf(client.getId()))
                .setValue(client)
                .addOnCompleteListener(task -> callback.clientsChanged(true))
                .addOnFailureListener(e -> callback.clientsChanged(false));
    }

}
