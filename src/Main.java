import java.util.Calendar;
import java.util.Date;
import java.util.Timer;


/**
 * @author: zhangbaoning
 * @date: 2018/11/29
 * @since: JDK 1.8
 * @description: 对账文件生成主入口
 */
public class Main {

    public static void main(String[] args) {
        // 获取当天日期的18点
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,16);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Date date = calendar.getTime();

        // 创建定时器从当天18点开始，每个一天时间进行再次执行
        Timer timer = new Timer();
        timer.schedule(new WriteFile(),date,24*60*60*1000);
    }
}
