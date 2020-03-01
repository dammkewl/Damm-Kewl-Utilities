package nl.damm.util.widget;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.IdRes;

public class SpinnerUtils {

    public static <T> Spinner spinMeUp(Activity activityContext, @IdRes int id, T[] items) {
        ArrayAdapter<T> adapter = new ArrayAdapter<>(
                activityContext,
                android.R.layout.simple_spinner_item,
                items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = activityContext.findViewById(id);
        spinner.setAdapter(adapter);
        return spinner;
    }
}
