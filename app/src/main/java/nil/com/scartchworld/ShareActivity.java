package nil.com.scartchworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareSubText = "Scratch Cards";
        String shareBodyText = "https://play.google.com/store/apps/details?id=com.whatsapp&hl=en";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubText);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(shareIntent, "Share With"));
    }
}
