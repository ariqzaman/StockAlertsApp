package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.capstoneproject.fragments.AlertsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_alerts:
                        Toast.makeText(MainActivity.this, "Alerts!", Toast.LENGTH_SHORT).show();
                        fragment = new AlertsFragment();
                        break;
                    case R.id.action_portfolio:
                        Toast.makeText(MainActivity.this, "Portfolio!", Toast.LENGTH_SHORT).show();
                        fragment = new AlertsFragment();
                        //fragment = new PortfolioFragment();
                        break;
                    case R.id.action_news:
                    default:
                        Toast.makeText(MainActivity.this, "News!", Toast.LENGTH_SHORT).show();
                        fragment = new AlertsFragment();
                        //fragment = new NewsFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }

        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_alerts);
    }
}