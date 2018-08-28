package de.senacor.bankathon.pocloy.authentication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.LinkedList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.senacor.bankathon.pocloy.R;
import de.senacor.bankathon.pocloy.authentication.dto.UserAssets;
import de.senacor.bankathon.pocloy.authentication.framework.DataHolder;

public class UnwrapStickersFragment extends Fragment {
    @BindView(R.id.sticker1)
    ImageView firstSticker;

    @BindView(R.id.sticker2)
    ImageView secondSticker;

    @BindView(R.id.sticker3)
    ImageView thirdSticker;

    @BindView(R.id.sticker4)
    ImageView fourthSticker;

    List<UserAssets> userAssets;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unwrap_stickers, container, false);
        ButterKnife.bind(this, view);

        this.userAssets = DataHolder.getUserAssets();
        this.userAssets = new LinkedList<>();
        UserAssets userAsset = new UserAssets();
        userAssets.add(userAsset);
        userAssets.add(userAsset);
        userAssets.add(userAsset);
        userAssets.add(userAsset);

        initializeGiftView();

        return view;
    }

    private void initializeGiftView() {
        if (userAssets.size() == 4) {
            firstSticker.setVisibility(View.VISIBLE);
            secondSticker.setVisibility(View.VISIBLE);
            thirdSticker.setVisibility(View.VISIBLE);
            fourthSticker.setVisibility(View.VISIBLE);
        }

        if (userAssets.size() == 3) {
            firstSticker.setVisibility(View.VISIBLE);
            secondSticker.setVisibility(View.VISIBLE);
            thirdSticker.setVisibility(View.VISIBLE);
        }

        if (userAssets.size() == 2) {
            firstSticker.setVisibility(View.VISIBLE);
            secondSticker.setVisibility(View.VISIBLE);
        }

        if (userAssets.size() == 1) {
            firstSticker.setVisibility(View.VISIBLE);
        }
    }

}
