package zcy.android.com.realmapp.struct;

/**
 * Created by user on 2017/9/4.
 */

public abstract class FunctionWithParamOnly<Param> extends Function {
  public FunctionWithParamOnly(String functionName) {
    super(functionName);
  }

  public abstract void function(Param data);
}
