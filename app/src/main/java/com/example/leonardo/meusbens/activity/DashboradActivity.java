package com.example.leonardo.meusbens.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.leonardo.meusbens.R;
import com.example.leonardo.meusbens.adapter.TabsAdapter;
import com.example.leonardo.meusbens.util.SlidingTabLayout;

public class DashboradActivity extends AppCompatActivity {
    private Toolbar toolbarPrincipal;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashborad);


        /**************************************************************************
         * configurar Toolbar
         *
         **************************************************************************/
        toolbarPrincipal = (Toolbar) findViewById(R.id.toolbar_principal);
        setSupportActionBar(toolbarPrincipal);

        /**************************************************************************
         * configurar abas
         *
         **************************************************************************/
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab_main);
        viewPager = (ViewPager) findViewById(R.id.view_pager_main);

        /**************************************************************************
         * configurar adapter
         *
         **************************************************************************/
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(tabsAdapter);
        slidingTabLayout.setCustomTabView(R.layout.tabs_view,R.id.text_item_tab);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.branco));
        slidingTabLayout.setViewPager(viewPager);

    }

}
