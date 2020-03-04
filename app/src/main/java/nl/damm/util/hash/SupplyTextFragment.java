package nl.damm.util.hash;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import java.nio.charset.Charset;
import java.util.SortedMap;

import nl.damm.util.R;
import nl.damm.util.widget.SpinnerUtils;

public class SupplyTextFragment extends Fragment {

    private SupplyTextViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.supply_text_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SortedMap<String,Charset> charsets = Charset.availableCharsets();

        Spinner p = SpinnerUtils.spinMeUp(
                this.getActivity(),
                R.id.charsetSpinner,
                charsets.values().toArray()
        );

        //This will be what most people use right? Or is it UTF-16 nowadays since all the extensions?
        if(charsets.containsKey("UTF-8")) {
            p.setSelection(charsets.headMap("UTF-8").size());
        }

        mViewModel = ViewModelProviders.of(this).get(SupplyTextViewModel.class);

        EditText e = this.getActivity().findViewById(R.id.hashInputText);
        e.setText(mViewModel.val);
        e.setOnEditorActionListener((v, actionId, event) -> {mViewModel.val = e.getText().toString(); return false;});
    }
}
