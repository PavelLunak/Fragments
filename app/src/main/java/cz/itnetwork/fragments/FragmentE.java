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

public class FragmentE extends Fragment implements View.OnClickListener {

    TextView textView2;
    EditText etMessageForActivity;
    EditText etMessageForFragment;
    Button btnSendToActivity;
    Button btnSendToFragment;

    OnActivityEMessageSendListener listener;

    MainActivity activity;
    Bundle argumemts;
    String message;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity) {
            activity = (MainActivity) context;
        }
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
        View view = inflater.inflate(R.layout.fragment_e, container, false);
        textView2 = view.findViewById(R.id.textView2);
        etMessageForActivity = view.findViewById(R.id.etMessageForActivity);
        etMessageForFragment = view.findViewById(R.id.etMessageForFragment);
        btnSendToActivity = view.findViewById(R.id.btnSendToActivity);
        btnSendToFragment = view.findViewById(R.id.btnSendToFragment);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView2.setText(message);

        btnSendToActivity.setOnClickListener(this);
        btnSendToFragment.setOnClickListener(this);
    }

    public void setListener(OnActivityEMessageSendListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSendToActivity:
                if (etMessageForActivity.getText() != null) {
                    if (listener != null) {
                        listener.onActivityEMessageSend(etMessageForActivity.getText().toString());
                    }
                }
                break;
            case R.id.btnSendToFragment:
                if (etMessageForFragment.getText() != null) {

                }
                break;
        }
    }

    public interface OnActivityEMessageSendListener {
        public void onActivityEMessageSend(String msg);
    }
}
