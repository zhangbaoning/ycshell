import sun.security.krb5.internal.crypto.Aes256;
import sun.security.provider.MD5;

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
public class ReadShopFile implements Callable<HashMap> {
    @Override
    public HashMap call() throws Exception {
        HashMap hashMap = new HashMap(30);
        try (Reader reader = new FileReader("C:/Users/lenovo/Desktop/延长壳牌建行聚合支付微信openid文件接口处理相关/SHOP.105000573992432.20181017.txt");
             LineNumberReader lineReader = new LineNumberReader(reader)){
            String str;
            int j = 0;
            while ((str = lineReader.readLine()) != null) {
                if (lineReader.getLineNumber()>2) {
                    String[] strings = str.split("\t");
                    try {
                        System.out.println("读取shop文件");
                        // 订单号
                        String ptlsh = strings[4];
                        List transList = new LinkedList();
                        // 订单金额
                        String ddje = strings[8];
                        // 交易时间
                        String jysj = strings[0];
                        // 微信openid
                        String openid = strings[7];
                        transList.add(ddje);
                        transList.add(jysj);
                        transList.add(openid);
                        hashMap.put(ptlsh, transList);
                        j ++;
                        System.out.println(ptlsh + "当前是第:" + j);
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
