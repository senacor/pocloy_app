package de.senacor.bankathon.pocloy.authentication.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.senacor.bankathon.pocloy.R;
import de.senacor.bankathon.pocloy.authentication.dto.StickerData;
import de.senacor.bankathon.pocloy.authentication.dto.StickerResources;
import de.senacor.bankathon.pocloy.authentication.dto.VoucherRedeemingData;
import de.senacor.bankathon.pocloy.authentication.framework.DataHolder;
import de.senacor.bankathon.pocloy.authentication.task.RedeemStickerTask;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class RedeemStickersFragment extends Fragment {

    @BindView(R.id.redeem_vouchers_filter)
    TextView redeemVouchersFilter;

    @BindView(R.id.redeem_vouchers_table)
    TableLayout redeemVouchersTable;

    LayoutInflater layoutInflater;

    private List<VoucherRedeemingData> previousTableRows;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_redeem, container, false);
        this.layoutInflater = inflater;
        ButterKnife.bind(this, view);

        redeemVouchersFilter.addTextChangedListener(new FilterWatcher());

        previousTableRows = DataHolder.getVoucherRedeemingData();
        buildTable(previousTableRows);

        return view;
    }

    private void buildTable(List<VoucherRedeemingData> data) {
        data
                .stream()
                .map(this::createTableRow)
                .forEach(redeemVouchersTable::addView);
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
        redeamableProduct.setOnClickListener(createTableRowOnClickListener(voucherRedeemingData.getId(), voucherRedeemingData
                .getPrice()));
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

    private View.OnClickListener createTableRowOnClickListener(int voucherRedeemingId, Map<StickerResources, Integer> entries) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(voucherRedeemingId, entries);
            }
        };
    }


    private void showDialog(int voucherRedeemingId, Map<StickerResources, Integer> entries) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
        builderSingle.setTitle("Redeem Stickers");
        RedeemStickerTask redeemStickerTask = new RedeemStickerTask(String.valueOf(voucherRedeemingId)) {
            @Override
            protected void handleSuccessfulRedemption() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "Sticker Redemption was successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            protected void handleFailedRedemption(String reason) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), reason, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        ArrayAdapter<StickerData> stickerDataArrayAdapter = generateArrayAdapter(entries);
        builderSingle.setNegativeButton("Redeem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                redeemStickerTask.execute();
            }
        });
        builderSingle.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.setAdapter(stickerDataArrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //nothing todo
            }
        });
        builderSingle.show();
    }

    private void showFailedToast(String reason) {
        Toast.makeText(getContext(), reason, Toast.LENGTH_LONG);
    }


    private ArrayAdapter<StickerData> generateArrayAdapter(Map<StickerResources, Integer> entries) {
        List<StickerData> stickerDataList = entries.entrySet()
                .stream()
                .map(entry -> new StickerData(entry.getValue(), entry.getKey().getImageCode()))
                .collect(Collectors.toList());

        final ArrayAdapter<StickerData> arrayAdapter = new ArrayAdapter<StickerData>(getContext(), android.R.layout.select_dialog_singlechoice, stickerDataList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                ViewHolder viewHolder = Optional.ofNullable(convertView)
                        .map(View::getTag)
                        .map(tag -> (ViewHolder) tag)
                        .orElseGet(() -> {
                            @SuppressLint("ViewHolder") View collectionItem =
                                    layoutInflater.inflate(R.layout.redeem_sticker_item, parent, false);
                            ImageView image = collectionItem.findViewById(R.id.redeem_sticker_image);
                            TextView count = collectionItem.findViewById(R.id.redeem_sticker_count);
                            return new ViewHolder(collectionItem, image, count);
                        });
                if (convertView != null) {
                    convertView.setTag(viewHolder);
                }

                StickerData stickerData = getItem(position);
                assert stickerData != null;
                viewHolder.image.setImageResource(stickerData.getStickerId());
                viewHolder.count.setText(String.format(Locale.getDefault(), "x%d", stickerData.getAmount()));

                return viewHolder.collectionItem;
            }
        };

        return arrayAdapter;
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

    private class FilterWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            List<VoucherRedeemingData> tableRows = DataHolder.getVoucherRedeemingData();
            if (StringUtils.hasLength(s)) {
                tableRows = tableRows
                        .stream()
                        .filter(entry -> {
                            String voucherName = entry.getName().toLowerCase();
                            String filter = s.toString().toLowerCase();
                            return voucherName.contains(filter);
                        })
                        .collect(Collectors.toList());
            }
            if (!Objects.equals(previousTableRows, tableRows)) {
                redeemVouchersTable.removeAllViews();
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

    private class ViewHolder {
        private final View collectionItem;
        private final ImageView image;
        private final TextView count;

        public ViewHolder(View collectionItem, ImageView image, TextView count) {
            this.collectionItem = collectionItem;
            this.image = image;
            this.count = count;
        }
    }
}
