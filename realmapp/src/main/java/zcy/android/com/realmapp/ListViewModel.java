package zcy.android.com.realmapp;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class ListViewModel implements Serializable {

    private String name;

    public ListViewModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
