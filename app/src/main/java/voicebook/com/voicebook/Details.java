package voicebook.com.voicebook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Details extends AppCompatActivity {

    private TextView output;
    private TextView name;
    private TextView position;
    private TextView dept;
    private TextView address;
    private TextView office;
    private TextView home;
    private TextView mobile;
    private TextView email;
    private TextView dob;
    private TextView vehicle;
    private ImageButton callOffice;
    private ImageButton callHome;
    private ImageButton callMobile;
    private ImageButton sendEmail;
    private ImageButton sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        output = findViewById(R.id.output);
        name = findViewById(R.id.DisplayName);
        position = findViewById(R.id.Position);
        dept = findViewById(R.id.Department);
        address = findViewById(R.id.DisplayAddress);
        office = findViewById(R.id.OfficeIntercom);
        home = findViewById(R.id.HomeIntercom);
        mobile = findViewById(R.id.MobileNumber);
        email = findViewById(R.id.Email);
        dob = findViewById(R.id.DateOfBirth);
        vehicle = findViewById(R.id.Vehicle);
        callOffice = findViewById(R.id.CallOfficeIntercom);
        callHome = findViewById(R.id.CallHomeIntercom);
        callMobile = findViewById(R.id.CallMobile);
        sendEmail = findViewById(R.id.EmailButton);
        sendMessage = findViewById(R.id.SendMessage);


        String Fname = getIntent().getExtras().getString("fName");
        String Lname = getIntent().getExtras().getString("lName");

        String displayName = Fname + " " + Lname;
        name.setText(displayName);

        InputStream is = getResources().openRawResource(R.raw.contacts);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line = " ";
        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");

                if ((Fname.toLowerCase()).equals(tokens[1].toLowerCase()) && (Lname.toLowerCase()).equals(tokens[2].toLowerCase())) {
                    address.setText(tokens[3]);
                    office.setText(tokens[4]);
                    home.setText(tokens[5]);
                    mobile.setText(tokens[6]);
                    email.setText(tokens[7]);
                    dept.setText(tokens[8]);
                    dob.setText(tokens[11]);
                    vehicle.setText(tokens[10]);
                    position.setText(tokens[9]);
                }
            }
        } catch (IOException e) {
            Log.wtf("MainActvity", "Error Reading DataFile line = " + line);

            e.printStackTrace();
        }

        callMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = (mobile.getText().toString().trim());
                dialContactPhone(number);
            }
        });

        callOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = (office.getText().toString().trim());
                number = "0624327"+number;
                dialContactPhone(number);
            }
        });

        callHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = (home.getText().toString().trim());
                number = "0624327"+number;
                dialContactPhone(number);
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String[] TO = {Email};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");


                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Details.this,
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = mobile.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                startActivity(intent);
            }
        });


    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}
