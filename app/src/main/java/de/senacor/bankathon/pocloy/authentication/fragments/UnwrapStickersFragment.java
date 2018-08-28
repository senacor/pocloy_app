package de.senacor.bankathon.pocloy.authentication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import butterknife.ButterKnife;
import de.senacor.bankathon.pocloy.R;
import de.senacor.bankathon.pocloy.authentication.dto.UserAssets;
import de.senacor.bankathon.pocloy.authentication.framework.DataHolder;

public class UnwrapStickersFragment extends Fragment {
    List<UserAssets> userAssets;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_unwrap_stickers, container, false);
        ButterKnife.bind(this, view);
        
        this.userAssets = DataHolder.getUserAssets();
        
        return view;
    }
    
}
