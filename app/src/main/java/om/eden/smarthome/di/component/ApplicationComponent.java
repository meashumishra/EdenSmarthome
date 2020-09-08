package om.eden.smarthome.di.component;

import android.app.Application;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;
import om.eden.smarthome.base.BaseApplication;
import om.eden.smarthome.di.module.ActivityBindingModule;
import om.eden.smarthome.di.module.ApplicationModule;
import om.eden.smarthome.di.module.ContextModule;


@Singleton
@Component(modules = {ContextModule.class,
        ApplicationModule.class, AndroidSupportInjectionModule.class,
        ActivityBindingModule.class})

public interface ApplicationComponent extends AndroidInjector<DaggerApplication>{
    void inject(BaseApplication app);



    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();

    }






}
