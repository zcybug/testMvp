package com.android.mvp.db;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/24 0024  下午 3:20
 * 修改历史：
 * ================================================
 */

public class User extends RealmObject {

  public String name;//关键字
  public int age;

  @Ignore private int sessionId;//主键

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getSessionId() {
    return sessionId;
  }

  public void setSessionId(int sessionId) {
    this.sessionId = sessionId;
  }

  @Override public String toString() {
    return "User{" + "name='" + name + '\'' + ", age=" + age + ", sessionId=" + sessionId + '}';
  }
}
