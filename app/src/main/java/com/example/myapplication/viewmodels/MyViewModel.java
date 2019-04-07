package com.example.myapplication.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.myapplication.models.MyModel;
import com.example.myapplication.repositories.MyRepository;

import java.util.List;

public class MyViewModel extends ViewModel{
    MutableLiveData<List<MyModel>> myPlacesData;
    private MyRepository myPlacesRepo;
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();

    public void init(){
        if(myPlacesData != null){
            return;
        }
        myPlacesRepo = MyRepository.getmInstance();
        myPlacesData = myPlacesRepo.getMyModelData();
    }

    public MutableLiveData<List<MyModel>> getMyPlacesData() {
        return myPlacesData;
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    public void addNewPlace(final MyModel myModel) {
        isUpdating.setValue(true);

        /*putting AsyncTask here is a bad practice but just pretending that we are fetching data from the server so i put a delay of 1 sec*/

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<MyModel> currentPlace = myPlacesData.getValue();
                currentPlace.add(myModel);
                myPlacesData.postValue(currentPlace);
                isUpdating.postValue(false);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
