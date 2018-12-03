import sun.net.ftp.FtpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: zhangbaoning
 * @date: 2018/11/29
 * @since: JDK 1.8
 * @description: TODO
 */
public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        long start = System.currentTimeMillis();

        // 获取当前日期时间
        Calendar calendar = Calendar.getInstance();
        // 获取此时此刻的昨天
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        // 对昨天进行格式化
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String yesterday = dateFormat.format(date);

        // 文件名
        String fileName = "ycshell_payorder_"+yesterday;
        // 用文件名创建文件
        File file = new File("D://"+fileName+".txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PrintWriter writer = new PrintWriter(file);
        ReadTransFile trans = new ReadTransFile();
        ReadShopFile shop = new ReadShopFile();
        FutureTask<HashMap> transTask = new FutureTask<>(trans);
        FutureTask<HashMap> shopTask = new FutureTask<>(shop);
        HashMap<String,LinkedList> transMap = null;
        HashMap shopMap = null;
        Thread transThread = new Thread(transTask);
        Thread shopThread = new Thread(shopTask);
        transThread.start();
        shopThread.start();
        try {
           transMap =  transTask.get();
            shopMap =  shopTask.get();
            for (String key : transMap.keySet()) {
                if (shopMap.containsKey(key)){
                    StringBuffer sb = new StringBuffer();
                    List<String> tranList = transMap.get(key);
                    List<String> shopList = (List) shopMap.get(key);
                    for (String str : tranList) {
                        sb.append(str+"|");
                    }
                    for (String str : shopList) {
                        sb.append(str+"|");
                    }
                    writer.println(sb.toString());
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        writer.close();
        System.out.println((end-start)/1000.000+"秒");
    }
}
