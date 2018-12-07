import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author: zhangbaoning
 * @date: 2018/12/3
 * @since: JDK 1.8
 * @description: ftp工具类
 */
public class FTPUtils {
    /**
     *
     * @param fileName 文件名
     * @param host 主机地址
     * @param port 端口
     * @param user 用户名
     * @param pwd 密码
     */
    public static void connect(String filePath,String fileName,String host,int port,String user,String pwd) {
        FtpClient client = FtpClient.create();
        SocketAddress socketAddress = new InetSocketAddress(host,port);
        try {
            // 地址连接
            client.connect(socketAddress);
            // 登陆
            client.login(user,pwd.toCharArray());
            // 发送文件
            InputStream inputStream = new FileInputStream(fileName);
            client.putFile(filePath,inputStream);

            System.out.println(client.getSystem());
        } catch (FtpProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
