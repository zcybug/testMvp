package zcy.android.com.realmapp;

/**
 * Created by user on 2017/9/4.
 */

public class BlankFragment1 extends BaseFragment {
    public static final String INTERFACE=BlankFragment1.class.getName()+"NPNR";


    public void onButtonPressed(String uri){
        mFunctionManager.invokeFunction(INTERFACE);
    }

}
