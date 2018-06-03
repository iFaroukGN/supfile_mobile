package tg.geek228.supfile.Fragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tg.geek228.supfile.R;

/**
 * Created by farouk on 10/05/2018.
 */

public class ProfileView extends Fragment {

    public ProfileView() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_profil_view,container,false);
    }
}
