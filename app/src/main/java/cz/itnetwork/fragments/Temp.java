package cz.itnetwork.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;
import java.util.Random;

public class Temp extends AppCompatActivity implements
        FragmentManager.OnBackStackChangedListener,
        FragmentA.OnResultCalculatedListener,
        OnFragmentMessageSentListener {

    // Odkazy do resources na soubory s deklarací animací přechodu fragmentů
    final int animShowFragment = R.anim.anim_fragment_show;
    final int animHideFragment = R.anim.anim_fragment_hide;
    final int animPopHideFragment = R.anim.anim_fragment_pop_hide;
    final int animPopShowFragment = R.anim.anim_fragment_pop_show;

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
    TextView labelMessageE;

    Button btnA;
    Button btnB;
    Button btnC;
    Button btnD;
    Button btnSendToFragmentB;

    CheckBox chbAddToBackStack;
    CheckBox chbNewInstance;

    // Zobrazení počtu vložených fragmentů
    TextView labelFragmentsCount;

    // Zobrazení počtu transakcí v zásobníku
    TextView labelTransactionsCount;

    public FragmentManager fragmentManager;

    // Počet transakcí v zásobníku
    int fragmentsInStack;

    // Počet vložených fragmnetů
    int fragmentsCount;

    // Počet transakcí v zásobníku
    int transactions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labelMessageA = findViewById(R.id.labelMessageA);
        labelMessageB = findViewById(R.id.labelMessageB);
        labelMessageC = findViewById(R.id.labelMessageC);
        labelMessageD = findViewById(R.id.labelMessageD);
        labelMessageE = findViewById(R.id.labelMessageE);

        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);
        btnSendToFragmentB = findViewById(R.id.btnSendToFragmentB);
        chbAddToBackStack = findViewById(R.id.chbAddToBackStack);
        chbNewInstance = findViewById(R.id.chbNewInstance);
        labelFragmentsCount = findViewById(R.id.labelFragmentsCount);
        labelTransactionsCount = findViewById(R.id.labelTransactionsCount);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);

        updateFragmentsCount();
        updateTransactionsCount();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Aktualizace textu v toolbaru aplikace po otočení zařízení nebo jiné změně stavu aplikace
        updateToolbarText();
    }

    /*
    // Stisknutí tlačítka ZPĚT na zařízení
    @Override
    public void onBackPressed() {
        if (fragmentsCount == 0 || transactions == 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setTitle("Konec");

            alertDialogBuilder
                    .setMessage("Opravdu ukončit aplikaci?")
                    .setCancelable(false)
                    .setPositiveButton("Ano", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            super.onBackPressed();
        }
    }
     */

    // Sledování změn v zásobníku.
    @Override
    public void onBackStackChanged() {
        updateFragmentsCount();
        updateTransactionsCount();
        updateToolbarText();
    }

    public void updateFragmentsCount() {
        List<Fragment> fragments = fragmentManager.getFragments();

        if (fragments != null) {
            // Získání počtu fragmentů vložených do aktivity
            fragmentsCount = fragments.size();
        }

        // Aktualizace zobrazeného počtu transakcí
        labelFragmentsCount.setText("Počet fragmentů: " + fragmentsCount);

        chbAddToBackStack.setEnabled(fragmentsCount == 0);
    }

    public void updateTransactionsCount() {
        transactions = fragmentManager.getBackStackEntryCount();
        labelTransactionsCount.setText("Počet transakcí: " + transactions);
    }

    // Nastavení textu toolbaru aplikace podle toho, zda je zobrazen některý fragment
    public void updateToolbarText() {

        // Získání seznamu fragmentů vložených do aktivity
        List<Fragment> fragments = fragmentManager.getFragments();

        if (fragments != null) {
            if (fragments.size() > 0) {
                if (fragments.get(fragments.size() - 1) instanceof CustomDialogFragment) {
                    return;
                }

                setTitle(fragments.get(fragments.size() - 1).toString());
            } else {
                setTitle(getString(R.string.app_name));
            }
        } else {
            setTitle(getString(R.string.app_name));
        }
    }

    // Kliknutí na jedno z tlačítek
    public void buttonClick(View view) {

        Bundle data = new Bundle();

        switch (view.getId()) {
            case R.id.btnA:
                int min = 1;
                int max = 100;

                Random random = new Random();
                int number1 = random.nextInt(max - min + 1) + min;
                int number2 = random.nextInt(max - min + 1) + min;

                data.putInt("number1", number1);
                data.putInt("number2", number2);

                labelMessageA.setText("" + number1 + " + " + number2 + " =");
                showFragment(FRAGMENT_A_NAME, data);
                break;
            case R.id.btnB:
                showFragment(FRAGMENT_B_NAME, null);
                break;
            case R.id.btnC:
                data.putString("message", "Zpráva z aktivity pro fragment C");
                showFragment(FRAGMENT_C_NAME, data);
                break;
            case R.id.btnD:
                data.putString("message", "Zpráva z aktivity pro fragment D");
                showFragment(FRAGMENT_D_NAME, data);
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
                    Toast.makeText(this, "Fragment B nebyl nalezen...", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    // Požadavek na zobrazení fragnmentu
    // name -> název fragmentu, který má být zobrazen
    // message -> textový řetězec, který bude fragmentu předán a ve fragmentu zobrazen
    public void showFragment(String name, Bundle data) {

        Fragment fragment = null;

        if (!chbNewInstance.isChecked()) {
            fragment = fragmentManager.findFragmentByTag(name);
        }

        addFragment(fragment, name, data);
        /*
        if (fragment == null) {
            addFragment(fragment, name, data);
        } else {
            restoreFragment(name);
        }
         */

        updateToolbarText();
        updateFragmentsCount();
        updateTransactionsCount();
    }

    // Vytvoření fragmentu
    public Fragment createFragment(String name) {
        if (name.equals(FRAGMENT_A_NAME)) {
            FragmentA newFragment = new FragmentA();
            return newFragment;
        } else if (name.equals(FRAGMENT_B_NAME)) {
            FragmentB newFragment = new FragmentB();
            newFragment.setTestNumber(fragmentsCount);
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

    public void addFragment(Fragment fragment, String name, Bundle data) {

        if (fragment == null) {
            fragment = createFragment(name);
        }

        fragment.setArguments(data);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(animShowFragment, animHideFragment, animPopShowFragment, animPopHideFragment);

        // Pokud zobrazujeme fragment E, bude vložen do kontejneru, který fragment zobrazí
        // v celém dostupném prostoru displeje. V opačném případě bude použit kontejner s omezenou velikostí
        if (name.equals(FRAGMENT_E_NAME)) {
            // Přidání fragmentu pod tagem "name", díky kterému budeme moci později tento přidný fragment
            // vyhledat pomocí metody FragmentManager.findFragmentByTag(name);
            fragmentTransaction.add(R.id.container2, fragment, name);
        } else {
            fragmentTransaction.add(R.id.container, fragment, name);
        }

        // Parametr "name" opět slouží k pozdějšímu vyhledání fragmentu
        if (chbAddToBackStack.isChecked()) fragmentTransaction.addToBackStack(null);
        //if (chbAddToBackStack.isChecked()) fragmentTransaction.addToBackStack(name);
        //else fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    // Byl-li již fragment se jménem "name" vytvořen a je v zásobníku, bude tento fragment zobrazen
    public void restoreFragment(String name) {
        int beCount = fragmentManager.getBackStackEntryCount();
        if (beCount == 0) return;
        fragmentManager.popBackStack(name, 0);
    }

    public void setMessageB(String messageB) {
        labelMessageB.setText(messageB);
    }

    @Override
    public void onResultCalculated(int number1, int number2, int result) {
        labelMessageA.setText("" + number1 + " + " + number2 + " = " + result);
    }

    @Override
    public void onFragmentMessageSent(String fragmentName, String message) {
        if (fragmentName.equals(FRAGMENT_C_NAME)) {
            labelMessageC.setText(message);
        } else if (fragmentName.equals(FRAGMENT_D_NAME)) {
            labelMessageD.setText(message);
        } else if (fragmentName.equals(FRAGMENT_E_NAME)) {
            labelMessageE.setText(message);
        }
    }
}
