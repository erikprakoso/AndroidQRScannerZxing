package com.iirykode.androidqrscannerzxing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textViewInvoice,textViewPaymentType,textViewPaymentDesc,textViewEmail,textViewMobilePhone,textViewAmount,textViewName,textViewTimeout;
    ImageButton fabScan;

    //Objek QR Scanner
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Lihat obejct
        fabScan = (ImageButton) findViewById(R.id.fabScan);
        textViewInvoice = (TextView) findViewById(R.id.textViewInvoice);
        textViewPaymentType = (TextView) findViewById(R.id.textViewPaymentType);
        textViewPaymentDesc = (TextView) findViewById(R.id.textViewPaymentDesc);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewMobilePhone = (TextView) findViewById(R.id.textViewMobilePhone);
        textViewAmount = (TextView) findViewById(R.id.textViewAmount);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewTimeout = (TextView) findViewById(R.id.textViewTimeout);

        //inisialisasi objek scan
        qrScan = new IntentIntegrator(this);

        //melampirkan onclick listener
        fabScan.setOnClickListener(this);

    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    textViewInvoice.setText(obj.getString("invoice"));
                    textViewPaymentType.setText(obj.getString("payment_type"));
                    textViewPaymentDesc.setText(obj.getString("payment_desc"));
                    textViewEmail.setText(obj.getString("email"));
                    textViewMobilePhone.setText(obj.getString("mobilephone"));
                    textViewAmount.setText(obj.getString("amount"));
                    textViewName.setText(obj.getString("name"));
                    textViewTimeout.setText(obj.getString("timeout"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
        //inisialisasi qr scan
        qrScan.initiateScan();
    }

}

