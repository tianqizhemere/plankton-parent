package top.tianqi.plankton.core.common.utils;

import java.util.*;

/**
 * JAVA 集合 按照某个字段（依据一定条件）进行分组
 *
 * Demo:
 *  Map<String,List<Student>> resMap = ListUtils.groupBy(studentList,new ListUtils.GroupBy<String,Student>(){
 *     @Override
 *     public String groupBy(Student row){
 *         String trq = row.getTrq();
 *         String trqStr = "";
 *         if(trq != null){
 *             trqStr = trq.substring(0,4);
 *         }
 *         return trqStr;
 *     }
 * });
 *
 *
 * @author Wukh
 * @create 2021-01-06
 */
public final class ListUtils {

    /**
     * list 集合分组
     *
     * @param list    待分组集合
     * @param groupBy 分组Key算法
     * @param <K>     分组Key类型
     * @param <V>     行数据类型
     * @return 分组后的Map集合
     */
    public static <K, V> Map<K, List<V>> groupBy(List<V> list, GroupBy<K, V> groupBy) {
        return groupBy((Collection<V>) list, groupBy);
    }

    /**
     * list 集合分组
     *
     * @param list    待分组集合
     * @param groupBy 分组Key算法
     * @param <K>     分组Key类型
     * @param <V>     行数据类型
     * @return 分组后的Map集合
     */
    public static <K, V> Map<K, List<V>> groupBy(Collection<V> list, GroupBy<K, V> groupBy) {
        Map<K, List<V>> resultMap = new LinkedHashMap<K, List<V>>();
        for (V e : list) {
            K k = groupBy.groupBy(e);
            if (resultMap.containsKey(k)) {
                resultMap.get(k).add(e);
            } else {
                List<V> tmp = new LinkedList<V>();
                tmp.add(e);
                resultMap.put(k, tmp);
            }
        }
        return resultMap;
    }

    /**
     * List分组
     *
     * @param <K> 返回分组Key
     * @param <V> 分组行
     */
    public interface GroupBy<K, V> {
        K groupBy(V row);
    }
}
