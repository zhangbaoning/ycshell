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
 * @description: 写文件
 */
public class WriteFile extends TimerTask {
    /**
     * 生产文件输出路径
     */
    private String outPath = "D://";
    /**
     * 清算交易对账单文件路径
     */
    private String tranFilePath = "C:/Users/lenovo/Desktop/延长壳牌建行聚合支付微信openid文件接口处理相关/trans-11252-20181018(清算交易对账单样例文件).txt";
    /**
     * 建行交易对账单文件路径
     */
    private String shopFilePath = "C:/Users/lenovo/Desktop/延长壳牌建行聚合支付微信openid文件接口处理相关/SHOP.105000573992432.20181017.txt";
    /**
     * ftp主机地址
     */
    private String host = "192.168.2.4";
    /**
     * ftp端口
     */
    private int port = 21;
    /**
     * ftp用户名
     */
    private String user = "tomcat";
    /**
     * ftp密码
     */
    private String pwd = "tomcat";
    private String ftpPath="ycshell/";

    @Override
    public void run() {
        long start = System.currentTimeMillis();

        // 获取当前日期时间
        Calendar calendar = Calendar.getInstance();
        // 获取此时此刻的昨天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        // 对昨天进行格式化
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String yesterday = dateFormat.format(date);

        // 文件名
        String fileName = "ycshell_payorder_" + yesterday;
        // 用文件名创建文件
        String pathFileName = outPath + fileName + ".txt";
        File file = new File(pathFileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 读取文件的对象
        ReadTransFile trans = new ReadTransFile(tranFilePath);
        ReadShopFile shop = new ReadShopFile(shopFilePath);

        // 获取线程返回数据
        FutureTask<HashMap> transTask = new FutureTask<>(trans);
        FutureTask<HashMap> shopTask = new FutureTask<>(shop);

        HashMap<String, LinkedList> transMap = null;
        HashMap shopMap = null;

        //开启两个读取线程
        Thread transThread = new Thread(transTask);
        Thread shopThread = new Thread(shopTask);
        transThread.start();
        shopThread.start();
        try {
            // 读取文件之后的数据
            transMap = transTask.get();
            shopMap = shopTask.get();

            // 使用两个map相同的key对数据进行匹配
            for (String key : transMap.keySet()) {
                if (shopMap.containsKey(key)) {
                    StringBuffer sb = new StringBuffer();
                    List<String> tranList = transMap.get(key);
                    List<String> shopList = (List) shopMap.get(key);
                    for (String str : tranList) {
                        sb.append(str + "|");
                    }
                    for (String str : shopList) {
                        sb.append(str + "|");
                    }
                    writer.println(sb.toString());
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        writer.close();
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.000 + "秒");
        // ftp发送
        FTPUtils.connect(ftpPath+fileName,pathFileName, host, port, user, pwd);
    }
}

