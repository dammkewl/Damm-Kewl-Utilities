package nl.damm.util;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import nl.damm.util.hash.HashAnythingActivity;

public class UtilitiesLauncher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().getRootView();

        setContentView(R.layout.activity_utilities_launcher);
    }

    public void showHashChecker(final View view) {
        Intent intent = new Intent(this, HashAnythingActivity.class);
        startActivity(intent);
    }

}
