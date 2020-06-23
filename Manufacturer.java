package com.example.churdlab3;

import java.io.Serializable;
import java.util.ArrayList;

public class Manufacturer implements Serializable {

    String mNameManu;
    ArrayList<String> mModels;

    public Manufacturer(String name, ArrayList muscmModels){
        this.mNameManu = name;
        this.mModels = muscmModels;
    }
    public String getmNameManu(){
        return this.mNameManu;
    }

    public String getModelName(int pos){
        if(pos > mModels.size()){
            return "Position out of array bounds";
        }
        else
        {
            return mModels.get(pos);
        }
    }

    public void deleteModel(int pos){
        for(int x = 0; x < mModels.size(); x++){
            if(pos == x){
                mModels.remove(x);
            }
        }
    }
    public int getNumOfModels(){
        return mModels.size();
    }
    public void addModel(String model){
        mModels.add(model);
    }
    public void addModelCollection(ArrayList<String> additions){
        for(int x = 0; x< additions.size(); x++){
            mModels.add(additions.get(x));
        }
    }
}
