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
import androidx.fragment.app.FragmentTransaction;

public class FragmentE extends Fragment implements View.OnClickListener, CustomDialogFragment.OnFruitSelectedListener {

    TextView textView2;
    EditText etMessageForActivity;
    EditText etMessageForFragmentD;
    Button btnSendToActivity;
    Button btnSendToFragment;
    Button btnShowDialog;
    Button btnShowDialog2;
    TextView labelMyFruit;

    OnFragmentMessageSentListener listener;

    MainActivity activity;
    Bundle argumemts;
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

        argumemts = getArguments();
        message = argumemts.getString("message");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_e, container, false);
        textView2 = view.findViewById(R.id.textView2);
        etMessageForActivity = view.findViewById(R.id.etMessageForActivity);
        etMessageForFragmentD = view.findViewById(R.id.etMessageForFragmentD);
        btnSendToActivity = view.findViewById(R.id.btnSendToActivity);
        btnSendToFragment = view.findViewById(R.id.btnSendToFragment);
        btnShowDialog = view.findViewById(R.id.btnShowDialog);
        btnShowDialog2 = view.findViewById(R.id.btnShowDialog2);
        labelMyFruit = view.findViewById(R.id.labelMyFruit);

        textView2.setText(message);

        btnSendToActivity.setOnClickListener(this);
        btnSendToFragment.setOnClickListener(this);
        btnShowDialog.setOnClickListener(this);
        btnShowDialog2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.updateFragmentsCount();
        activity.updateTransactionsCount();
    }

    public void setListener(OnFragmentMessageSentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSendToActivity:
                if (etMessageForActivity.getText() != null) {
                    if (listener != null) {
                        listener.onFragmentMessageSent(activity.FRAGMENT_E_NAME, etMessageForActivity.getText().toString());
                    }
                }
                break;
            case R.id.btnSendToFragment:
                if (etMessageForFragmentD.getText() != null) {
                    FragmentD fragmentD = (FragmentD) activity.getSupportFragmentManager().findFragmentByTag(activity.FRAGMENT_D_NAME);

                    if (fragmentD != null) {
                        fragmentD.setMessageFromFragmentE(etMessageForFragmentD.getText().toString());
                    }
                }
                break;
            case R.id.btnShowDialog:
                CustomDialogFragment dialog = new CustomDialogFragment();
                dialog.setTitle("Výběr ovoce");
                dialog.setMessage("Vyberte ovoce, které vám ze zobrazené nabídky chutná nejvíce");
                dialog.setItems(new String[] {
                        "Jablka",
                        "Hrušky",
                        "Švestky",
                        "Třešně",
                        "Banány",
                        "Pomeranče",
                        "Avokádo",
                        "Pomelo",
                        "Kiwi",
                        "Citrón",
                        "Blumy"});
                dialog.setListener(this);

                dialog.show(getActivity().getSupportFragmentManager(), "dialog");
                break;
            case R.id.btnShowDialog2:
                CustomDialogFragment dialog2 = new CustomDialogFragment();
                dialog2.setTitle("Výběr ovoce");
                dialog2.setMessage("Vyberte ovoce, které vám ze zobrazené nabídky chutná nejvíce");
                dialog2.setItems(new String[] {
                        "Jablka",
                        "Hrušky",
                        "Švestky",
                        "Třešně",
                        "Banány",
                        "Pomeranče",
                        "Avokádo",
                        "Pomelo",
                        "Kiwi",
                        "Citrón",
                        "Blumy"});
                dialog2.setListener(this);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(R.id.container2, dialog2);
                transaction.addToBackStack(null);
                transaction.commit();

                break;
        }
    }

    @Override
    public String toString() {
        return "Fragment E";
    }

    @Override
    public void onFruitSelected(String fruitName) {
        labelMyFruit.setText("Mám rád " + fruitName);
    }
}
