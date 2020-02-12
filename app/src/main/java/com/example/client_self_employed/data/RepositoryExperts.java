package com.example.client_self_employed.data;

import androidx.annotation.NonNull;

import com.example.client_self_employed.domain.IExpertCallBack;
import com.example.client_self_employed.domain.IExpertsRepository;
import com.example.client_self_employed.domain.model.Expert;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RepositoryExperts implements IExpertsRepository {
    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mDatabaseReferenceExpert = mReference.child("experts");

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
                callBack.expertsIsLoaded(experts);
                //   loadExpertsPhoto(experts, status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    /*private void loadExpertsPhoto(List<Expert> experts, IAppointmentCallback status) {
        List<Expert> expertsWithUri = new ArrayList<>();
        for (Expert expert : experts) {
            mPathReference.child("expert_" + expert.getId() + ".jpg").getDownloadUrl()
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            expert.setExpertPhotoUri(task.getResult());

                        }
                    });
        }
        status.clientsExpertsIsLoaded(experts);

    }*/
}
