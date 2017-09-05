package zcy.android.com.realmapp.struct;

/**
 * Created by user on 2017/9/4.
 */

public abstract class FunctionWithParamOnly<Param> extends Function {
    public FunctionWithParamOnly(String funtionName) {
        super(funtionName);
    }

    public   abstract  void funtion(Param data);
}
