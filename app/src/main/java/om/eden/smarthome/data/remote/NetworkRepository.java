package om.eden.smarthome.data.remote;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;


import javax.inject.Inject;

import io.reactivex.Single;
import om.eden.smarthome.ui.model.ApiResponse;


public class NetworkRepository {

    private final NetworkService networkService;


   Context context;

    @Inject
    public NetworkRepository(NetworkService networkService, Context context) {
        this.networkService = networkService;
        this.context=context;



        Log.e("TAG", "NetworkRepository: "+context );

    }



    public Single<ApiResponse> getPhotos(String query,int page) {
        return networkService.getImages( query, page);
    }




}


