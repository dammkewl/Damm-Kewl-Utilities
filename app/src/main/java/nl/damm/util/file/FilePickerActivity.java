package nl.damm.util.file;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import nl.damm.util.R;

public class FilePickerActivity extends AppCompatActivity {

    private FilePickerViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker);

        model = ViewModelProviders.of(this).get(FilePickerViewModel.class);

        TextView text = findViewById(R.id.currentDirectory);
        try {
            text.setText(model.getCurrentViewingDirectory().getCanonicalPath());//does canonicalizing visit links? or is it just FS dependent hence the IOex?
        } catch (IOException e) {
            text.setText("Could not canonicalize the path of the value supplied for pre-setting the current viewing directory");
        }

        RecyclerView directories = findViewById(R.id.filePickerDirectories);
        directories.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });
        directories.setLayoutManager(new LinearLayoutManager(this));
    }
}
