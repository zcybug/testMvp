package mvp.android.com.mvplib.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 5213230387175987834L;
    public int code;
    public String msg;
    public T data;
}
