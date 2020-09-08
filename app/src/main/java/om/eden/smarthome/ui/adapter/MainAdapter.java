package om.eden.smarthome.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import om.eden.smarthome.R;
import om.eden.smarthome.databinding.LayoutPhotoListItemBinding;
import om.eden.smarthome.ui.MainViewModel;
import om.eden.smarthome.ui.listener.OnLoadMoreListener;
import om.eden.smarthome.ui.model.Photo;


public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    OnLoadMoreListener listener;



    public List<Photo> photoList=new ArrayList<>();


    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

    private boolean shouldLoadMore;



  public MainAdapter(MainViewModel viewModel, RecyclerView recyclerView,GridLayoutManager manager, LifecycleOwner lifecycleOwner, OnLoadMoreListener loadMoreListener) {
        this.listener = loadMoreListener;

        viewModel.getPhotos().observe(lifecycleOwner, photos -> {

            Log.e("TAG", "mainAdapter: "+photos.size() );

            if (photos != null) {
                photoList.addAll(photos);

            }
            notifyDataSetChanged();
        });

        viewModel.getShouldLoadMore().observe(lifecycleOwner,shouldLoadMore->{
            this.shouldLoadMore=shouldLoadMore;
        });

        viewModel.getIsLoading().observe(lifecycleOwner,isLoading->{
            loading=isLoading;
        });


//        setHasStableIds(true);


//      if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {

          final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView
                  .getLayoutManager();


      Log.e("TAG", "MainAdapter: "+ (recyclerView.getLayoutManager() instanceof GridLayoutManager));
      Log.e("TAG", "MainAdapter: "+ (recyclerView.getLayoutManager() instanceof LinearLayoutManager));
      Log.e("TAG", "MainAdapter:= "+"000"+ (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager));




          recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                      @Override
                      public void onScrolled(RecyclerView recyclerView,
                                             int dx, int dy) {
                          super.onScrolled(recyclerView, dx, dy);

                          totalItemCount = manager.getItemCount();
                          lastVisibleItem = manager
                                  .findLastVisibleItemPosition();

                          Log.e("TAG", "onScrolled: "+( !loading && totalItemCount <= (lastVisibleItem + visibleThreshold)));
                          if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                              // End has been reached
                              // Do something
                              if (listener != null) {
                                  listener.onLoadMore();
                              }
                              loading = true;
                          }
                      }
                  });
      }


//    }





    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            LayoutPhotoListItemBinding binding =  LayoutPhotoListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new PhotoItemViewHolder(binding);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {


        if (viewHolder instanceof PhotoItemViewHolder) {
            Photo photo=photoList.get(position);

            ((PhotoItemViewHolder) viewHolder).bind(photo);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return photoList == null ? 0 : photoList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position ==photoList.size()-1 && shouldLoadMore) {
            return VIEW_TYPE_LOADING;
        } else{
            return VIEW_TYPE_ITEM;
        }




    }




    @Override
    public long getItemId(int position) {

        return position;
    }




    public void clearItem(){
        if (photoList != null) photoList.clear();
        notifyDataSetChanged();
    }






    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }


    static final class PhotoItemViewHolder extends RecyclerView.ViewHolder {

        LayoutPhotoListItemBinding binding;

        private Photo photo;



        PhotoItemViewHolder(LayoutPhotoListItemBinding binding) {
            super(binding.getRoot());


            this.binding=binding;
            itemView.setOnClickListener(v -> {
                if(photo != null) {


                }
            });
        }

        void bind(Photo photo) {
            this.photo=photo;

            String url = "http://farm" + photo.getFarm() + ".staticflickr.com/" + photo.getServer() +
                    "/" + photo.getId() + "_" + photo.getSecret() + ".jpg";


            Glide.with(binding.getRoot().getContext())
                    .setDefaultRequestOptions(RequestOptions
                            .diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)
                            .priority(Priority.LOW) )
                    .load(url)

              .into(binding.image);


        }
    }



}
