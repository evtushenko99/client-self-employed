package com.example.client_self_employed.data;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.client_self_employed.data.model.FirebaseExpert;
import com.example.client_self_employed.domain.IClientAppointmentCallback;
import com.example.client_self_employed.domain.IExpertCallBack;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RepositoryExperts implements IExpertsRepository {

    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mDatabaseReferenceExpert = mReference.child("experts");
    private StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();


    @Override
    public void loadAllExperts(IExpertCallBack callBack) {
        List<Expert> experts = new ArrayList<>();
        mDatabaseReferenceExpert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot expertKey : dataSnapshot.getChildren()) {
                    Expert expert = expertKey.getValue(Expert.class);
                    experts.add(expert);
                }
                // callBack.expertsIsLoaded(experts);
                loadExpertsPhoto(experts, callBack);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void loadExpertsNameForActiveAppointments(List<Appointment> activeAppointment, List<Long> expertsId, IClientAppointmentCallback dataStatus) {
        if (expertsId.size() == 0) {
            dataStatus.clientsAppointmentsIsLoaded(activeAppointment, new ArrayList<>());
        } else {
            for (Long expertId : expertsId) {
                mDatabaseReferenceExpert.orderByChild(FirebaseExpert.Fields.ID).equalTo(expertId)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<Expert> experts = new ArrayList<>();
                                for (DataSnapshot keyExpert : dataSnapshot.getChildren()) {
                                    Expert expert = keyExpert.getValue(Expert.class);
                                    if (!experts.contains(expert)) {
                                        experts.add(expert);
                                    }
                                }
                                if (experts.size() == expertsId.size()) {
                                    dataStatus.clientsAppointmentsIsLoaded(activeAppointment, experts);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
                            }
                        });

            }
        }

    }

    private void loadExpertsPhoto(List<Expert> experts, IExpertCallBack callBack) {
        mStorageReference.child("expert_" + experts.get(0).getId() + ".jpg").getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        experts.get(0).setExpertPhotoUri(task.getResult());
                        callBack.expertsIsLoaded(experts);
                    }
                });
       /* for (Expert expert : experts) {
            mStorageReference.child("expert_" + expert.getId() + ".jpg").getDownloadUrl()
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            expert.setExpertPhotoUri(task.getResult());
                            callBack.expertsIsLoaded(experts);
                        }
                    });
        }*/


    }
}
