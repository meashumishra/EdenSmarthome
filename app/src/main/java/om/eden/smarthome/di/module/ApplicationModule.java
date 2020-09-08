package om.eden.smarthome.di.module;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import okhttp3.OkHttpClient;

import okhttp3.logging.HttpLoggingInterceptor;
import om.eden.smarthome.data.remote.NetworkService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



@Module(includes ={ViewModelModule.class,ContextModule.class})
public class ApplicationModule {

    private static final String BASE_URL = "https://api.flickr.com/services/rest/";

    @Singleton
    @Provides
    Retrofit provideRetrofit() {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();

        okhttpClientBuilder.addInterceptor(interceptor).build();


        OkHttpClient client =okhttpClientBuilder.build();
        okhttpClientBuilder.connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)

        ;
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    @Singleton
    @Provides
    static NetworkService provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }

}
