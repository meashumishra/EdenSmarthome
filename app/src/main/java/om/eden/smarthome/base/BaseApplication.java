package om.eden.smarthome.base;

import android.app.Application;


import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import om.eden.smarthome.di.component.ApplicationComponent;
import om.eden.smarthome.di.component.DaggerApplicationComponent;


public class BaseApplication extends DaggerApplication {

    Application instance;


    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent component = DaggerApplicationComponent.builder().application(this).build();
        component.inject(this);


        return component;
    }

    public  Application getApplication(){

        if (instance == null){
            instance=new BaseApplication();
        }

        return instance;

    }








}
