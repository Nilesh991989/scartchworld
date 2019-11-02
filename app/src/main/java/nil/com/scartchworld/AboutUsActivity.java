package nil.com.scartchworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.StringJoiner;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView applicationTextView = findViewById(R.id.aboutus_application);
        applicationTextView.setText("Scratch world is provide you the scratch card which help you to grow your business");
        applicationTextView.setTextSize(18);

        TextView versionTextView = findViewById(R.id.aboutus_version);
        versionTextView.setText("Version : 1.0.0");
        versionTextView.setTextSize(18);

        TextView contactTextView = findViewById(R.id.aboutus_contactnumber);
        contactTextView.setText("Contact Number : +91-9960049799");
        contactTextView.setTextSize(18);

        TextView emailTextView = findViewById(R.id.aboutus_email);
        emailTextView.setText("Email : Nileshdoshi991989@gmail.com");
        emailTextView.setTextSize(18);
    }
}
