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

public class FragmentD extends Fragment {

    TextView textView2;
    EditText etMessage;
    Button btnSend;
    Button btnShowFragmentE;
    TextView labelMessageFromFragmentE;

    OnFragmentMessageSentListener listener;

    MainActivity activity;
    Bundle argumemts;
    String message;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (MainActivity) context;
        activity.setTitle(toString());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        argumemts = getArguments();
        message = argumemts.getString("message");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d, container, false);
        textView2 = view.findViewById(R.id.textView2);
        etMessage = view.findViewById(R.id.etMessage);
        btnSend = view.findViewById(R.id.btnSend);
        btnShowFragmentE = view.findViewById(R.id.btnShowFragmentE);
        labelMessageFromFragmentE = view.findViewById(R.id.labelMessageFromFragmentE);

        textView2.setText(message);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMessage.getText() != null) {
                    if (listener != null) {
                        //listener.onFragmentMessageSent(activity.FRAGMENT_D_NAME, etMessage.getText().toString());
                    }
                }
            }
        });

        btnShowFragmentE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString("message", "Tento fragment je otevírán z fragmentu D a je vložen do druhého kontejneru.");
                //activity.showFragment(activity.FRAGMENT_E_NAME, data);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //activity.updateFragmentsCount();
        //activity.updateTransactionsCount();
    }

    @Override
    public String toString() {
        return "Fragment D";
    }

    public void setListener(OnFragmentMessageSentListener listener) {
        this.listener = listener;
    }

    public void setMessageFromFragmentE(String message) {
        labelMessageFromFragmentE.setText(message);
    }
}
