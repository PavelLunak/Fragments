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

    OnActivityDMessageSendListener listener;

    MainActivity activity;
    Bundle argumemts;
    String message;

    @Override
    public void onAttach(Context context) {
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
        View view = inflater.inflate(R.layout.fragment_d, container, false);
        textView2 = view.findViewById(R.id.textView2);
        etMessage = view.findViewById(R.id.etMessage);
        btnSend = view.findViewById(R.id.btnSend);
        btnShowFragmentE = view.findViewById(R.id.btnShowFragmentE);
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
                        listener.onActivityDMessageSend(etMessage.getText().toString());
                    }
                }
            }
        });

        btnShowFragmentE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showFragment(activity.FRAGMENT_E_NAME, "Tento fragment je otevírán z fragmentu D a je vložen do druhého kontejneru.");
            }
        });
    }

    @Override
    public String toString() {
        return "Fragment D";
    }

    public void setListener(OnActivityDMessageSendListener listener) {
        this.listener = listener;
    }

    public interface OnActivityDMessageSendListener {
        public void onActivityDMessageSend(String msg);
    }
}
