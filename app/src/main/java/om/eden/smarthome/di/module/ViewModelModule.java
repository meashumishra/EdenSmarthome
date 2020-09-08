package om.eden.smarthome.di.module;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import om.eden.smarthome.di.util.ViewModelKey;
import om.eden.smarthome.ui.MainViewModel;
import om.eden.smarthome.util.ViewModelFactory;


@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
