package cz.itnetwork.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {

    TextView labelHeader;
    TextView labelMessage;
    ListView listView;

    String textHeader = "";
    String textMessage = "";
    String[] items;

    OnFruitSelectedListener listener;


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("textHeader", textHeader);
        outState.putString("textMessage", textMessage);
        outState.putStringArray("items", items);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            textHeader = savedInstanceState.getString("textHeader", "");
            textMessage = savedInstanceState.getString("textMessage", "");
            items = savedInstanceState.getStringArray("items");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog_fragment, container, false);

        labelHeader = view.findViewById(R.id.labelHeader);
        labelMessage = view.findViewById(R.id.labelMessage);
        listView = view.findViewById(R.id.listView);

        labelHeader.setText(textHeader);
        labelMessage.setText(textMessage);

        if (items != null) {
            if (items.length > 0) {
                listView.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.list_item, items));
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    String itemText = ((TextView) view).getText().toString();
                    if (itemText.equals("Nic mi nechutná, zavřít...")) {
                        dismiss();
                    } else {
                        listener.onFruitSelected(itemText);
                    }
                }

                dismiss();
            }
        });

        return view;
    }

    // Tato metoda je volána pouze v případě, že bude tento objekt zobrazen jako dialog
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    public void setTitle(String textHeader) {
        this.textHeader = textHeader;
    }

    public void setMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public void setListener(OnFruitSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnFruitSelectedListener {
        void onFruitSelected(String fruitName);
    }
}
