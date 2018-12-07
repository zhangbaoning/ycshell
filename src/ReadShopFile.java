

import java.io.*;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author: zhangbaoning
 * @date: 2018/11/29
 * @since: JDK 1.8
 * @description: 建行交易对账单文件读取
 */
public class ReadShopFile implements Callable<HashMap> {
    private String shopFilePath;
    public ReadShopFile(String shopFilePath) {
        this.shopFilePath = shopFilePath;
    }

    @Override
    public HashMap call() throws Exception {
        HashMap hashMap = new HashMap(30);
        try (Reader reader = new FileReader(shopFilePath);
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
                        // 对openid进行MD5加密
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        transList.add(toHex(md.digest(openid.getBytes())));
                        hashMap.put(ptlsh, transList);
                        j ++;
                        System.out.println("当前是第:" + j);
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
    public static String toHex(byte[] buffer) {
        StringBuffer sb = new StringBuffer(buffer.length * 2);
        for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 15, 16));
        }
        return sb.toString();
    }
}
