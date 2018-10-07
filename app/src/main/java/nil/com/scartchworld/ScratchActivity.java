package nil.com.scartchworld;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cooltechworks.views.ScratchImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import nil.com.scartchworld.helper.database.DatabaseHelper;

public class ScratchActivity extends AppCompatActivity {
    private boolean doubleBackToExitPressedOnce = false;
    private static final int TOTAL_ATTEMPT = 100;
    private List<Integer> randomPrizeList = new ArrayList<>();
    private List<Integer> randomModPrizes = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private int maxAttempts;
    private int remainingAttemptFromTotalAttempt;
    private int per10Set;
    private int prizeCounter;
    private boolean revealed = false;
    private boolean winFlag= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String updateActivityDate = simpleDateFormat.format(new Date());
        databaseHelper.addKeyValue("updateActivityDate",updateActivityDate);

        getInitialProperties();
        per10Set = (10 * maxAttempts) / TOTAL_ATTEMPT;
        if(randomModPrizes.isEmpty()){
            getForModData();
        }
        checkForEndOfAttempts();
        ScratchImageView scratchImageView = findViewById(R.id.scartchImage);
        int imageSourceId = getRandomImageSource();
        scratchImageView.setImageResource(imageSourceId);
        scratchImageView.setRevealListener(new ScratchImageView.IRevealListener() {
            @Override
            public void onRevealed(ScratchImageView tv) {
            }

            @Override
            public void onRevealPercentChangedListener(ScratchImageView siv, float percent) {
                if(!revealed){
                    revealed = true;
                    databaseHelper.addKeyValue("remainingAttemptFromTotalAttempt",Integer.toString(remainingAttemptFromTotalAttempt));
                    databaseHelper.addKeyValue("prizeCounter",Integer.toString(prizeCounter));
                    if(winFlag){
                        winFlag = false;
                        int winRemainingAttempt = getIntValue(databaseHelper.getValue("winRemainingAttempt"));
                        winRemainingAttempt ++;
                        databaseHelper.addKeyValue("winRemainingAttempt",Integer.toString(winRemainingAttempt));
                    }
                }
            }
        });
    }

    private void checkForEndOfAttempts() {
        if(remainingAttemptFromTotalAttempt == 0){
            databaseHelper.dropTable();
            Intent mainActivityIntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(mainActivityIntent);
        }
    }

    private void getInitialProperties() {
        maxAttempts = getIntValue(databaseHelper.getValue("maxAttempts"));
        maxAttempts = maxAttempts==0 ? 1 : maxAttempts;

        remainingAttemptFromTotalAttempt = getIntValue(databaseHelper.getValue("remainingAttemptFromTotalAttempt"));

        String randomModPrizesString = databaseHelper.getValue("randomModPrizes");
        if(randomModPrizesString != null){
            randomModPrizesString = randomModPrizesString.replace("[","").replace("]","").trim();
            List<String> integerList = Arrays.asList(randomModPrizesString.split(","));
            for(String record : integerList){
                randomModPrizes.add(Integer.parseInt(record.trim()));
            }
        }

        prizeCounter = getIntValue(databaseHelper.getValue("prizeCounter"));
    }

    private int getIntValue(String value) {
        return value == null ? 0 : Integer.parseInt(value);
    }

    private int getRandomImageSource() {
        remainingAttemptFromTotalAttempt --;
        int bucket = remainingAttemptFromTotalAttempt / 10;
        String randomPrizeString = databaseHelper.getValue("bucket"+bucket);
        if(randomPrizeString == null){
            createPrizeList();
            prizeCounter = 0;
            databaseHelper.addKeyValue("prizeCounter",Integer.toString(prizeCounter));
            databaseHelper.addKeyValue("bucket"+bucket,randomPrizeList.toString());
        }else{
            randomPrizeString = randomPrizeString.replace("[","").replace("]","").trim();
            String[] strings = randomPrizeString.split(",");
            for(String record : strings){
                randomPrizeList.add(Integer.parseInt(record.trim()));
            }
        }

        if(prizeCounter >= per10Set){
            if(randomModPrizes.get(bucket).equals(R.drawable.win)){
                winFlag = true;
                randomModPrizes.add(bucket,R.drawable.lose);
                databaseHelper.addKeyValue("randomModPrizes",randomModPrizes.toString());
                return R.drawable.win;
            }else{
                return R.drawable.lose;
            }
        }else{
            int getLocation = remainingAttemptFromTotalAttempt %10;
            if(randomPrizeList.get(getLocation).equals(R.drawable.win)){
                prizeCounter++;
                winFlag = true;
                return R.drawable.win;
            }else{
               return R.drawable.lose;
            }
        }
    }

    private void createPrizeList() {
        if(randomPrizeList.size() ==0){
            for (int i=0; i< per10Set;i++){
                randomPrizeList.add(R.drawable.win);
            }
            for(int i=0; i < 10-per10Set;i++){
                randomPrizeList.add(R.drawable.lose);
            }
        }
        shuffleRandomPrizeList();
    }

    private void shuffleRandomPrizeList() {
        List<Integer> attempedNumberList = new ArrayList<>();
        List<Integer> newRandomPrizeList = new ArrayList<>();
        Random random = new Random();
        do{
            int randomInt = random.nextInt(10);
            if (!attempedNumberList.contains(randomInt)) {
                attempedNumberList.add(randomInt);
                newRandomPrizeList.add(randomPrizeList.get(randomInt));
            }
        }while (newRandomPrizeList.size() != randomPrizeList.size());
        randomPrizeList.clear();
        randomPrizeList.addAll(newRandomPrizeList);
    }

    private void getForModData() {
        int remainingMod = maxAttempts % 10;
        for (int i=0; i< remainingMod;i++){
            randomModPrizes.add(R.drawable.win);
        }
        for(int i=0; i < 10-remainingMod;i++){
            randomModPrizes.add(R.drawable.lose);
        }
        shuffleRandomModPrizeList();
    }

    private void shuffleRandomModPrizeList() {
        List<Integer> attempedNumberList = new ArrayList<>();
        List<Integer> newRandomModPrizeList = new ArrayList<>();
        Random random = new Random();
        do{
            int randomInt = random.nextInt(10);
            if (!attempedNumberList.contains(randomInt)) {
                attempedNumberList.add(randomInt);
                newRandomModPrizeList.add(randomModPrizes.get(randomInt));
            }
        }while (newRandomModPrizeList.size() != randomModPrizes.size());
        randomModPrizes.clear();
        randomModPrizes.addAll(newRandomModPrizeList);
        databaseHelper.addKeyValue("randomModPrizes",randomModPrizes.toString());
    }

    public void reset(View view){
        revealed = false;
        finish();
        Intent refresh = new Intent(this, ScratchActivity.class);
        startActivity(refresh);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.menuAppStatus:
                startActivity(new Intent(this, AppStatusActivity.class));
                return true;
            case R.id.menuContactUs:
                startActivity(new Intent(this, AboutUsActivity.class));
                return true;
            case R.id.menuShare:
                startActivity(new Intent(this, ShareActivity.class));
                return true;
            default:
                startActivity(new Intent(this, ScratchActivity.class));
                return true;
        }
    }

}
