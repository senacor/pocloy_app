package de.senacor.bankathon.pocloy.authentication.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
import de.senacor.bankathon.pocloy.authentication.dto.StickerData;
import de.senacor.bankathon.pocloy.authentication.dto.StickerResources;
import de.senacor.bankathon.pocloy.authentication.dto.UserAssets;
import de.senacor.bankathon.pocloy.authentication.framework.DataHolder;
import de.senacor.bankathon.pocloy.authentication.task.UpdateCollectionTask;

public class MyCollectionFragment extends Fragment {

    private static final String STICKER_DATA_KEY = "stickerData";

    @BindView(R.id.stickerList)
    ListView stickersList;

    @BindView(R.id.pullToRefreshCollection)
    SwipeRefreshLayout pullToRefreshCollection;

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
        ArrayAdapter stickersAdapter = new ArrayAdapter<StickerData>(
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

        pullToRefreshCollection.setOnRefreshListener(() -> {
            UpdateCollectionTask updateCollectionTask = new UpdateCollectionTask() {

                @Override
                protected void handleFailedAuthentication() {
                    Toast toast = Toast.makeText(getContext(), "Could not update sticker collection", Toast.LENGTH_SHORT);
                    toast.show();
                }

                @Override
                protected void handleSuccessfulAuthentication(List<UserAssets> result) {
                    super.handleSuccessfulAuthentication(result);
                    stickersAdapter.clear();
                    stickerData.addAll(mapUserAssetsToStickData(result));
                    pullToRefreshCollection.setRefreshing(false);
                }
            };
            pullToRefreshCollection.setRefreshing(true);
            updateCollectionTask.execute((Void) null);
        });

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
     * Factory method which creates an instance of {@link MyCollectionFragment} with from
     * {@link DataHolder#getUserAssets()}.
     *
     * @return a new instance of {@link MyCollectionFragment}
     */
    @NonNull
    public static MyCollectionFragment createMyCollectionFragment() {
        List<StickerData> stickerData = mapUserAssetsToStickData(DataHolder.getUserAssets());

        MyCollectionFragment fragment = new MyCollectionFragment();
        fragment.setArguments(createBundle(stickerData));
        return fragment;
    }

    private static List<StickerData> mapUserAssetsToStickData(List<UserAssets> userAssets) {
        return userAssets
                .stream()
                .filter(userAsset -> !StickerResources.UNKNOWN.getImageCode().equals(userAsset.getContent()))
                .collect(Collectors.toMap(
                        UserAssets::getContent,
                        userAsset -> 1,
                        Integer::sum
                )).entrySet()
                .stream()
                .map(entry -> new StickerData(entry.getValue(), entry.getKey()))
                .collect(Collectors.toList());
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
