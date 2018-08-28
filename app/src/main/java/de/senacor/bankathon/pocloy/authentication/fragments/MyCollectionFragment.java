package de.senacor.bankathon.pocloy.authentication.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.senacor.bankathon.pocloy.R;

public class MyCollectionFragment extends Fragment {

    private static final String STICKER_DATA_KEY = "stickerData";

    @BindView(R.id.stickerList)
    ListView stickersList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.bind(this, view);

        List<StickerData> stickerData =
                Optional.ofNullable(getArguments())
                        .map(arguments -> arguments.<StickerData>getParcelableArrayList(STICKER_DATA_KEY))
                        .map(filterOutUnknown())
                        .orElse(Collections.emptyList());
        ListAdapter stickersAdapter = new ArrayAdapter<StickerData>(
                getContext(),
                android.R.layout.simple_list_item_1,
                stickerData
        ) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                ViewHolder viewHolder = Optional.ofNullable(convertView)
                        .map(View::getTag)
                        .map(tag -> (ViewHolder) tag)
                        .orElseGet(() -> {
                            @SuppressLint("ViewHolder") View collectionItem =
                                    inflater.inflate(R.layout.collection_item, parent, false);
                            ImageView image = collectionItem.findViewById(R.id.collection_sticker_image);
                            TextView count = collectionItem.findViewById(R.id.collection_sticker_count);
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

        stickersList.setAdapter(stickersAdapter);
        return view;
    }

    @NonNull
    private Function<List<StickerData>, List<StickerData>> filterOutUnknown() {
        return stickers -> stickers.stream()
                .filter(data -> !Objects.equals("unknown", data.getStickerName()))
                .collect(Collectors.toList());
    }

    private static Bundle createBundle(List<StickerData> stickerData) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STICKER_DATA_KEY, new ArrayList<>(stickerData));
        return bundle;
    }


    /**
     * Factory method which creates an instance of {@link MyCollectionFragment} with the given sticker data.
     *
     * @param stickerData to be displayed
     * @return a new instance of {@link MyCollectionFragment}
     */
    @NonNull
    public static MyCollectionFragment createMyCollectionFragment(List<StickerData> stickerData) {
        MyCollectionFragment fragment = new MyCollectionFragment();
        fragment.setArguments(createBundle(stickerData));
        return fragment;
    }

    @Deprecated
    public static List<StickerData> createMockStickerData() {
        return Arrays.asList(
                new StickerData(4, "bottle_wine"),
                new StickerData(1, "car_hatchback"),
                new StickerData(2, "cup"),
                new StickerData(10, "food_apple"),
                new StickerData(6, "food"),
                new StickerData(4, "gas_station"),
                new StickerData(3, "pizza"),
                new StickerData(7, "hamburger"),
                new StickerData(8, "silverware_fork_knife"),
                new StickerData(2, "sunglasses"),
                new StickerData(12, "white_balance_sunny"),
                new StickerData(4, "unknown")
        );
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
