package ua.edu.kneu.kneujornal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    static final String ACTION_MAIN_RECEIVER = "ACTION_MAIN_RECEIVER";
    LocalBroadcastManager bManager;
    dataBaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDbHelper = new dataBaseHelper(this);

        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            myDbHelper.openDataBase();
        }catch(SQLiteException sqle){
            throw sqle;
        }
        Cursor myCursor = myDbHelper.onrawquary("SELECT * FROM main;");
        String name, mark, maxMark;
        int i=1;
        while(myCursor.moveToNext()) {
            name = myCursor.getString(2);
            mark = myCursor.getString(3);
            maxMark = myCursor.getString(4);
            String temp = mark + "|" + mark;
            switch (i) {
                case 1:
                    TextView textView1 = findViewById(R.id.text1);
                    textView1.setText(name.toCharArray(), 0, name.length());
                    TextView textView11 = findViewById(R.id.text11);
                    textView11.setText(temp.toCharArray(), 0, temp.length());
                    break;
                case 2:
                    TextView textView2 = findViewById(R.id.text2);
                    textView2.setText(name.toCharArray(), 0, name.length());
                    TextView textView22 = findViewById(R.id.text22);
                    textView22.setText(temp.toCharArray(), 0, temp.length());
                    break;
                case 3:
                    TextView textView3 = findViewById(R.id.text3);
                    textView3.setText(name.toCharArray(), 0, name.length());
                    TextView textView33 = findViewById(R.id.text33);
                    textView33.setText(temp.toCharArray(), 0, temp.length());
                    break;
                case 4:
                    TextView textView4 = findViewById(R.id.text4);
                    textView4.setText(name.toCharArray(), 0, name.length());
                    TextView textView44 = findViewById(R.id.text44);
                    textView44.setText(temp.toCharArray(), 0, temp.length());
                    break;
                case 5:
                    TextView textView5 = findViewById(R.id.text5);
                    textView5.setText(name.toCharArray(), 0, name.length());
                    TextView textView55 = findViewById(R.id.text55);
                    textView55.setText(temp.toCharArray(), 0, temp.length());
                    break;
                case 6:
                    TextView textView6 = findViewById(R.id.text6);
                    textView6.setText(name.toCharArray(), 0, name.length());
                    TextView textView66 = findViewById(R.id.text66);
                    textView66.setText(temp.toCharArray(), 0, temp.length());
                    break;
                case 7:
                    TextView textView7 = findViewById(R.id.text7);
                    textView7.setText(name.toCharArray(), 0, name.length());
                    TextView textView77 = findViewById(R.id.text77);
                    textView77.setText(temp.toCharArray(), 0, temp.length());
                    break;
            }
            i++;
        }

        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_MAIN_RECEIVER);
        bManager.registerReceiver(bReceiver,intentFilter);

        startService(new Intent(this,CommunicationJobService.class).putExtra("action","chek_login"));

    }

    @Override
    protected void onDestroy() {
        bManager.unregisterReceiver(bReceiver);
        super.onDestroy();
    }

    public void rowClick(View view) {
        startActivity(new Intent(MainActivity.this,SubjInfoActivity.class).putExtra("subj",
                ((TextView)((TableRow)view).getVirtualChildAt(0)).getText()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_settings: {
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                break;
            }
            case R.id.action_sign_out: {
                new AlertDialog.Builder(MainActivity.this).setTitle("Выход")
                        .setMessage("Вы уверены, что хотите выйти?").setPositiveButton("Нет", null)
                        .setNegativeButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        startService(new Intent(MainActivity.this,CommunicationJobService.class)
                                .putExtra("action","sign_out"));
                    }
                }).show();
                break;
            }
        }
        return false;
    }

    private BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null) {
                switch (intent.getStringExtra("action")) {
                    case "no_login":
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        break;

                    case "subjects":
                        /*ContentValues row1 = new ContentValues();
                        String[] st = new String[5];
                        int r_count = intent.getIntExtra("count",0);
                        for (int i=0;i<r_count;i++){
                            st = intent.getStringArrayExtra(Integer.toString(i));
                            row1.put("_id", st[0]);
                            row1.put("teacherInfo", st[1]);
                            row1.put("nazva", st[2]);
                            row1.put("mark", st[3]);
                            row1.put("maxMark", st[4]);
                        }
                        myDbHelper.inset1("main", row1);
                        break;*/
                }

            }
        }
    };
}


