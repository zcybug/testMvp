package zcy.android.com.realmapp;

import android.content.Context;
import android.support.v4.app.Fragment;

import zcy.android.com.realmapp.struct.FunctionsManager;

/**
 * Created by user on 2017/9/4.
 */

public class BaseFragment extends Fragment {

    protected FunctionsManager mFunctionManager;
    MainActivity mBaseActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity){
            mBaseActivity=(MainActivity)context;
            mBaseActivity.setFunctionForFragment(getTag());
        }
    }

    public void setmFunctionManager(FunctionsManager functionManager){
        this.mFunctionManager=functionManager;
    }

}
