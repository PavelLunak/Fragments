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

public class FragmentC extends Fragment {

    TextView textView2;
    EditText etMessage;
    Button btnSend;

    MainActivity activity;
    OnFragmentMessageSentListener listener;

    Bundle arguments;
    String message;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = (MainActivity) context;
        activity.setTitle(toString());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arguments = getArguments();
        message = arguments.getString("message");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.updateFragmentsCount();
        activity.updateTransactionsCount();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, container, false);
        textView2 = view.findViewById(R.id.textView2);
        etMessage = view.findViewById(R.id.etMessage);
        btnSend = view.findViewById(R.id.btnSend);

        textView2.setText(message);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMessage.getText() != null) {
                    if (listener != null) {
                        listener.onFragmentMessageSent(activity.FRAGMENT_C_NAME, etMessage.getText().toString());
                    }
                }
            }
        });

        return view;
    }

    @Override
    public String toString() {
        return "Fragment C";
    }

    public void setListener(OnFragmentMessageSentListener listener) {
        this.listener = listener;
    }
}
