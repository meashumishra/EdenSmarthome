package om.eden.smarthome.ui;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;





import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import om.eden.smarthome.data.remote.NetworkRepository;
import om.eden.smarthome.ui.model.ApiResponse;
import om.eden.smarthome.ui.model.Photo;





public class MainViewModel extends ViewModel {
    private static final String TAG = MainViewModel.class.getCanonicalName();
    private final NetworkRepository repository;
    private CompositeDisposable disposable;

    private String mQuery;
    private int mPageNumber;

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private MutableLiveData<Boolean> isLoading=new MutableLiveData<>();

    public MutableLiveData<Boolean> getShouldLoadMore() {
        return shouldLoadMore;
    }

    private MutableLiveData<Boolean > shouldLoadMore=new MutableLiveData<>();


    private MutableLiveData<List<Photo>> photoList=new MutableLiveData<>();




    public MutableLiveData<List<Photo>> getPhotos() {
        return photoList;
    }




    @Inject
    public MainViewModel(NetworkRepository networkRepository){

        repository=networkRepository;
        disposable=new CompositeDisposable();

        shouldLoadMore.setValue(false);



    }



    public void searchPhotos(String query,int page) {


        if (mPageNumber==0){
            mPageNumber=1;

            shouldLoadMore.setValue(false);
        }
        isLoading.setValue(true);


        mQuery = query;
        mPageNumber = page;

        disposable.add(repository.getPhotos(query,mPageNumber).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<ApiResponse>() {
                    @Override
                    public void onSuccess(ApiResponse value) {
                        Log.e(TAG, "onSuccess: "+value.getPhotos().getPages()+"="+mPageNumber );


                        if ( mPageNumber < value.getPhotos().getPages()){
                            shouldLoadMore.setValue(true);
                            Log.e(TAG, "onSuccess: "+"Load more"+shouldLoadMore.getValue());
                        }



                        photoList.setValue(value.getPhotos().getPhoto());
                        isLoading.setValue(false);


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: "+e.getMessage() );
                        isLoading.setValue(false);

                    }
                }));
    }


    public void searchNextPage(){


        if(!isLoading.getValue() &&shouldLoadMore.getValue()){
            Log.e(TAG, "onSuccess: "+"Next call" );
          searchPhotos(mQuery,mPageNumber+1);
        }
    }


}
