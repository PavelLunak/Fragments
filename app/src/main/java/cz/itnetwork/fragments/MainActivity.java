package cz.itnetwork.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        FragmentManager.OnBackStackChangedListener,
        FragmentA.OnActivityAMessageSendListener,
        FragmentC.OnActivityCMessageSendListener,
        FragmentD.OnActivityDMessageSendListener,
        FragmentE.OnActivityEMessageSendListener {

    // Odkazy do resources na soubory s deklarací animací přechodu fragmentů
    final int animShowFragment = R.anim.anim_fragment_show;
    final int animHideFragment = R.anim.anim_fragment_hide;

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

    // Volba add, replace, remove, hide
    RadioGroup rgChoice;

    // Zaškrtnuto - ukládání fragmentů do zásobníku
    CheckBox chbAddToBackStack;

    // Zaškrtnuto - bude vytvořen nový fragment i v případě, již je jeho jiná instance v zásobníku
    CheckBox checkBoxNewInstance;

    // Zobrazení počtu transakcí v zásobníku
    TextView labelBackStackCount;

    // Zobrazení počtu fragmentů v zásobníku
    TextView labelFragmentsCount;

    public FragmentManager fragmentManager;

    // Počet transakcí v zásobníku
    int fragmentsInStack;

    // Počet fragmnetů v zásobníku
    int fragmentsCount;


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

        rgChoice = findViewById(R.id.rgChoice);

        chbAddToBackStack = findViewById(R.id.chbAddToBackStack);
        checkBoxNewInstance = findViewById(R.id.checkBoxNewInstance);

        labelBackStackCount = findViewById(R.id.labelBackStackCount);
        labelFragmentsCount = findViewById(R.id.labelFragmentsCount);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);

        fragmentsInStack = fragmentManager.getBackStackEntryCount();

        labelBackStackCount.setText("Zásobník transakcí: " + fragmentsInStack);
        labelFragmentsCount.setText("Počet fragmentů: " + fragmentsCount);

        rgChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbAdd :
                        chbAddToBackStack.setEnabled(true);
                        chbAddToBackStack.setChecked(true);
                        break;
                    case R.id.rbReplace :
                    case R.id.rbRemove :
                    case R.id.rbHide :
                        chbAddToBackStack.setChecked(false);
                        chbAddToBackStack.setEnabled(false);
                        break;
                }
            }
        });
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
        updateFragmentsCount();

        if (fragmentsCount == 0) finish();
        else super.onBackPressed();
    }

    // Sledování změn v zásobníku.
    @Override
    public void onBackStackChanged() {
        Log.d("lifecycle", "onBackStackChanged()");

        updateBackStackTransactionsCount();
        updateFragmentsCount();
        updateToolbarText();
    }

    public void updateBackStackTransactionsCount() {
        // Počet fragmentů (přesněji - transakcí) v zásobníku
        fragmentsInStack = fragmentManager.getBackStackEntryCount();

        // Aktualizace zobrazeného počtu transakcí
        labelBackStackCount.setText("Zásobník transakcí: " + fragmentsInStack);

        if (fragmentsInStack <= 0) return;

        Log.d("lifecycle", "-------------- BACK STACK --------------");
        for (int i = 0; i < fragmentsInStack; i ++) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
            if (entry != null) {
                if (fragmentManager.getBackStackEntryAt(i).getName() != null) {
                    Log.d("lifecycle", fragmentManager.getBackStackEntryAt(i).getName());
                }
            }
        }
    }

    public void updateFragmentsCount() {
        Log.d("lifecycle", "-------------- FRAGMENTS --------------");

        List<Fragment> fragments = fragmentManager.getFragments();

        if (fragments != null) {

            // Získání počtu fragmentů vložených do aktivity
            fragmentsCount = fragments.size();

            if (fragments.size() > 0) {
                for (Fragment fragment : fragments) {
                    Log.d("lifecycle", fragment.toString());
                }
            } else {
                Log.d("lifecycle", "fragments.size() == 0");
            }
        } else {
            Log.d("lifecycle", "fragments == null");
        }

        // Aktualizace zobrazeného počtu transakcí
        labelFragmentsCount.setText("Počet fragmentů: " + fragmentsCount);

        Log.d("lifecycle", "-----------------------------------------------------------");
    }

    // Nastavení textu toolbaru aplikace podle toho, zda je zobrazen některý fragment
    public void updateToolbarText() {

        // Získání seznamu fragmentů vložených do aktivity
        List<Fragment> fragments = fragmentManager.getFragments();

        if (fragments != null) {
            if (fragments.size() > 0) {
                setTitle(fragments.get(fragments.size() - 1).toString());
            } else {
                setTitle(getString(R.string.app_name));
            }
        } else {
            setTitle(getString(R.string.app_name));
        }

        /* Aktualizace toolbaru podle změn zásobníku
        if (fragmentsInStack > 0) {
            // Poslední transakce v zásobníku
            FragmentManager.BackStackEntry lastEntry = fragmentManager.getBackStackEntryAt(fragmentsInStack - 1);

            // Tag, pod kterým byla uložena poslední transakce
            String lastFragmentTag = lastEntry.getName();
            if (lastFragmentTag == null) return;

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
        */
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
    // name -> název fragmentu, který má být zobrazen
    // message -> textový řetězec, který bude fragmentu předán a ve fragmentu zobrazen
    public void showFragment(String name, String message) {

        Fragment fragment = null;

        if (checkBoxNewInstance.isChecked()) {
            performNewTransaction(fragment, name, message);
        } else {
            if (chbAddToBackStack.isChecked()) {
                fragment = fragmentManager.findFragmentByTag(name);
            }

            if (fragment == null) {
                performNewTransaction(fragment, name, message);
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

    public void performNewTransaction(Fragment fragment, String name, String message) {
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
            switch (rgChoice.getCheckedRadioButtonId()) {
                case R.id.rbAdd :
                    fragmentTransaction.add(R.id.container, fragment, name);
                    break;
                case R.id.rbReplace :
                    /*
                    Nahrazení existujícího fragmentu, který byl přidán do kontejneru. To je v podstatě
                    stejné jako volání metody remove() pro všechny aktuálně přidané fragmenty,
                    které byly přidány do stejného kontejneru.
                    */
                    fragmentTransaction.replace(R.id.container, fragment, name);
                    break;
                case R.id.rbRemove :
                    /*
                    Odstraní existující fragment. Pokud byl dříve přidán do kontejneru, bude také odstraněn
                    z tohoto kontejneru.
                    */
                    //fragmentTransaction.remove(fragment);
                    Fragment fragmentToRemove = fragmentManager.findFragmentByTag(name);

                    if (fragmentToRemove != null) fragmentTransaction.remove(fragmentToRemove);
                    else Toast.makeText(this, "Fragment neexistuje", Toast.LENGTH_LONG).show();

                    break;
                case R.id.rbHide :
                    /*
                    Skryje (nebo zobrazí skrytý) existující fragment, který byl dříve přidán do kontejneru.
                    */
                    Fragment fragmentToHide = fragmentManager.findFragmentByTag(name);
                    if (fragmentToHide != null) {
                        if (fragmentToHide.isHidden()) {
                            fragmentTransaction.show(fragmentToHide);
                        } else {
                            fragmentTransaction.hide(fragmentToHide);
                        }
                    } else {
                        Toast.makeText(this, "Fragment neexistuje", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }

        // Parametr "name" opět slouží k pozdějšímu vyhledání fragmentu
        if (chbAddToBackStack.isChecked()) fragmentTransaction.addToBackStack(name);

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
