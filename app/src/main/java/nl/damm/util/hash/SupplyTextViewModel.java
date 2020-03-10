package nl.damm.util.hash;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.nio.charset.Charset;

public class SupplyTextViewModel extends AndroidViewModel {

    private final SharedPreferences prefs;//is it okay to keep this ref?

    @NonNull
    private String textValue;
    @NonNull
    private Charset charset;

    public SupplyTextViewModel(@NonNull Application application) {
        super(application);
        prefs = application.getSharedPreferences(SupplyTextViewModel.class.getName(), Context.MODE_PRIVATE);
        textValue = prefs.getString("textValue", "");
        charset = Charset.forName(prefs.getString("charset", Charset.defaultCharset().name()));//TODO: throws UnsupportedCharsetException
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (!
                prefs.edit()
                        .putString("textValue", textValue)
                        .putString("charset", charset.name())
                        .commit()) {//TODO: what are the fail states?
            throw new IllegalStateException("commit failed");//TODO: where does this bubble to?
        }
    }

    public void setTextValue(@NonNull String textValue) {//TODO: synchronized? volatile?
        this.textValue = textValue;
    }

    public void setCharset(@NonNull Charset charset) {//TODO: synchronized? volatile?
        this.charset = charset;
    }

    @NonNull
    String getTextValue() {
        return this.textValue;
    }

    @NonNull
    Charset getCharset() {
        return this.charset;
    }

}
