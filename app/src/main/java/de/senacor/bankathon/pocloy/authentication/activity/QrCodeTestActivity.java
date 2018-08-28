package de.senacor.bankathon.pocloy.authentication.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import net.glxn.qrgen.android.QRCode;
import java.util.ArrayList;
import butterknife.ButterKnife;
import de.senacor.bankathon.pocloy.R;

public class QrCodeTestActivity extends AppCompatActivity {
    private static final String QR_CODES_TO_RENDER = "qrCodes";
    private static ArrayList<String> qrCodesToRender;

    public static Intent createIntent(Context context, @NonNull ArrayList<String> qrCodesToRender) {
        Intent intent = new Intent(context, QrCodeTestActivity.class);
        //TODO: Fix this hack 
        ArrayList<String> list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        list.add("C");

        intent.putStringArrayListExtra(QR_CODES_TO_RENDER, list);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        this.qrCodesToRender = getIntent().getStringArrayListExtra(QR_CODES_TO_RENDER);
        
        renderQrCodes();
    }

    private void renderQrCodes() {
        String firstQrCode = qrCodesToRender.get(0);
        String secondQrCode = qrCodesToRender.get(1);
        String thirdQrCode = qrCodesToRender.get(2);

        Bitmap firstBitmap = QRCode.from(firstQrCode).bitmap();
        Bitmap secondBitmap = QRCode.from(secondQrCode).bitmap();
        Bitmap thirdBitmap = QRCode.from(thirdQrCode).bitmap();

        ImageView firstImageView = (ImageView) findViewById(R.id.imageView1);
        firstImageView.setImageBitmap(firstBitmap);
        ImageView secondImageView = (ImageView) findViewById(R.id.imageView2);
        secondImageView.setImageBitmap(secondBitmap);
        ImageView thirdImageView = (ImageView) findViewById(R.id.imageView3);
        thirdImageView.setImageBitmap(thirdBitmap);

    }


}
