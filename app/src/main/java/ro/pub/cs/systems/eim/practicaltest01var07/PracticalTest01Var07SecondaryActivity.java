package ro.pub.cs.systems.eim.practicaltest01var07;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var07SecondaryActivity extends AppCompatActivity {

    private TextView cell00, cell01, cell10, cell11;
    private Button sumButton, productButton;
    private String text00 = "";
    private String text01 = "";
    private String text10 = "";
    private String text11 = "";

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            int text00, text01, text10, text11;
            text00 = Integer.valueOf(cell00.getText().toString());
            text01 = Integer.valueOf(cell01.getText().toString());
            text10 = Integer.valueOf(cell10.getText().toString());
            text11 = Integer.valueOf(cell11.getText().toString());
            int sum = text00 + text01 + text10 + text11;
            int product = text00 * text01 * text10 * text11;
            switch (view.getId()) {
                case R.id.sum_button:
                    intent.putExtra(Constants.SUM, sum);
                    setResult(RESULT_OK, intent);
                    break;
                case R.id.product_button:
                    intent.putExtra(Constants.PRODUCT, product);
                    setResult(RESULT_OK, intent);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var07_secondary);

        cell00 = (TextView) findViewById(R.id.cell00);
        cell01 = (TextView) findViewById(R.id.cell01);
        cell10 = (TextView) findViewById(R.id.cell10);
        cell11 = (TextView) findViewById(R.id.cell11);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.CELL00)) {
            text00 = intent.getStringExtra(Constants.CELL00);
            cell00.setText(text00);
        }
        if (intent != null && intent.getExtras().containsKey(Constants.CELL01)) {
            text01 = intent.getStringExtra(Constants.CELL01);
            cell01.setText(text01);
        }
        if (intent != null && intent.getExtras().containsKey(Constants.CELL10)) {
            text10 = intent.getStringExtra(Constants.CELL10);
            cell10.setText(text10);
        }
        if (intent != null && intent.getExtras().containsKey(Constants.CELL11)) {
            text11 = intent.getStringExtra(Constants.CELL11);
            cell11.setText(text11);
        }

        sumButton = (Button) findViewById(R.id.sum_button);
        productButton = (Button) findViewById(R.id.product_button);

        sumButton.setOnClickListener(buttonClickListener);
        productButton.setOnClickListener(buttonClickListener);
    }
}