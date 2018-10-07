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
        TextView textView = findViewById(R.id.aboutus);
        StringBuilder sb = new StringBuilder();
        sb.append("Scratch world is provide you the scratch card which help you to grow your business and having business").append("\n");
        sb.append("Version : 1.0.0").append("\n");
        sb.append("Contact Number : +91-9960049799").append("\n");
        sb.append("Email : Nileshdoshi991989@gmail.com");
        textView.setText(sb.toString());
        textView.setTextSize(20);
    }
}
