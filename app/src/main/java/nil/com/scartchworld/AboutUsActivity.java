package nil.com.scartchworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        TextView textView = findViewById(R.id.aboutus);
        textView.setText("Scratch world is provide you the scratch card which help you to grow your business \n Version : 1.0.0 \n Contact Number: +91-9960049799");
        textView.setTextSize(20);
    }
}
