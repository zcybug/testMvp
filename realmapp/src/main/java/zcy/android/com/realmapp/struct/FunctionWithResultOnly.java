package zcy.android.com.realmapp.struct;

/**
 * Created by user on 2017/9/4.
 */

public abstract class FunctionWithResultOnly<Result> extends Function {
  public FunctionWithResultOnly(String functionName) {
    super(functionName);
  }

  public abstract Result function();
}
