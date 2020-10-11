package cz.itnetwork.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityFirstFragment extends AppCompatActivity {

    Button btnShowFirstFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_fragment);

        btnShowFirstFragment = findViewById(R.id.btnShowFirstFragment);

        btnShowFirstFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFirstFragment();
            }
        });
    }

    private void showFirstFragment() {
        Bundle args = new Bundle();
        args.putString("message", "Toto je náš první fragment :-)");
        args.putInt("int", 6156);
        args.putFloat("float", 23.789f);
        args.putString("string", "Textový řetězec");
        args.putBoolean("boolean", true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FirstFragment firstFragment = new FirstFragment();
        firstFragment.setArguments(args);
        fragmentTransaction.add(R.id.containerForFirstFragment, firstFragment);
        fragmentTransaction.commit();
    }
}