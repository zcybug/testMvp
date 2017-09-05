package zcy.android.com.realmapp.struct;

/**
 * Created by user on 2017/9/4.
 */

public abstract class FunctionNoParamNoResult extends Function {
  public FunctionNoParamNoResult(String functionName) {
    super(functionName);
  }

  public abstract void function();
}
