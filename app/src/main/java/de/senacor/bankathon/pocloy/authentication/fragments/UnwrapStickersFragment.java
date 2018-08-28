package de.senacor.bankathon.pocloy.authentication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.senacor.bankathon.pocloy.R;
import de.senacor.bankathon.pocloy.authentication.dto.UnpackedSticker;
import de.senacor.bankathon.pocloy.authentication.dto.UserAssets;
import de.senacor.bankathon.pocloy.authentication.framework.DataHolder;
import de.senacor.bankathon.pocloy.authentication.task.UnpackStickerTask;

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
        
        initializeGiftView();

        return view;
    }

    private void initializeGiftView() {
        if (userAssets.size() == 4) {
            firstSticker.setVisibility(View.VISIBLE);
            firstSticker.setOnClickListener(createOnClickListener(userAssets.get(0).getCodeId()));
            
            secondSticker.setVisibility(View.VISIBLE);
            secondSticker.setOnClickListener(createOnClickListener(userAssets.get(1).getCodeId()));
            
            thirdSticker.setVisibility(View.VISIBLE);
            thirdSticker.setOnClickListener(createOnClickListener(userAssets.get(2).getCodeId()));
            
            fourthSticker.setVisibility(View.VISIBLE);
            fourthSticker.setOnClickListener(createOnClickListener(userAssets.get(3).getCodeId()));
        }

        if (userAssets.size() == 3) {
            firstSticker.setVisibility(View.VISIBLE);
            firstSticker.setOnClickListener(createOnClickListener(userAssets.get(0).getCodeId()));
            
            secondSticker.setVisibility(View.VISIBLE);
            secondSticker.setOnClickListener(createOnClickListener(userAssets.get(1).getCodeId()));
            
            thirdSticker.setVisibility(View.VISIBLE);
            thirdSticker.setOnClickListener(createOnClickListener(userAssets.get(2).getCodeId()));
        }

        if (userAssets.size() == 2) {
            firstSticker.setVisibility(View.VISIBLE);
            firstSticker.setOnClickListener(createOnClickListener(userAssets.get(0).getCodeId()));
            
            secondSticker.setVisibility(View.VISIBLE);
            secondSticker.setOnClickListener(createOnClickListener(userAssets.get(1).getCodeId()));
        }

        if (userAssets.size() == 1) {
            firstSticker.setVisibility(View.VISIBLE);
            firstSticker.setOnClickListener(createOnClickListener(userAssets.get(0).getCodeId()));
        }
    }
    
    private View.OnClickListener createOnClickListener(String codeId){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnpackStickerTask unpackStickerTask = new UnpackStickerTask(codeId) {
                    @Override
                    protected void handleSuccessfulUnpackOfSticker(UnpackedSticker result) {
                        System.out.println("");
                    }

                    @Override
                    protected void handleFailedUnpackOfSticker() {
                        System.out.println("");
                    }
                };
                unpackStickerTask.execute();
            }
        };
    }

}
