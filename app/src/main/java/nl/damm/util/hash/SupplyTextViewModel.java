package nl.damm.util.hash;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import nl.damm.util.R;

public class SupplyTextViewModel extends AndroidViewModel {

//    final LiveData<String> userLiveData = new LiveData<String>(){
//        publi
//    };
    private final SharedPreferences prefs;
    public String val;

    public SupplyTextViewModel(@NonNull Application application) {
        super(application);
        prefs = application.getSharedPreferences(SupplyTextViewModel.class.getName(), Context.MODE_PRIVATE);
        val = prefs.getString("inputText","");
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if(!prefs.edit().putString("inputText", val).commit()) {
            throw new IllegalStateException("commit failed");
        }
    }
}
