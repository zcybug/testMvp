package zcy.android.com.realmapp.struct;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by user on 2017/9/4.
 */

public class FunctionsManager {

  private static FunctionsManager _instance;

  private FunctionsManager() {
    mFunctionNoParamNoResult = new HashMap<>();
    mFunctionWithResultOnly = new HashMap<>();
    mFunctionWithParamOnly = new HashMap<>();
    mFunctionWithParamAndResult = new HashMap<>();
  }

  public static FunctionsManager getInstance() {
    if (null == _instance) {
      _instance = new FunctionsManager();
    }
    return _instance;
  }

  private HashMap<String, FunctionNoParamNoResult> mFunctionNoParamNoResult;
  private HashMap<String, FunctionWithResultOnly> mFunctionWithResultOnly;
  private HashMap<String, FunctionWithParamOnly> mFunctionWithParamOnly;
  private HashMap<String, FunctionWithParamAndResult> mFunctionWithParamAndResult;

  public FunctionsManager addFunction(FunctionNoParamNoResult function) {
    mFunctionNoParamNoResult.put(function.mFunctionName, function);
    return this;
  }

  public void invokeFunction(String functionName) {
    if (TextUtils.isEmpty(functionName) == true) {
      return;
    }
    if (null != mFunctionNoParamNoResult) {
      FunctionNoParamNoResult f = mFunctionNoParamNoResult.get(functionName);
      if (null != f) {
        f.function();
      }
      if (null == f) {
        try {
          throw new FunctionException("Has no this function" + functionName);
        } catch (FunctionException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public FunctionsManager addFunction(FunctionWithResultOnly function) {
    mFunctionWithResultOnly.put(function.mFunctionName, function);
    return this;
  }

  public <Result> Result invokeFunction(String functionName, Class<Result> c)
      throws FunctionException {
    if (TextUtils.isEmpty(functionName) == true) {
      return null;
    }
    if (null != mFunctionWithResultOnly) {
      FunctionWithResultOnly f = mFunctionWithResultOnly.get(functionName);
      if (null != f) {
        if (null != c) {
          return c.cast(f.function());
        } else {
          return (Result) f.function();
        }
      }
      if (null == f) {
        throw new FunctionException("Has no this function" + functionName);
      }
    }
    return null;
  }
}
