package mvp.android.com.mvplib.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import mvp.android.com.mvplib.R;


/**
 * toast工具
 * Created by zona on 2017/6/20.
 */

public class ToastView {

    private Toast toast;
    private View view;
    private static ToastView instance;

    private IToastView iToastView;

    private ToastView(Context context) {

    }

    private synchronized static ToastView getInstance(Context context) {
        if (instance == null) {
            instance = new ToastView(context);
        }
        return instance;
    }


    public static ToastView makeText(final Context context, CharSequence t) {
        ToastView toastView = getInstance(context);
        if (toastView.toast == null) {
            toastView.toast = new Toast(context);
            toastView.iToastView = new IToastView<CharSequence>() {
                @Override
                public View changeView(View view, CharSequence o) {
                    if (view == null) {
                        LayoutInflater inflate = (LayoutInflater)
                                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view = inflate.inflate(R.layout.toast_black, null);
                    }
                    TextView tv = (TextView) view.findViewById(R.id.message);
                    tv.setText(o);
                    return view;
                }
            };
            toastView.toast.setDuration(Toast.LENGTH_SHORT);
            toastView.toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toastView.view = toastView.iToastView.changeView(toastView.view, t);
        toastView.toast.setView(toastView.view);
        return toastView;
    }

    public void show() {
        toast.show();
    }

    public interface IToastView<T> {
        View changeView(View view, T t);
    }
}
