package zcy.android.com.realmapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import zcy.android.com.realmapp.struct.FunctionException;

/**
 * Created by user on 2017/9/4.
 */

public class BlankFragment2 extends BaseFragment {

    private View view;
    public static final String INTERFACE_RESULT_ONLY = BlankFragment1.class.getName() + "withResult";


    private static final String tagStr = "fragment2_str";

    public static BlankFragment2 getInstance(int tag) {
        Bundle bundle = new Bundle();
        bundle.putInt(tagStr, tag);
        BlankFragment2 contentFragment = new BlankFragment2();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    public void onButtonPressed(String uri) {
        try {
            String str=mFunctionManager.invokeFunction(INTERFACE_RESULT_ONLY,String.class);
            Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();

        } catch (FunctionException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_layout, container, false);
        view.findViewById(R.id.fragment_btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed("uri");
            }
        });
        return view;
    }
}
