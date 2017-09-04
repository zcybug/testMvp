package zcy.android.com.realmapp.struct;

/**
 * Created by user on 2017/9/4.
 */

public abstract class FunctionWithParamAndResult<Result,Param> extends Function {
    public FunctionWithParamAndResult(String functionName) {
        super(functionName);
    }

    public   abstract  Result funtion(Param data);
}
