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

    TextView labelArgInt;
    TextView labelArgFloat;
    TextView labelArgString;
    TextView labelArgBoolean;

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
        labelArgInt = view.findViewById(R.id.labelArgInt);
        labelArgFloat = view.findViewById(R.id.labelArgFloat);
        labelArgString = view.findViewById(R.id.labelArgString);
        labelArgBoolean = view.findViewById(R.id.labelArgBoolean);

        args = getArguments();

        if (args != null) {
            message = args.getString("message", "???");

            labelArgInt.setText("int: " + args.getInt("int", -1));
            labelArgFloat.setText("float: " + args.getFloat("float", -1f));
            labelArgString.setText("String: " + args.getString("string", "???"));
            labelArgBoolean.setText("boolean: " + args.getBoolean("boolean", false));
        }

        label.setText(message);

        return view;
    }

    public void setMessage(String message) {
        this.message = message;
        label.setText(message);
    }
}
