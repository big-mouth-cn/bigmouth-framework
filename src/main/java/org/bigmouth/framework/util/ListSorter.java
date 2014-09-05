package org.bigmouth.framework.util;

import org.apache.commons.beanutils.BeanComparator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
 
/**
 * 对 List 元素的多个属性进行排序的类
 */
@SuppressWarnings({"unchecked"})
public class ListSorter {
 
    /**
     * List 元素的多个属性进行排序。例如 ListSorter.sort(list, "name", "age")，则先按
     * name 属性排序，name 相同的元素按 age 属性排序。
     *
     * @param list       包含要排序元素的 List
     * @param properties 要排序的属性。前面的值优先级高。
     */
    public static <V> void sort(List<V> list, final String... properties) {
        Collections.sort(list, new Comparator<V>() {
            public int compare(V o2, V o1) {
                if (o1 == null && o2 == null) return 0;
                if (o1 == null) return -1;
                if (o2 == null) return 1;
 
                for (String property : properties) {
                    @SuppressWarnings("rawtypes")
                    Comparator c = new BeanComparator(property);
                    //int result = c.compare(o1, o2);//降序
                    int result = c.compare(o2, o1);//升序
                    if (result != 0) {
                        return result;
                    }
                }
                return 0;
            }
        });
    }
}
