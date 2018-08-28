package de.senacor.bankathon.pocloy.authentication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.senacor.bankathon.pocloy.R;
import de.senacor.bankathon.pocloy.authentication.dto.StickerResources;
import de.senacor.bankathon.pocloy.authentication.dto.VoucherRedeemingData;
import de.senacor.bankathon.pocloy.authentication.framework.DataHolder;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class RedeemStickersFragment extends Fragment {

    @BindView(R.id.redeem_vouchers_filter)
    TextView redeemVouchersFilter;

    @BindView(R.id.redeem_vouchers_table)
    TableLayout redeemVouchersTable;

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

        DataHolder.getVoucherRedeemingData()
                .stream()
                .map(this::createTableRow)
                .forEach(redeemVouchersTable::addView);

        return view;
    }

    @NonNull
    private TableRow createTableRow(VoucherRedeemingData voucherRedeemingData) {
        TableRow tableRow = new TableRow(getContext());
        tableRow.setLayoutParams(new TableLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        tableRow.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        tableRow.setPadding(20, 20, 10, 20);

        TextView redeamableProduct = new TextView(getContext());
        redeamableProduct.setText(voucherRedeemingData.getName());
        redeamableProduct.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        redeamableProduct.setForegroundGravity(Gravity.START);
        redeamableProduct.setPadding(20, 10, 10, 10);
        tableRow.addView(redeamableProduct);

        LinearLayout priceOverview = new LinearLayout(getContext());
        priceOverview.setOrientation(LinearLayout.VERTICAL);
        priceOverview.setHorizontalGravity(Gravity.END);
        voucherRedeemingData.getPrice()
                .entrySet()
                .stream()
                .map(this::createPriceOverview)
                .forEach(priceOverview::addView);
        tableRow.addView(priceOverview);

        return tableRow;
    }

    @NonNull
    private LinearLayout createPriceOverview(Map.Entry<StickerResources, Integer> entry) {
        LinearLayout price = new LinearLayout(getContext());
        price.setOrientation(LinearLayout.HORIZONTAL);

        ImageView priceImage = new ImageView(getContext());
        priceImage.setImageResource(entry.getKey().getImageReference());
        priceImage.setMinimumHeight(20);
        price.addView(priceImage);

        TextView amount = new TextView(getContext());
        amount.setText(String.format(Locale.getDefault(), "x%d", entry.getValue()));
        amount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        amount.setPadding(10, 0, 20, 0);
        amount.setForegroundGravity(Gravity.END);
        price.addView(amount);

        return price;
    }

}
