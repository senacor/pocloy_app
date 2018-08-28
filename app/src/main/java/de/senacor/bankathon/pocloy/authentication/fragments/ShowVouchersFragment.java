package de.senacor.bankathon.pocloy.authentication.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.glxn.qrgen.android.QRCode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.senacor.bankathon.pocloy.R;

public class ShowVouchersFragment extends Fragment {

    private static final String QR_CODES_TO_RENDER = "qrCodes";

    private List<String> qrCodesToRender;

    @BindView(R.id.imageView1)
    ImageView firstImageView;
    @BindView(R.id.imageView2)
    ImageView secondImageView;
    @BindView(R.id.imageView3)
    ImageView thirdImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_show_vouchers, container, false);
        ButterKnife.bind(this, inflate);

        this.qrCodesToRender = getArguments().getStringArrayList(QR_CODES_TO_RENDER);
        renderQrCodes();
        return inflate;
    }

    private void renderQrCodes() {
        String firstQrCode = qrCodesToRender.get(0);
        String secondQrCode = qrCodesToRender.get(1);
        String thirdQrCode = qrCodesToRender.get(2);

        Bitmap firstBitmap = QRCode.from(firstQrCode).bitmap();
        Bitmap secondBitmap = QRCode.from(secondQrCode).bitmap();
        Bitmap thirdBitmap = QRCode.from(thirdQrCode).bitmap();

        firstImageView.setImageBitmap(firstBitmap);
        secondImageView.setImageBitmap(secondBitmap);
        thirdImageView.setImageBitmap(thirdBitmap);
    }

    public static Bundle createBundle() {
        Bundle bundle = new Bundle();
        //TODO: Fix this hack
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        bundle.putStringArrayList(QR_CODES_TO_RENDER, list);
        return bundle;
    }

}
