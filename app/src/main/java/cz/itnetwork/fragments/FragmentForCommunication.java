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

public class FragmentForCommunication extends Fragment {

    EditText etMessage;
    Button btnSend;
    TextView labelMessageFromActivity;

    ActivityCommunication activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof ActivityCommunication) {
            this.activity = (ActivityCommunication) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_communication, container, false);

        etMessage = view.findViewById(R.id.etMessage);
        btnSend = view.findViewById(R.id.btnSend);
        labelMessageFromActivity = view.findViewById(R.id.labelMessageFromActivity);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMessage.getText() != null) {
                    if (!etMessage.getText().toString().equals("")) {
                        sendMessageToActivity(etMessage.getText().toString());
                    }
                }
            }
        });

        return view;
    }

    private void sendMessageToActivity(String msg) {
        if (activity != null) {
            activity.setMessageFromFragment(msg);
        }
    }

    public void setMessageFromActivity(String msg) {
        this.labelMessageFromActivity.setText(msg);
    }
}
