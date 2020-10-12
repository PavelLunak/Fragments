package cz.itnetwork.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnInsertFragment;
    Button btnCommunication;
    Button btnInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsertFragment = findViewById(R.id.btnInsertFragment);
        btnCommunication = findViewById(R.id.btnCommunication);
        btnInterface = findViewById(R.id.btnInterface);

        btnInsertFragment.setOnClickListener(this);
        btnCommunication.setOnClickListener(this);
        btnInterface.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btnInsertFragment:
                intent = new Intent(this, ActivityFirstFragment.class);
                startActivity(intent);
                break;
            case R.id.btnCommunication:
                intent = new Intent(this, ActivityCommunication.class);
                startActivity(intent);
                break;
            case R.id.btnInterface:
                intent = new Intent(this, ActivityInterface.class);
                startActivity(intent);
                break;
        }
    }
}
