package registry.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anzhen on 2016/5/25.
 */
public class ListUtil {


    public static <K,V,M>  Map<K,V> toMap(Collection<M> datum,MapBuilder<K,V,M> builder){

        Map<K,V> map = new HashMap<K, V>();
        if(datum !=null && builder != null){
            for(M data:datum){
                builder.put(data,map);
            }
        }
        return map;
    }

    /**
     * 把集合放到Map中
     *
     * @param datum   集合
     * @param map     Map
     * @param builder 构造器
     * @param <K>
     * @param <V>
     * @param <M>
     */
    public static <K, V, M> void put(Collection<M> datum, Map<K, V> map, MapBuilder<K, V, M> builder) {
        if (map != null && datum != null && builder != null) {
            for (M data : datum) {
                builder.put(data, map);
            }
        }
    }



   public interface MapBuilder<K,V,M>{
       void put(M data,Map<K,V> map);
   }

}
