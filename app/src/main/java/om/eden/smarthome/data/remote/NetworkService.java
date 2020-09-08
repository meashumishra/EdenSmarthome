package om.eden.smarthome.data.remote;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import om.eden.smarthome.ui.model.ApiResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;


public interface NetworkService {


    @GET("?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1")
    Single<ApiResponse> getImages(@Query("text")String query,@Query("page") int page);






}
