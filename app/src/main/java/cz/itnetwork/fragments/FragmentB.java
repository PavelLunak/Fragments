package cz.itnetwork.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentB extends Fragment {

    TextView textView2;
    EditText etMessage;
    Button btnSend;

    MainActivity activity;

    int testNumber = 0;


    // Tato metoda je volána při vložení fragmentu do aktivity.
    // Díky parametru context získáme referenci na aktivitu, ve které je fragment zobrazen
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity) {
            activity = (MainActivity) context;
            activity.setTitle(toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        textView2 = view.findViewById(R.id.textView2);
        etMessage = view.findViewById(R.id.etMessage);
        btnSend = view.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMessage.getText() != null) {
                    if (activity != null) {
                        // Nastavení zprávy z fragmentu B v aktivitě
                        //activity.setMessageB(etMessage.getText().toString());
                    }
                }
            }
        });

        if (activity != null) {
            //activity.updateFragmentsCount();
            //activity.updateTransactionsCount();
        }

        textView2.setText("" + testNumber);

        return view;
    }

    @Override
    public String toString() {
        return "Fragment B";
    }

    public void setMessage(String msg) {
        if (msg != null) {
            textView2.setText(msg);
        }
    }

    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }
}
