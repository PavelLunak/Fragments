package cz.itnetwork.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class FragmentA extends Fragment {

    TextView labelNumber1, labelNumber2;
    Button btnSend;

    MainActivity activity;
    OnResultCalculatedListener listener;

    Bundle arguments;

    int number1;
    int number2;
    int result;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = (MainActivity) context;
        listener = (OnResultCalculatedListener) context;
        activity.setTitle(toString());

        arguments = getArguments();
        number1 = arguments.getInt("number1");
        number2 = arguments.getInt("number2");
        result = number1 + number2;

        if (context instanceof MainActivity) {

        } else if (context instanceof MainActivity) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        labelNumber1 = view.findViewById(R.id.labelNumber1);
        labelNumber2 = view.findViewById(R.id.labelNumber2);
        btnSend = view.findViewById(R.id.btnSend);

        labelNumber1.setText("" + number1);
        labelNumber2.setText("" + number2);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onResultCalculated(number1, number2, result);
            }
        });

        activity.updateFragmentsCount();
        activity.updateTransactionsCount();

        return view;
    }

    @Override
    public String toString() {
        return "Fragment A";
    }

    public interface OnResultCalculatedListener {
        void onResultCalculated(int number1, int number2, int result);
    }
}
