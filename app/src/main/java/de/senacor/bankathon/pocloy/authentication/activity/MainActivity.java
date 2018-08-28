package de.senacor.bankathon.pocloy.authentication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.Optional;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.senacor.bankathon.pocloy.R;
import de.senacor.bankathon.pocloy.authentication.fragments.MyCollectionFragment;
import de.senacor.bankathon.pocloy.authentication.fragments.QrCodeFragment;
import de.senacor.bankathon.pocloy.authentication.fragments.RedeemStickersFragment;
import de.senacor.bankathon.pocloy.authentication.fragments.TradeStickersFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_layout)
    DrawerLayout mainLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbar();

        Log.d("MainActivity", "MainActivity onCreate called");

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            mainLayout.closeDrawers();

            Log.d("MainActivity", "Switching navigation item");

            withFragmentTransaction(fragmentTransaction ->
                    determineFragmentToShow(menuItem.getItemId())
                            .ifPresent(fragment -> fragmentTransaction.replace(R.id.content_frame, fragment))
            );

            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mainLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.menu);
        }
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    private Optional<Fragment> determineFragmentToShow(int menuItemId) {
        switch (menuItemId) {
            case R.id.nav_qrcode:
                QrCodeFragment qrCodeFragment = new QrCodeFragment();
                qrCodeFragment.setArguments(QrCodeFragment.createBundle());
                return Optional.of(qrCodeFragment);
            case R.id.nav_collection:
                return Optional.of(new MyCollectionFragment());
            case R.id.nav_redeem:
                return Optional.of(new RedeemStickersFragment());
            case R.id.nav_trade:
                return Optional.of(new TradeStickersFragment());
        }
        return Optional.empty();
    }

    private void withFragmentTransaction(FragmentTransactionAction action) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        action.apply(fragmentTransaction);
        fragmentTransaction.commit();
    }

    @FunctionalInterface
    private interface FragmentTransactionAction {
        void apply(FragmentTransaction fragmentTransaction);
    }
}
