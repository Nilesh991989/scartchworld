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
        remainingAttempts.setText(String.format("Remaining Attempts : %d / %d", remainingAttemptFromTotalAttempt,100));
        remainingAttempts.setTextSize(20);

        TextView remainingWinAttempts =  findViewById(R.id.remainingWinAttempt);
        remainingWinAttempts.setText(String.format("Remaining Win Attempts : %d / %d", winRemainingAttempt,maxAttempts));
        remainingWinAttempts.setTextSize(20);

        TextView statusStartActivityDate = findViewById(R.id.statusStartActivityDate);
        String startActivityDate = databaseHelper.getValue("startActivityDate");
        statusStartActivityDate.setText(String.format("Start Activity Date : %ss",startActivityDate));
        statusStartActivityDate.setTextSize(20);

        TextView statusUpdateActivityDate = findViewById(R.id.statusUpdateActivityDate);
        String updateActivityDate = databaseHelper.getValue("updateActivityDate");
        statusUpdateActivityDate.setText(String.format("Update Activity Date : %ss",updateActivityDate));
        statusUpdateActivityDate.setTextSize(20);
    }

    private int getIntValue(String value) {
        return value == null ? 0 : Integer.parseInt(value);
    }
}
