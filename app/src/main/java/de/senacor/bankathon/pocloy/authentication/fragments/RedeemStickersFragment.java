package de.senacor.bankathon.pocloy.authentication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.senacor.bankathon.pocloy.R;

public class RedeemStickersFragment extends Fragment {

    @BindView(R.id.redeem_vouchers_filter)
    TextView redeemVouchersFilter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_redeem, container, false);
        ButterKnife.bind(this, view);

        redeemVouchersFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("onTextChanged", String.format("s = %s, start = %d, before = %d, count = %d", s, start, before, count));
            }

            @Override
            public void afterTextChanged(Editable s) {
                InputFilter noWhitespaceFilter = (source, start, end, dest, dstart, dend) ->
                        (source == null)
                                ? null
                                : source.toString().replaceAll("\\W", "");
                s.setFilters(new InputFilter[]{
                        noWhitespaceFilter,
                        new InputFilter.LengthFilter(30)
                });
            }
        });

        return view;
    }

}
