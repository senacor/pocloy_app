package de.senacor.bankathon.pocloy.authentication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.glxn.qrgen.android.QRCode;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.senacor.bankathon.pocloy.R;
import de.senacor.bankathon.pocloy.authentication.dto.UserVoucher;
import de.senacor.bankathon.pocloy.authentication.framework.DataHolder;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class ShowVouchersFragment extends Fragment {

    private static final String MERCHANT_VOUCHER_FORMAT = "https://desolate-depths-64341.herokuapp.com/user/consumeVoucher?voucherId=%d&user=%s";

    @BindView(R.id.own_vouchers_filter)
    TextView ownVouchersFilter;

    @BindView(R.id.own_vouchers_table)
    TableLayout ownVouchersTable;

    private List<UserVoucher> previousTableRows;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_show_vouchers, container, false);
        ButterKnife.bind(this, inflate);

        ownVouchersFilter.addTextChangedListener(new FilterWatcher());

        previousTableRows = DataHolder.getUserVouchers();
        buildTable(previousTableRows);

        return inflate;
    }

    private void buildTable(List<UserVoucher> data) {
        data
                .stream()
                .map(this::createTableRow)
                .forEach(ownVouchersTable::addView);
    }

    @NonNull
    private TableRow createTableRow(UserVoucher userVoucher) {
        TableRow tableRow = new TableRow(getContext());
        tableRow.setLayoutParams(new TableLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        tableRow.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        tableRow.setPadding(20, 20, 10, 20);

        TextView redeamableProduct = new TextView(getContext());
        redeamableProduct.setText(userVoucher.getVoucherTypeName());
        redeamableProduct.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        redeamableProduct.setForegroundGravity(Gravity.START);
        redeamableProduct.setPadding(20, 10, 10, 10);
        tableRow.addView(redeamableProduct);

        QRCode qrCode = QRCode.from(String.format(
                Locale.getDefault(),
                MERCHANT_VOUCHER_FORMAT,
                userVoucher.getId(),
                GsonRestTemplate.getCredentials().getUsername()
        ));
        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(qrCode.bitmap());
        imageView.setForegroundGravity(Gravity.END);
        imageView.setPadding(0, 0, 20, 0);
        tableRow.addView(imageView);

        tableRow.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            ImageView largeQrView = new ImageView(getContext());
            largeQrView.setImageBitmap(qrCode.bitmap());
            largeQrView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            largeQrView.setMinimumHeight(700);

            builder
                    .setTitle(R.string.exchange_voucher)
                    .setPositiveButton(R.string.exchange_voucher_ok, (dialog, which) -> {
                        // TODO
                        dialog.dismiss();
                    })
                    .setView(largeQrView)
            ;
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        return tableRow;
    }

    private class FilterWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            List<UserVoucher> tableRows = DataHolder.getUserVouchers();
            if (StringUtils.hasLength(s)) {
                tableRows = tableRows
                        .stream()
                        .filter(entry -> {
                            String voucherName = entry.getVoucherTypeName().toLowerCase();
                            String filter = s.toString().toLowerCase();
                            return voucherName.contains(filter);
                        })
                        .collect(Collectors.toList());
            }
            if (!Objects.equals(previousTableRows, tableRows)) {
                ownVouchersTable.removeAllViews();
                buildTable(tableRows);
                previousTableRows = tableRows;
            }
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
    }
}
