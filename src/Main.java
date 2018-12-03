import java.util.HashMap;

/**
 * @author: zhangbaoning
 * @date: 2018/11/29
 * @since: JDK 1.8
 * @description: TODO
 */
public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        HashMap<Integer,String> hashMap1 = new HashMap();
        HashMap<Integer,String>  hashMap2 = new HashMap();
        for (int i=0;i<1000000;i++){
            hashMap1.put(i,"zhangbaoning");
            hashMap2.put(i,"zhangyupeng");
        }
        for (int o : hashMap1.keySet()) {
            if (hashMap2.containsKey(o)){
                System.out.println(hashMap1.get(o)+hashMap2.get(o));
            }
        }
        long end = System.currentTimeMillis();
        System.out.println((end-start)/1000.000+"秒");
    }
}
