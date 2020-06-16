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

public class FragmentA extends Fragment {

    TextView textView2;
    EditText etMessage;
    Button btnSend;

    OnActivityAMessageSendListener listener;

    Bundle argumemts;
    String message;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        argumemts = getArguments();
        message = argumemts.getString("message");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        textView2 = view.findViewById(R.id.textView2);
        etMessage = view.findViewById(R.id.etMessage);
        btnSend = view.findViewById(R.id.btnSend);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView2.setText(message);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMessage.getText() != null) {
                    if (listener != null) {
                        listener.onActivityAMessageSend(etMessage.getText().toString());
                    }
                }
            }
        });
    }

    public void setListener(OnActivityAMessageSendListener listener) {
        this.listener = listener;
    }

    public interface OnActivityAMessageSendListener {
        public void onActivityAMessageSend(String msg);
    }
}
