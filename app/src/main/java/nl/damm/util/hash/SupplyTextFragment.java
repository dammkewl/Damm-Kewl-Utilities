package nl.damm.util.hash;

import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.SortedMap;

import nl.damm.util.R;
import nl.damm.util.widget.SpinnerUtils;

public class SupplyTextFragment extends Fragment {

    public static SupplyTextViewModel getModel(FragmentActivity a, @IdRes int id) {
        return ((SupplyTextFragment) a.getSupportFragmentManager().findFragmentById(id)).viewModel;
    }

    private static final SortedMap<String,Charset> charsets = Collections.unmodifiableSortedMap(Charset.availableCharsets());

    private SupplyTextViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.supply_text_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(SupplyTextViewModel.class);

        Spinner p = SpinnerUtils.spinMeUp(
                this.getActivity(),
                R.id.charsetSpinner,
                charsets.values().toArray()
        );
        p.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setCharset(charsets.get(((Charset)p.getItemAtPosition(position)).name()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                throw new IllegalStateException();//TODO: how does this bubble?
            }
        });
        p.setSelection(charsets.headMap(viewModel.getCharset().name()).size());

        EditText e = this.getActivity().findViewById(R.id.hashInputText);
        e.setText(viewModel.getTextValue());
        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setTextValue(s.toString());
            }
        });
    }
}
