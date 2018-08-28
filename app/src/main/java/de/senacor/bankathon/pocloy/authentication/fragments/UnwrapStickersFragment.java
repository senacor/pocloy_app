package de.senacor.bankathon.pocloy.authentication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.senacor.bankathon.pocloy.R;
import de.senacor.bankathon.pocloy.authentication.dto.StickerResources;
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
            if (userAssets.get(0).getStatus().equals("unpacked")) {
                firstSticker.setImageResource(StickerResources.forImageCode(userAssets.get(0).getContent()).getImageReference());
            } else {
                firstSticker.setOnClickListener(createOnClickListener(userAssets.get(0).getCodeId()));
            }

            secondSticker.setVisibility(View.VISIBLE);
            if (userAssets.get(1).getStatus().equals("unpacked")) {
                secondSticker.setImageResource(StickerResources.forImageCode(userAssets.get(1).getContent()).getImageReference());
            } else {
                secondSticker.setOnClickListener(createOnClickListener(userAssets.get(1).getCodeId()));
            }


            thirdSticker.setVisibility(View.VISIBLE);
            if (userAssets.get(2).getStatus().equals("unpacked")) {
                thirdSticker.setImageResource(StickerResources.forImageCode(userAssets.get(2).getContent()).getImageReference());
            } else {
                thirdSticker.setOnClickListener(createOnClickListener(userAssets.get(2).getCodeId()));
            }

            fourthSticker.setVisibility(View.VISIBLE);
            if (userAssets.get(3).getStatus().equals("unpacked")) {
                fourthSticker.setImageResource(StickerResources.forImageCode(userAssets.get(3).getContent()).getImageReference());
            } else {
                fourthSticker.setOnClickListener(createOnClickListener(userAssets.get(3).getCodeId()));
            }

        }

        if (userAssets.size() == 3) {
            firstSticker.setVisibility(View.VISIBLE);
            if (userAssets.get(0).getStatus().equals("unpacked")) {
                firstSticker.setImageResource(StickerResources.forImageCode(userAssets.get(0).getContent()).getImageReference());
            } else {
                firstSticker.setOnClickListener(createOnClickListener(userAssets.get(0).getCodeId()));
            }

            secondSticker.setVisibility(View.VISIBLE);
            if (userAssets.get(1).getStatus().equals("unpacked")) {
                secondSticker.setImageResource(StickerResources.forImageCode(userAssets.get(1).getContent()).getImageReference());
            } else {
                secondSticker.setOnClickListener(createOnClickListener(userAssets.get(1).getCodeId()));
            }


            thirdSticker.setVisibility(View.VISIBLE);
            if (userAssets.get(2).getStatus().equals("unpacked")) {
                thirdSticker.setImageResource(StickerResources.forImageCode(userAssets.get(2).getContent()).getImageReference());
            } else {
                thirdSticker.setOnClickListener(createOnClickListener(userAssets.get(2).getCodeId()));
            }
        }

        if (userAssets.size() == 2) {
            firstSticker.setVisibility(View.VISIBLE);
            if (userAssets.get(0).getStatus().equals("unpacked")) {
                firstSticker.setImageResource(StickerResources.forImageCode(userAssets.get(0).getContent()).getImageReference());
            } else {
                firstSticker.setOnClickListener(createOnClickListener(userAssets.get(0).getCodeId()));
            }

            secondSticker.setVisibility(View.VISIBLE);
            if (userAssets.get(1).getStatus().equals("unpacked")) {
                secondSticker.setImageResource(StickerResources.forImageCode(userAssets.get(1).getContent()).getImageReference());
            } else {
                secondSticker.setOnClickListener(createOnClickListener(userAssets.get(1).getCodeId()));
            }
        }

        if (userAssets.size() == 1) {
            firstSticker.setVisibility(View.VISIBLE);
            if (userAssets.get(0).getStatus().equals("unpacked")) {
                firstSticker.setImageResource(StickerResources.forImageCode(userAssets.get(0).getContent()).getImageReference());
            } else {
                firstSticker.setOnClickListener(createOnClickListener(userAssets.get(0).getCodeId()));
            }
        }
    }

    private View.OnClickListener createOnClickListener(String codeId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnpackStickerTask unpackStickerTask = new UnpackStickerTask(codeId) {
                    @Override
                    protected void handleSuccessfulUnpackOfSticker(UnpackedSticker result) {
                        for (int i = 0; i < userAssets.size(); i++) {
                            if (userAssets.get(i).getCodeId().equals(result.getCodeId())) {
                                if (i == 0) {
                                    firstSticker.setImageResource(StickerResources.forImageCode(result.getContent()).getImageReference());
                                    break;
                                }
                                if (i == 1) {
                                    secondSticker.setImageResource(StickerResources.forImageCode(result.getContent()).getImageReference());
                                    break;
                                }
                                if (i == 2) {
                                    thirdSticker.setImageResource(StickerResources.forImageCode(result.getContent()).getImageReference());
                                    break;
                                }
                                if (i == 3) {
                                    fourthSticker.setImageResource(StickerResources.forImageCode(result.getContent()).getImageReference());
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    protected void handleFailedUnpackOfSticker(String reason) {
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), reason, Toast.LENGTH_LONG);
                        toast.show();
                    }
                };
                unpackStickerTask.execute();
            }
        };
    }

}
