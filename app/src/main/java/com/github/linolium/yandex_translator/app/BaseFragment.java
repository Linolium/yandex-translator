package com.github.linolium.yandex_translator.app;

import android.app.Fragment;

import com.github.linolium.yandex_translator.di.HasComponent;

/**
 * Created by Linolium on 07.04.2017.
 */

public class BaseFragment extends Fragment {

    @SuppressWarnings("unchecked")
    protected <T> T getComponent(Class<T> componentType) {
        return componentType.cast(((HasComponent<T>)getActivity()).getComponent());
    }
}
