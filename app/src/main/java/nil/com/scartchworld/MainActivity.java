package nil.com.scartchworld;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nil.com.scartchworld.helper.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private EditText maxAttempts;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper  = new DatabaseHelper(this);
        maxAttempts = findViewById(R.id.maxAttempts);
        maxAttempts.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validate(s.toString().trim());
            }

            public void validate(String input){
                if(input == null || "".equals(input)){
                    maxAttempts.requestFocus();
                    maxAttempts.setError("Please enter amount greater than 0 and less than 100");
                }else{
                    Integer parseInt = Integer.parseInt(input);
                    if(parseInt == 0 || parseInt > 100){
                        maxAttempts.requestFocus();
                        maxAttempts.setError("Please enter amount greater than 0 and less than 100");
                    }
                }
            }
        });

        String value = databaseHelper.getValue("maxAttempts");
        if(value !=null &&  Integer.valueOf(value) > 0){
            Intent scarchViewIntent = new Intent(getApplicationContext(),ScratchActivity.class);
            startActivity(scarchViewIntent);
        }
    }

    public void nextActivity(View view){

        databaseHelper.addKeyValue("maxAttempts",maxAttempts.getText().toString());
        databaseHelper.addKeyValue("remainingAttemptFromTotalAttempt",Integer.toString(100));
        Intent scarchViewIntent = new Intent(getApplicationContext(),ScratchActivity.class);
        startActivity(scarchViewIntent);
    }

    @Override
    public void onBackPressed(){
        if(doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this,"Please click BACK again to exit",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        },2000);
    }
}

    abstract class InputTextWatcher implements TextWatcher{

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public abstract void validate(String input);

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        validate(s.toString().trim());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}