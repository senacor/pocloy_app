package de.senacor.bankathon.pocloy.authentication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.senacor.bankathon.pocloy.R;
import de.senacor.bankathon.pocloy.authentication.dto.StickerResources;
import de.senacor.bankathon.pocloy.authentication.dto.TradeOffer;
import de.senacor.bankathon.pocloy.authentication.framework.DataHolder;
import de.senacor.bankathon.pocloy.authentication.task.AcceptTradeTask;
import de.senacor.bankathon.pocloy.authentication.task.LoadAvailableTradesTask;
import de.senacor.bankathon.pocloy.authentication.task.UpdateMyTradesTask;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class TradeStickersFragment extends Fragment {

    @BindView(R.id.trade_stickers_filter)
    TextView tradeStickersFilter;

    @BindView(R.id.trade_stickers_table)
    TableLayout tradeStickersTable;

    @BindView(R.id.add_trade_offer)
    FloatingActionButton addTradeOffer;

    private List<TradeOffer> previousTableRows;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trade, container, false);
        ButterKnife.bind(this, view);

        tradeStickersFilter.addTextChangedListener(new FilterWatcher());

        previousTableRows = DataHolder.getTradeOffers();
        buildTable(previousTableRows);

        addTradeOffer.setOnClickListener(v -> addTradeOffer());

        return view;
    }

    private void buildTable(List<TradeOffer> data) {
        data
                .stream()
                .map(this::createTableRow)
                .forEach(tradeStickersTable::addView);
    }

    @NonNull
    private TableRow createTableRow(TradeOffer tradeOffer) {
        TableRow tableRow = new TableRow(getContext());
        tableRow.setLayoutParams(new TableLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        tableRow.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        tableRow.setVerticalGravity(Gravity.CENTER_VERTICAL);
        tableRow.setPadding(20, 20, 10, 20);
        tableRow.setOnClickListener(createTableRowOnClickListener(tradeOffer));

        LinearLayout offer = new LinearLayout(getContext());
        offer.setOrientation(LinearLayout.HORIZONTAL);
        offer.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        ImageView offerImage = new ImageView(getContext());
        offerImage.setImageResource(tradeOffer.getOfferedStickerType().getImageReference());
        offerImage.setPadding(20, 10, 10, 10);
        offerImage.setForegroundGravity(Gravity.START);
        offer.addView(offerImage);
        TextView offerAmount = new TextView(getContext());
        offerAmount.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
        offerAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        offerAmount.setGravity(Gravity.CENTER_VERTICAL);
        offerAmount.setText(String.format(Locale.getDefault(), "x%d", tradeOffer.getOfferedStickerAmount()));
        offer.addView(offerAmount);
        tableRow.addView(offer);

        ImageView arrows = new ImageView(getContext());
        arrows.setImageResource(R.drawable.swap_horizontal);
        arrows.setForegroundGravity(Gravity.CENTER_VERTICAL);
        tableRow.addView(arrows);

        LinearLayout needed = new LinearLayout(getContext());
        needed.setOrientation(LinearLayout.HORIZONTAL);
        needed.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        ImageView neededImage = new ImageView(getContext());
        neededImage.setImageResource(tradeOffer.getRequiredStickerType().getImageReference());
        neededImage.setPadding(10, 10, 20, 10);
        neededImage.setForegroundGravity(Gravity.END);
        needed.addView(neededImage);
        TextView neededAmount = new TextView(getContext());
        neededAmount.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
        neededAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        neededAmount.setGravity(Gravity.CENTER_VERTICAL);
        neededAmount.setText(String.format(Locale.getDefault(), "x%d", tradeOffer.getRequiredStickerAmount()));
        needed.addView(neededAmount);
        tableRow.addView(needed);

        return tableRow;
    }

    private View.OnClickListener createTableRowOnClickListener(TradeOffer tradeOffer) {
        return v -> showDialog(tradeOffer);
    }

    private void showDialog(TradeOffer tradeOffer) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
        builderSingle.setIcon(R.drawable.ic_launcher_background);
        builderSingle.setTitle(R.string.trade_stickers_accept_title);
        builderSingle.setPositiveButton("OK", (dialog, which) -> {
            AcceptTradeTask acceptTradeTask = new AcceptTradeTask() {

                @Override
                protected void handleSuccessfulRetrieval() {
                    Toast toast = Toast.makeText(getContext(), "Trade successfull!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                @Override
                protected void handleFailedRetrieval() {
                    Toast toast = Toast.makeText(getContext(), "Trade failed", Toast.LENGTH_LONG);
                    toast.show();
                }
            };
            acceptTradeTask.execute(tradeOffer);
            dialog.dismiss();
        });
        builderSingle.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        LinearLayout offer = new LinearLayout(getContext());
        offer.setOrientation(LinearLayout.VERTICAL);
        offer.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        offer.setGravity(Gravity.CENTER);
        offer.setForegroundGravity(Gravity.CENTER);
        offer.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        offer.setHorizontalGravity(Gravity.CENTER_VERTICAL);
        offer.setPadding(20, 10, 20, 10);

        LinearLayout give = new LinearLayout(getContext());
        give.setGravity(Gravity.CENTER);
        give.setOrientation(LinearLayout.HORIZONTAL);
        TextView givePrefix = new TextView(getContext());
        givePrefix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        givePrefix.setText(R.string.trade_offer_title);
        give.addView(givePrefix);
        TextView giveAmount = new TextView(getContext());
        giveAmount.setPadding(10, 0, 10, 0);
        giveAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        giveAmount.setText(String.format(Locale.getDefault(), "%dx", tradeOffer.getOfferedStickerAmount()));
        give.addView(giveAmount);
        ImageView giveImage = new ImageView(getContext());
        giveImage.setImageResource(tradeOffer.getOfferedStickerType().getImageReference());
        give.addView(giveImage);
        offer.addView(give);

        LinearLayout receive = new LinearLayout(getContext());
        receive.setGravity(Gravity.CENTER);
        receive.setOrientation(LinearLayout.HORIZONTAL);
        TextView receivePrefix = new TextView(getContext());
        receivePrefix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        receivePrefix.setText(R.string.trade_required_title);
        receive.addView(receivePrefix);
        TextView receiveAmount = new TextView(getContext());
        receiveAmount.setPadding(10, 0, 10, 0);
        receiveAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        receiveAmount.setText(String.format(Locale.getDefault(), "%dx", tradeOffer.getRequiredStickerAmount()));
        receive.addView(receiveAmount);
        ImageView receiveImage = new ImageView(getContext());
        receiveImage.setImageResource(tradeOffer.getRequiredStickerType().getImageReference());
        receive.addView(receiveImage);
        offer.addView(receive);

        builderSingle.setView(offer);

        builderSingle.show();
    }

    private void addTradeOffer() {
        // TODO Make dynamic
        TradeOffer tradeOffer = new TradeOffer(StickerResources.APPLE, 3, StickerResources.HAMBURGER, 1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Offer new trade?");
        builder.setMessage("3 apples for one hamburger");
        builder.setPositiveButton("OK", (dialog, which) -> {
            UpdateMyTradesTask updateMyTradesTask = new UpdateMyTradesTask() {
                @Override
                protected void handleSuccessfulRetrieval() {
                    Log.d("TradeStickersFragment", "Trade performed, updating...");
                    LoadAvailableTradesTask loadAvailableTradesTask = new LoadAvailableTradesTask() {

                        @Override
                        protected void handleSuccessfulRetrieval(List<TradeOffer> result) {
                            Log.d("TradeStickersFragment", "Trade performed, updating...");
                            DataHolder.setTradeOffers(result);
                            Toast toast = Toast.makeText(getContext(), "Created new trade: 3 apples for one hamburger", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        @Override
                        protected void handleFailedRetrieval() {
                            Log.d("TradeStickersFragment", "Trades could not be updated");
                            Toast toast = Toast.makeText(getContext(), "Trades could not be updated", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    };
                    loadAvailableTradesTask.execute((Void) null);
                }

                @Override
                protected void handleFailedRetrieval() {
                    Log.d("TradeStickersFragment", "Trade could not be performed");
                    Toast toast = Toast.makeText(getContext(), "Trade could not be performed", Toast.LENGTH_LONG);
                    toast.show();
                }
            };
            updateMyTradesTask.execute(new UpdateMyTradesTask.TradeOfferUpdate(tradeOffer, UpdateMyTradesTask.TradeOfferAction.ADD_NEW_OFFER));
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private class FilterWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            List<TradeOffer> tableRows = DataHolder.getTradeOffers();
            if (StringUtils.hasLength(s)) {
                tableRows = tableRows
                        .stream()
                        .filter(entry -> {
                            String neededName = entry.getRequiredStickerType().getImageCode().toLowerCase().replaceAll("_", "");
                            String offeredName = entry.getOfferedStickerType().getImageCode().toLowerCase().replaceAll("_", "");
                            String filter = s.toString().toLowerCase();
                            return neededName.contains(filter) || offeredName.contains(filter);
                        })
                        .collect(Collectors.toList());
            }
            if (!Objects.equals(previousTableRows, tableRows)) {
                tradeStickersTable.removeAllViews();
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
