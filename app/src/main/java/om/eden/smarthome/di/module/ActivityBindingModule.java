package om.eden.smarthome.di.module;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import om.eden.smarthome.ui.MainActivity;


@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector()
    abstract MainActivity bindMainActivity();

}
