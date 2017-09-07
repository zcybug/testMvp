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

public class BlankFragment3 extends BaseFragment {
    private View view;
    public static final String INTERFACE_RANDP = BlankFragment1.class.getName() + "withRNP";
    private static final String tagStr = "fragment3_str";

    public static BlankFragment3 getInstance(int tag) {
        Bundle bundle = new Bundle();
        bundle.putInt(tagStr, tag);
        BlankFragment3 contentFragment = new BlankFragment3();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    public void onButtonPressed(String uri) {
        try {
            String str = mFunctionManager.invokeFunction(INTERFACE_RANDP, "520", String.class);
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
