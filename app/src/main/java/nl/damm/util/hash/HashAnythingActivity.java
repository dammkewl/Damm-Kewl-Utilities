package nl.damm.util.hash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import org.conscrypt.OpenSSLMessageDigestJDK;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.MessageDigestSpi;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

import nl.damm.util.R;
import nl.damm.util.file.FilePickerActivity;
import nl.damm.util.stream.Collectors;

public class HashAnythingActivity extends AppCompatActivity {

    private static final String serviceTypeNameForHashAlgos = MessageDigest.class.getSimpleName();
    private static final Map<String, Collection<Service>> servicesProvidingAlgos = Collections.unmodifiableMap(
            Stream.of(Security.getProviders())
                    .map(Provider::getServices)
                    .flatMap(Collection::stream)
                    .filter(s -> serviceTypeNameForHashAlgos.equals(s.getType()))
                    .collect(Collectors.toIdentity(
                            new HashMap<>(),
                            (map, service) -> map.computeIfAbsent(service.getAlgorithm(), key -> new HashSet<>())
                                    .add(service)

                    ))
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_anything);

        Spinner algoSpinner = spinMeUp(
                R.id.hashAlgoSpinner,
                servicesProvidingAlgos.keySet().toArray(new String[servicesProvidingAlgos.size()])
        );
        algoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinMeUp(
                        R.id.hashServiceSpinner,
                        servicesProvidingAlgos.get(parent.getItemAtPosition(position)).toArray(new Service[0])
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinMeUp(
                        R.id.hashServiceSpinner,
                        new String[0]
                );
            }
        });
    }

    private <T> Spinner spinMeUp(@IdRes int id, T[] items) {
        ArrayAdapter<T> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(id);
        spinner.setAdapter(adapter);
        return spinner;
    }

    public void hashMe(View view) throws NoSuchAlgorithmException {
        Spinner spinner = findViewById(R.id.hashServiceSpinner);
        System.out.println("SPINNER HEIGHT"+spinner.getHeight());
        Service service = (Service) spinner.getSelectedItem();
        TextView inText = findViewById(R.id.hashInputText);//EditText?
        TextView outText = findViewById(R.id.hashOutputText);//EditText?
        Object serviceItem = service.newInstance(null);
        for(Class<?> x = serviceItem.getClass(); x != null; x = x.getSuperclass()) {
            System.out.println("diggity: "+x.getCanonicalName());
        }
        MessageDigestSpi digest = (MessageDigestSpi) serviceItem;
        if(digest instanceof MessageDigest) {
            byte[] outBytes = ((MessageDigest)digest).digest(inText.getText().toString().getBytes());

            outText.setText(new BigInteger(outBytes).toString(16));
        } else if(digest instanceof OpenSSLMessageDigestJDK) {
            throw new IllegalStateException("No support yet for conscrypt");
        } else {
            throw new NoSuchAlgorithmException("Found non-comprehensive digest class type: "+digest.getClass().getCanonicalName());
        }
    }

    public void showFilePicker(View view) {
        Intent intent = new Intent(this, FilePickerActivity.class);
        startActivity(intent);
    }
}
