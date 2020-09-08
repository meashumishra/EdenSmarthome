package om.eden.smarthome.ui;


import androidx.annotation.Nullable;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;


import android.os.Bundle;

import android.view.View;


import java.util.List;

import javax.inject.Inject;

import om.eden.smarthome.R;
import om.eden.smarthome.base.BaseActivity;
import om.eden.smarthome.databinding.ActivityMainBinding;
import om.eden.smarthome.ui.adapter.MainAdapter;
import om.eden.smarthome.ui.listener.OnLoadMoreListener;
import om.eden.smarthome.ui.model.Photo;
import om.eden.smarthome.util.ItemDecorationColumns;
import om.eden.smarthome.util.ViewModelFactory;

public class MainActivity extends BaseActivity implements OnLoadMoreListener {
    private static final String TAG = "RecipeListActivity";

    @Inject
    ViewModelFactory viewModelFactory;
    private MainViewModel viewModel;

    ActivityMainBinding binding;


    private MainAdapter mAdapter;

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        View view = binding.getRoot();
        setContentView(view);

        initRecyclerView();
        subscribeObservers();
        initSearchView();

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
    }



    private void subscribeObservers(){
        viewModel.getPhotos().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable List<Photo> recipes) {

            }
        });


    }

    private void initRecyclerView(){

        GridLayoutManager manager=new GridLayoutManager(this,3);
        mAdapter = new MainAdapter(viewModel,binding.rvPhotos,manager,this,this);

       binding.rvPhotos .setLayoutManager(manager);
        binding.rvPhotos.addItemDecoration(new ItemDecorationColumns(
                getResources().getDimensionPixelSize(R.dimen.photos_list_spacing),
                getResources().getInteger(R.integer.photo_list_preview_columns)));
        binding.rvPhotos.setAdapter(mAdapter);


    }

    private void initSearchView(){
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                viewModel.searchPhotos(s, 1);
                mAdapter.clearItem();
               binding.searchView.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public void onLoadMore() {

        viewModel.searchNextPage();

    }
}