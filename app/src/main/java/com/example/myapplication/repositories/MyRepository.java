package com.example.myapplication.repositories;

import android.arch.lifecycle.MutableLiveData;

import com.example.myapplication.models.MyModel;

import java.util.ArrayList;
import java.util.List;

/*Singleton pattern*/

public class MyRepository {
    private static MyRepository mInstance;
    private ArrayList<MyModel> modelDataSet = new ArrayList<>();


    private MyRepository() {
    }

    public static MyRepository getmInstance(){
        if(mInstance == null){
            mInstance = new MyRepository();
        }
        return mInstance;
    }

    public MutableLiveData<List<MyModel>> getMyModelData(){

        setMyModelData();
        MutableLiveData<List<MyModel>> data = new MutableLiveData<>();
        data.setValue(modelDataSet);

        return data;
    }

    private void setMyModelData() {
        for(int i=0;i<10;i++){
            modelDataSet.add(new MyModel("https://i.redd.it/qn7f9oqu7o501.jpg",
                    "Portugal"));
        }

    }

}
