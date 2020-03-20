package nl.damm.util.file;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.io.File;

public class FilePickerViewModel extends AndroidViewModel {

    private final SharedPreferences prefs;//is it okay to keep this ref?

    @NonNull
    private File currentViewingDirectory;

    @NonNull
    private File currentSelectedFile;//TODO: multiselect?

    public FilePickerViewModel(@NonNull Application application) {
        super(application);
        prefs = application.getSharedPreferences(FilePickerViewModel.class.getName(), Context.MODE_PRIVATE);
        currentViewingDirectory = new File(prefs.getString("currentViewingDirectory", "."));
        currentSelectedFile = new File(prefs.getString("currentSelectedFile", "."));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!
                prefs.edit()
                        .putString("currentViewingDirectory", currentViewingDirectory.getPath())
                        .putString("currentSelectedFile", currentSelectedFile.getPath())
                        .commit()) {//TODO: what are the fail states?
            throw new IllegalStateException("commit failed");//TODO: where does this bubble to?
        }
    }

    @NonNull
    public File getCurrentViewingDirectory() {
        return currentViewingDirectory;
    }

    @NonNull
    public File getCurrentSelectedFile() {
        return currentSelectedFile;
    }

    public void setCurrentViewingDirectory(@NonNull File currentViewingDirectory) {
        this.currentViewingDirectory = currentViewingDirectory;
    }

    public void setCurrentSelectedFile(@NonNull File currentSelectedFile) {
        this.currentSelectedFile = currentSelectedFile;
    }


}
