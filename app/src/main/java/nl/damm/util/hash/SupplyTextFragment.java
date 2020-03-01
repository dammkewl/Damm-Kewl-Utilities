package nl.damm.util.hash;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.nio.charset.Charset;

import nl.damm.util.R;
import nl.damm.util.widget.SpinnerUtils;

public class SupplyTextFragment extends Fragment {

    private SupplyTextViewModel mViewModel;

    public static SupplyTextFragment newInstance() {
        return new SupplyTextFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.supply_text_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SpinnerUtils.spinMeUp(
                this.getActivity(),
                R.id.charsetSpinner,
                Charset.availableCharsets().values().toArray()
        );

        mViewModel = ViewModelProviders.of(this).get(SupplyTextViewModel.class);
        // TODO: Use the ViewModel
    }
}
