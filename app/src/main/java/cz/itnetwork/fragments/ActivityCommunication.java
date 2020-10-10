package cz.itnetwork.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityCommunication extends AppCompatActivity {

    EditText etMessage;
    Button btnAddFragment;
    Button btnSend;
    TextView labelMessageFromFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);

        etMessage = findViewById(R.id.etMessage);
        btnAddFragment = findViewById(R.id.btnAddFragment);
        btnSend = findViewById(R.id.btnSend);
        labelMessageFromFragment = findViewById(R.id.labelMessageFromFragment);

        btnAddFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMessage.getText() != null) {
                    if (!etMessage.getText().toString().equals("")) {
                        sendMessageToFragment(etMessage.getText().toString());
                    }
                }
            }
        });
    }

    private void showFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentForCommunication fragment = new FragmentForCommunication();
        fragmentTransaction.add(R.id.containerForCommunication, fragment, FragmentForCommunication.class.getName());
        fragmentTransaction.commit();
    }

    private void sendMessageToFragment(String msg) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentForCommunication fragment = (FragmentForCommunication) fm.findFragmentByTag(FragmentForCommunication.class.getName());

        if (fragment != null) {
            fragment.setMessageFromActivity(msg);
        }
    }

    public void setMessageFromFragment(String msg) {
        labelMessageFromFragment.setText(msg);
    }
}