package mvp.android.com.mvplib.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建：   dema
 * 时间：   2017/4/21 11:09
 */
public class ObjectToMapUtil {

    /**
     * 实体类对象转换成Map
     */
    public static Map<String, String> ConvertObjToMap(Object obj) {
        Map<String, String> reMap = new HashMap<>();
        if (obj == null)
            return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                if(!"serialVersionUID".equals( fields[i].getName())){
                    try {
                        Field f = obj.getClass().getDeclaredField(
                                fields[i].getName());
                        f.setAccessible(true);
                        Object object = f.get(obj);
                        if(object!=null){
                            String o = object.toString();
                            reMap.put(fields[i].getName(), o);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return reMap;
    }

}
