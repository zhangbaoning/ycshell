import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author: zhangbaoning
 * @date: 2018/11/29
 * @since: JDK 1.8
 * @description: TODO
 */
public class ReadTransFile implements Callable<HashMap> {


    public static void main(String[] args) {
     /*   long start = System.currentTimeMillis();
        new ReadFile().readFile();
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.000 + "s");*/
    }

    @Override
    public HashMap<String,LinkedList> call() throws Exception {
        HashMap hashMap = new HashMap(30);

        // try-with-resource 自动关闭资源
        try (
                Reader reader = new FileReader("C:/Users/lenovo/Desktop/延长壳牌建行聚合支付微信openid文件接口处理相关/trans-11252-20181018(清算交易对账单样例文件).txt");
                LineNumberReader lineReader = new LineNumberReader(reader)
        ){

            String str;
            int i = 0;
            while ((str = lineReader.readLine()) != null) {
                if (lineReader.getLineNumber() > 1 ) {
                    String[] strings = str.split("\\|");
                    try {
                        System.out.println("读取trans文件");
                        List transList = new LinkedList();
                        // 商户号
                        String shh = strings[1];
                        // 商户订单号
                        String shddh = strings[3];
                        // 平台流水号
                        String ptlsh = strings[4];
    //                    System.out.println(ptlsh);
                        transList.add(shh);
                        transList.add(shddh);
                        transList.add(ptlsh);
                        hashMap.put(ptlsh, transList);
                        i ++;
                        System.out.println(ptlsh + "当前是第:" + i);
                    } catch (Exception e) {
                        System.out.println(lineReader.getLineNumber() + "不存在");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}
