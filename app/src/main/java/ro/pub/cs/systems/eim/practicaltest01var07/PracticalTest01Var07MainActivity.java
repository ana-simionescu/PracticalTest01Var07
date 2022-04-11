package ro.pub.cs.systems.eim.practicaltest01var07;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var07MainActivity extends AppCompatActivity {

    private EditText cell00, cell01, cell10, cell11;
    private Button setButton;

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private int serviceStatus = Constants.SERVICE_STOPPED;
    private IntentFilter intentFilter = new IntentFilter();
    private Intent lastIntent= null;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            cell00 = (EditText) findViewById(R.id.cell00);
            cell01 = (EditText) findViewById(R.id.cell01);
            cell10 = (EditText) findViewById(R.id.cell10);
            cell11 = (EditText) findViewById(R.id.cell11);
            cell00.setText(intent.getIntExtra(Constants.CELL00, -1));
            cell01.setText(intent.getIntExtra(Constants.CELL01, -1));
            cell10.setText(intent.getIntExtra(Constants.CELL10, -1));
            cell11.setText(intent.getIntExtra(Constants.CELL11, -1));
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String text00, text01, text10, text11;
            text00 = cell00.getText().toString();
            text01 = cell01.getText().toString();
            text10 = cell10.getText().toString();
            text11 = cell11.getText().toString();

            switch (view.getId()) {
                case R.id.set_button:
                    if (isNumeric(text00) && isNumeric(text01)
                            && isNumeric(text10) && isNumeric(text11)) {
                        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var07SecondaryActivity.class);
                        intent.putExtra(Constants.CELL00, text00);
                        intent.putExtra(Constants.CELL01, text01);
                        intent.putExtra(Constants.CELL10, text10);
                        intent.putExtra(Constants.CELL11, text11);
                        startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    }
                    if (serviceStatus == Constants.SERVICE_STOPPED) {
                        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var07Service.class);
                        getApplicationContext().startService(intent);
                    }
                    break;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var07_main);

        intentFilter.addAction(Constants.ACTION_TYPE);

        cell00 = (EditText) findViewById(R.id.cell00);
        cell01 = (EditText) findViewById(R.id.cell01);
        cell10 = (EditText) findViewById(R.id.cell10);
        cell11 = (EditText) findViewById(R.id.cell11);

        setButton = (Button) findViewById(R.id.set_button);
        setButton.setOnClickListener(buttonClickListener);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.CELL00)) {
                cell00.setText(savedInstanceState.getString(Constants.CELL00));
            } else {
                cell00.setText("");
            }
            if (savedInstanceState.containsKey(Constants.CELL01)) {
                cell01.setText(savedInstanceState.getString(Constants.CELL01));
            } else {
                cell01.setText("");
            }
            if (savedInstanceState.containsKey(Constants.CELL10)) {
                cell10.setText(savedInstanceState.getString(Constants.CELL10));
            } else {
                cell10.setText("");
            }
            if (savedInstanceState.containsKey(Constants.CELL11)) {
                cell11.setText(savedInstanceState.getString(Constants.CELL11));
            } else {
                cell11.setText("");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(Constants.CELL00, cell00.getText().toString());
        savedInstanceState.putString(Constants.CELL01, cell01.getText().toString());
        savedInstanceState.putString(Constants.CELL10, cell10.getText().toString());
        savedInstanceState.putString(Constants.CELL11, cell11.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.CELL00)) {
            cell00.setText(savedInstanceState.getString(Constants.CELL00));
        } else {
            cell00.setText("");
        }
        if (savedInstanceState.containsKey(Constants.CELL01)) {
            cell01.setText(savedInstanceState.getString(Constants.CELL01));
        } else {
            cell01.setText("");
        }
        if (savedInstanceState.containsKey(Constants.CELL10)) {
            cell10.setText(savedInstanceState.getString(Constants.CELL10));
        } else {
            cell10.setText("");
        }
        if (savedInstanceState.containsKey(Constants.CELL11)) {
            cell11.setText(savedInstanceState.getString(Constants.CELL11));
        } else {
            cell11.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            if (intent != null && intent.getExtras().containsKey(Constants.SUM)) {
                int sum = intent.getIntExtra(Constants.SUM, -1);
                Toast.makeText(this, "The SUM is " + sum, Toast.LENGTH_LONG).show();
            }
            if (intent != null && intent.getExtras().containsKey(Constants.PRODUCT)) {
                int product = intent.getIntExtra(Constants.PRODUCT, -1);
                Toast.makeText(this, "The PRODUCT is " + product, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var07Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}