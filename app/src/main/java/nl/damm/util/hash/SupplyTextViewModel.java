package nl.damm.util.hash;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.nio.charset.Charset;

public class SupplyTextViewModel extends AndroidViewModel {

    private final SharedPreferences prefs;

    private String textValue;
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
                        .commit()) {
            throw new IllegalStateException("commit failed");//TODO: where does this bubble to?
        }
    }

    public void setTextValue(String textValue) {//TODO: synchronized? volatile?
        this.textValue = textValue;
    }

    public void setCharset(Charset charset) {//TODO: synchronized? volatile?
        this.charset = charset;
    }

    String getTextValue() {
        return this.textValue;
    }

    Charset getCharset() {
        return this.charset;
    }

}
