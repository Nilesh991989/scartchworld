package nil.com.scartchworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import nil.com.scartchworld.helper.database.DatabaseHelper;

public class AppStatusActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_status);
        databaseHelper = new DatabaseHelper(this);

        int maxAttempts = getIntValue(databaseHelper.getValue("maxAttempts"));
        int remainingAttemptFromTotalAttempt = getIntValue(databaseHelper.getValue("remainingAttemptFromTotalAttempt"));
        int winRemainingAttempt = getIntValue(databaseHelper.getValue("winRemainingAttempt"));

        TextView remainingAttempts =  findViewById(R.id.remainingAttemptStatus);
        remainingAttempts.setText(String.format("Remaining attempts are %d / %d", remainingAttemptFromTotalAttempt,100));

        TextView remainingWinAttempts =  findViewById(R.id.remainingWinAttempt);
        remainingWinAttempts.setText(String.format("Remaining win attempts are %d / %d", winRemainingAttempt,maxAttempts));
    }

    private int getIntValue(String value) {
        return value == null ? 0 : Integer.parseInt(value);
    }
}
