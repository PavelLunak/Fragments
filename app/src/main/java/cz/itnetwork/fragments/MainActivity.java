package cz.itnetwork.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        FragmentManager.OnBackStackChangedListener,
        FragmentA.OnActivityAMessageSendListener,
        FragmentC.OnActivityCMessageSendListener,
        FragmentD.OnActivityDMessageSendListener,
        FragmentE.OnActivityEMessageSendListener {

    // Odkazy do resources na soubory s deklarací animací přechodu fragmentů
    int animShowFragment = R.anim.anim_fragment_show;
    int animHideFragment = R.anim.anim_fragment_hide;

    // Konstanty s názvy fragmenrů
    final String FRAGMENT_A_NAME = "FragmentA";
    final String FRAGMENT_B_NAME = "FragmentB";
    final String FRAGMENT_C_NAME = "FragmentC";
    final String FRAGMENT_D_NAME = "FragmentD";
    final String FRAGMENT_E_NAME = "FragmentE";

    // Zobrazená zpráv z jednotlivých fragmentů
    TextView labelMessageA;
    TextView labelMessageB;
    TextView labelMessageC;
    TextView labelMessageD;

    Button btnA;
    Button btnB;
    Button btnC;
    Button btnD;
    Button btnSendToFragmentB;

    // Zaškrtnuto - ukládání fragmentů do zásobníku
    CheckBox chbRememberFragments;

    // Zaškrtnuto - bude vytvořen nový fragment i v případě, již je jeho jiná instance v zásobníku
    CheckBox checkBoxNewInstance;

    // Zobrazení počtu fragmentů v zásobníku
    TextView labelBackStackCount;

    public FragmentManager fragmentManager;

    // Počet fragmentů v zásobníku
    int fragmentsInStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labelMessageA = findViewById(R.id.labelMessageA);
        labelMessageB = findViewById(R.id.labelMessageB);
        labelMessageC = findViewById(R.id.labelMessageC);
        labelMessageD = findViewById(R.id.labelMessageD);

        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);
        btnSendToFragmentB = findViewById(R.id.btnSendToFragmentB);

        chbRememberFragments = findViewById(R.id.chbRememberFragments);
        checkBoxNewInstance = findViewById(R.id.checkBoxNewInstance);

        labelBackStackCount = findViewById(R.id.labelBackStackCount);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);

        fragmentsInStack = fragmentManager.getBackStackEntryCount();
        labelBackStackCount.setText("Fragments in stack: " + fragmentsInStack);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Aktualizace textu v toolbaru aplikace po otočení zařízení
        // nebo jiné změně stavu aplikace
        updateToolbarText();
    }

    // Stisknutí tlačítka ZPĚT na zařízení
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Sledování změn v zásobníku.
    @Override
    public void onBackStackChanged() {
        // Počet fragmentů (přesněji - transakcí) v zásobníku
        fragmentsInStack = fragmentManager.getBackStackEntryCount();

        // Aktualizace zobrazeného počtu transakcí
        labelBackStackCount.setText("Fragments in stack: " + fragmentsInStack);
        updateToolbarText();
    }

    // Nastavení textu toolbaru aplikace podle toho, zda je zobrazen některý fragment
    public void updateToolbarText() {
        if (fragmentsInStack > 0) {
            // Poslední transakce v zásobníku
            FragmentManager.BackStackEntry lastEntry = fragmentManager.getBackStackEntryAt(fragmentsInStack - 1);

            // Tag, pod kterým byla uložena poslední transakce
            String lastFragmentTag = lastEntry.getName();

            if (lastFragmentTag.equals(FRAGMENT_A_NAME)) {
                setTitle("Fragment A");
            } else if (lastFragmentTag.equals(FRAGMENT_B_NAME)) {
                setTitle("Fragment B");
            } else if (lastFragmentTag.equals(FRAGMENT_C_NAME)) {
                setTitle("Fragment C");
            } else if (lastFragmentTag.equals(FRAGMENT_D_NAME)) {
                setTitle("Fragment D");
            } else if (lastFragmentTag.equals(FRAGMENT_E_NAME)) {
                setTitle("Fragment E");
            }
        } else {
            setTitle(getString(R.string.app_name));
        }
    }

    // Kliknutí na jedno z tlačítek
    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.btnA:
                showFragment(FRAGMENT_A_NAME, "Text pro fragment A");
                break;
            case R.id.btnB:
                showFragment(FRAGMENT_B_NAME, null);
                break;
            case R.id.btnC:
                showFragment(FRAGMENT_C_NAME, "Text pro fragment C");
                break;
            case R.id.btnD:
                showFragment(FRAGMENT_D_NAME, "Text pro fragment D");
                break;
            case R.id.btnSendToFragmentB:
                // Vyhledání fragment, který byl identifikován tagem "FragmentB" při jeho zobrazování
                // transakcí. Nejprve je hledáno mezi těmi fragmenty, které byly přidány do aktivity.
                // Pokud není takový fragment nalezen, bude prohledán zásobník.
                FragmentB fragmentB = (FragmentB) fragmentManager.findFragmentByTag(FRAGMENT_B_NAME);

                // Text bude fragmentu nastaven i v případě, že zrovna nebude viditelný - stačí aby
                // byl v zásobníku.

                if (fragmentB != null) {
                    fragmentB.setMessage("Dodatečně odeslaný text z hlavní aktivity.");
                } else {
                    Toast.makeText(
                            this,
                            "Fragment B nebyl nalezen...",
                            Toast.LENGTH_LONG)
                            .show();
                }

                break;
        }
    }

    // Požadavek na zobrazení fragnmentu
    // name -> název fragmentu
    // message -> textový řetězec, který bude fragmentu předán a ve fragmentu zobrazen
    public void showFragment(String name, String message) {

        Fragment fragment = null;

        if (checkBoxNewInstance.isChecked()) {
            addFragment(fragment, name, message);
        } else {
            if (chbRememberFragments.isChecked()) {
                fragment = fragmentManager.findFragmentByTag(name);
            }

            if (fragment == null) {
                addFragment(fragment, name, message);
            } else {
                restoreFragment(name);
            }
        }
    }

    // Vytvoření fragmentu
    public Fragment createFragment(String name) {
        if (name.equals(FRAGMENT_A_NAME)) {
            FragmentA newFragment = new FragmentA();
            newFragment.setListener(this);
            return newFragment;
        } else if (name.equals(FRAGMENT_B_NAME)) {
            FragmentB newFragment = new FragmentB();
            return newFragment;
        } else if (name.equals(FRAGMENT_C_NAME)) {
            FragmentC newFragment = new FragmentC();
            newFragment.setListener(this);
            return newFragment;
        } else if (name.equals(FRAGMENT_D_NAME)) {
            FragmentD newFragment = new FragmentD();
            newFragment.setListener(this);
            return newFragment;
        } else {
            FragmentE newFragment = new FragmentE();
            newFragment.setListener(this);
            return newFragment;
        }
    }

    // Zobrazení fragmentu uživateli
    public void addFragment(Fragment fragment, String name, String message) {
        if (fragment == null) {
            fragment = createFragment(name);
        }

        Bundle args = new Bundle();
        args.putString("message", message);
        fragment.setArguments(args);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(animShowFragment, animHideFragment, animShowFragment, animHideFragment);

        // Pokud zobrazujeme fragment E, bude vložen do kontejneru, který fragment zobrazí
        // v celém dostupném prostoru displeje. V opačném případě bude použit kontejner s omezenou velikostí
        if (name.equals(FRAGMENT_E_NAME)) {
            // Přidání fragmentu pod tagem "name", díky kterému budeme moci později tento přidný fragment
            // vyhledat pomocí metody FragmentManager.findFragmentByTag(name);
            fragmentTransaction.add(R.id.container2, fragment, name);

        } else {
            if (chbRememberFragments.isChecked()) {
                fragmentTransaction.add(R.id.container, fragment, name);
            } else {
                fragmentTransaction.replace(R.id.container, fragment, name);
            }

        }

        // Parametr "name" opět slouží k pozdějšímu vyhledání fragmentu
        if (chbRememberFragments.isChecked()) fragmentTransaction.addToBackStack(name);

        fragmentTransaction.commit();
    }

    // Byl-li již fragment se jménem "name" vytvořen a je v zásobníku, bude tento fragment zobrazen
    public void restoreFragment(String name) {
        int beCount = fragmentManager.getBackStackEntryCount();
        if (beCount == 0) return;
        fragmentManager.popBackStack(name,0);
    }

    public void setMessageB(String messageB) {
        labelMessageB.setText(messageB);
    }

    @Override
    public void onActivityAMessageSend(String msg) {
        labelMessageA.setText(msg);
    }

    // Fragment B nemá listener. Zprávu z fragmentu B zde nastavujeme tak, že z fragmentu B voláme
    // metodu setMessageB(), deklarovanou zde v aktivitě.

    @Override
    public void onActivityCMessageSend(String msg) {
        labelMessageC.setText(msg);
    }

    @Override
    public void onActivityDMessageSend(String msg) {
        labelMessageD.setText(msg);
    }

    @Override
    public void onActivityEMessageSend(String msg) {

    }
}
