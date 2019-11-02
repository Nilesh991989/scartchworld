package nil.com.scartchworld;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        remainingAttempts.setText(String.format("Total Attempts Status : %d / %d", remainingAttemptFromTotalAttempt,100));
        remainingAttempts.setTextSize(20);

        TextView remainingWinAttempts =  findViewById(R.id.remainingWinAttempt);
        remainingWinAttempts.setText(String.format("Total Win Attempts Status: %d / %d", winRemainingAttempt,maxAttempts));
        remainingWinAttempts.setTextSize(20);

        TextView statusStartActivityDate = findViewById(R.id.statusStartActivityDate);
        String startActivityDate = databaseHelper.getValue("startActivityDate");
        statusStartActivityDate.setText(String.format("Start Activity Date : %s",startActivityDate));
        statusStartActivityDate.setTextSize(20);

        TextView statusUpdateActivityDate = findViewById(R.id.statusUpdateActivityDate);
        String updateActivityDate = databaseHelper.getValue("updateActivityDate");
        statusUpdateActivityDate.setText(String.format("Update Activity Date : %s",updateActivityDate));
        statusUpdateActivityDate.setTextSize(20);
    }

    public void reset(View view){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AppStatusActivity.this);
        alertBuilder.setTitle("Confirm");
        alertBuilder.setMessage("Are you sure");
        alertBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                databaseHelper.dropTable();
                Intent mainActivityIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainActivityIntent);
            }
        });

        alertBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    private int getIntValue(String value) {
        return value == null ? 0 : Integer.parseInt(value);
    }
}
