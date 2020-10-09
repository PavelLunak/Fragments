package cz.itnetwork.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {

    TextView label;

    Bundle args;
    String message = "TextView";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        label = view.findViewById(R.id.label);

        args = getArguments();

        if (args != null) {
            message = args.getString("message", "???");
        }

        label.setText(message);

        return view;
    }

    public void setMessage(String message) {
        this.message = message;
        label.setText(message);
    }
}
