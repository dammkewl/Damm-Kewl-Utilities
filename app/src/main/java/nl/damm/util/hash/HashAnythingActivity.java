package nl.damm.util.hash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.conscrypt.OpenSSLMessageDigestJDK;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
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
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;

import nl.damm.util.R;
import nl.damm.util.file.FilePickerActivity;
import nl.damm.util.stream.Collectors;

import static nl.damm.util.string.NumberRepresentationConversions.makeItHappen;
import static nl.damm.util.string.NumberRepresentationConversions.toHexByteOrderBigEndianLowerCase;
import static nl.damm.util.widget.SpinnerUtils.spinMeUp;

public class HashAnythingActivity extends AppCompatActivity {

    private static final String serviceTypeNameForHashAlgos = MessageDigest.class.getSimpleName();
    private static final SortedMap<String, Collection<Service>> servicesProvidingAlgos = Collections.unmodifiableSortedMap(
            Stream.of(Security.getProviders())
                    .map(Provider::getServices)
                    .flatMap(Collection::stream)
                    .filter(s -> serviceTypeNameForHashAlgos.equals(s.getType()))
                    .collect(Collectors.toIdentity(
                            new TreeMap<>(),
                            (map, service) -> map.computeIfAbsent(service.getAlgorithm(), key -> new HashSet<>())
                                    .add(service)

                    ))
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_anything);

        Spinner algoSpinner = spinMeUp(
                this,
                R.id.hashAlgoSpinner,
                servicesProvidingAlgos.keySet().toArray(new String[servicesProvidingAlgos.size()])
        );

        algoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Service[] services = servicesProvidingAlgos.get(parent.getItemAtPosition(position)).toArray(new Service[0]);
                Spinner spinner = spinMeUp(
                        HashAnythingActivity.this,
                        R.id.hashServiceSpinner,
                        services
                );

                //preselect algo's we can actually do something with (maybe filter the rest?)
                for(int i = 0; i < services.length; ++i) {
                    try {
                        if(MessageDigest.class.isAssignableFrom(Class.forName(services[i].getClassName()))) {
                            spinner.setSelection(i);
                            break;
                        }
                    } catch (ClassNotFoundException e) {
                        continue;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinMeUp(
                        HashAnythingActivity.this,
                        R.id.hashServiceSpinner,
                        new String[0]
                );
            }
        });

        //until we make a "remember" feature, just preselect what most people will be looking for
        algoSpinner.setSelection(servicesProvidingAlgos.headMap("SHA-256").size());

    }

    public void hashMe(View view) throws NoSuchAlgorithmException {
        Spinner spinner = findViewById(R.id.hashServiceSpinner);
        Service service = (Service) spinner.getSelectedItem();

        SupplyTextViewModel model = SupplyTextFragment.getModel(this, R.id.supplyHashInputTextFragment);
        
        TextView outText = findViewById(R.id.hashOutputText);//EditText?
        Object serviceItem = service.newInstance(null);

        MessageDigestSpi digest = (MessageDigestSpi) serviceItem;
        if(digest instanceof MessageDigest) {
            ByteBuffer bb = model.getCharset().encode(model.getTextValue());
            byte[] arr = bb.array();
            byte[] target = new byte[bb.limit()-bb.arrayOffset()];
            System.arraycopy(arr, bb.arrayOffset(), target, 0, target.length);
            byte[] outBytes = ((MessageDigest)digest).digest(target);
            outText.setText(toHexByteOrderBigEndianLowerCase(outBytes));
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
