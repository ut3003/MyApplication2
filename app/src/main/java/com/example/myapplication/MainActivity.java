package com.example.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.adapters.MyRecyclerViewAdapter;
import com.example.myapplication.models.MyModel;
import com.example.myapplication.viewmodels.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    private List<MyModel> myList;
    private List<String> myStrings;
    private RecyclerView recyclerView;
    private TextView actionText;
    private View view;
    private LinearLayout mLinearLayout;
    private LinearLayoutManager linearLayoutManager;
    private MyRecyclerViewAdapter myAdapter;
    private FloatingActionButton mFab;
    private MyViewModel myViewModel;
    private ProgressBar progressBar;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        recyclerView = findViewById(R.id.myRecyclerView);
        mFab = findViewById(R.id.fab);
        progressBar = findViewById(R.id.progress_bar);
        myList = new ArrayList<>();
        myStrings = new ArrayList<>();
        setSupportActionBar(toolbar);

        /* Custom Action Bar View*/
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_action_bar, null);
        actionText = view.findViewById(R.id.action_text);
        mLinearLayout = view.findViewById(R.id.my_custom_layout);

        /*ActionBar*/
        mLinearLayout.setVisibility(View.VISIBLE);
        actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(view);

        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        myViewModel.init();

        mFab.setOnClickListener(this);
        initRecyclerView();     //recyclerView initializer
        handleRecyclerViewScroll();     //Scrolling handling

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchPlaces();
        addPlace();
    }

    private void addPlace() {
        myViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    showProgressBar();
                } else {
                    hideProgressBar();
                    recyclerView.smoothScrollToPosition(myViewModel.getMyPlacesData().getValue().size() - 1); //srolling to the nth -1 position once added a place
                }
            }
        });
    }

    private void fetchPlaces() {
        myViewModel.getMyPlacesData().observe(this, new Observer<List<MyModel>>() {
            @Override
            public void onChanged(@Nullable List<MyModel> myModels) {
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initRecyclerView() {

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        myAdapter = new MyRecyclerViewAdapter(myViewModel.getMyPlacesData().getValue(), this);
        recyclerView.setAdapter(myAdapter);

    }


    void handleAppBar() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                    mLinearLayout.setVisibility(View.GONE);
                }
                if (scrollRange + verticalOffset == 0) {
                    mLinearLayout.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    mLinearLayout.setVisibility(View.GONE);
                    isShow = false;
                }
            }
        });
    }

    void handleRecyclerViewScroll() {
        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
                if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() > 1) {
                    params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED);
                } else {
                    params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
                }
                collapsingToolbarLayout.setLayoutParams(params);
                handleAppBar();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            myViewModel.addNewPlace(
                    new MyModel("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg", "Havasu Falls"));
        }
    }
}