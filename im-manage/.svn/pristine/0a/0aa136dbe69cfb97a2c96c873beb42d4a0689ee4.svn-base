package cn.com.gome.manage.ThreadPool;

import cn.com.gome.manage.mongodb.dao.MsgDao;
import cn.com.gome.manage.mongodb.dao.MsgStatsDao;
import cn.com.gome.manage.mongodb.model.MsgStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangshikai on 2016/2/18.
 */
public class SchedulThreadListener implements ServletContextListener {
    private Logger logger = LoggerFactory.getLogger(SchedulThreadListener.class);
    private ScheduledExecutorService scheduledExecutorService = null;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        init(scheduledExecutorService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        shutdown(scheduledExecutorService);
    }

    public void shutdown(ScheduledExecutorService scheduledExecutorService) {
        scheduledExecutorService.shutdown();
    }

    private void init(ScheduledExecutorService scheduledExecutorService) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy:MM:dd 23:59:59");
            String todayEnd = format1.format(Calendar.getInstance().getTime());
            final SimpleDateFormat format2 = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            long nowTime = System.currentTimeMillis();
            long todayEndTime = format2.parse(todayEnd).getTime();
            long initialTime = todayEndTime - nowTime;
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    MsgStatsDao msgStatsDao = new MsgStatsDao();
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, -1);
                    final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String yesterday = format.format(calendar.getTime());
                    final SimpleDateFormat formatStart = new SimpleDateFormat("yyyy:MM:dd 00:00:00");
                    String yesterdayStartDate = formatStart.format(calendar.getTime());
                    long yesterdayStartTime = 0L;
                    try {
                        yesterdayStartTime = format2.parse(yesterdayStartDate).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                        logger.error(e.getMessage());
                    }
                    MsgStats msgStats = msgStatsDao.findMsgStats(yesterday);
                    if (msgStats == null) {
                        MsgStats stats = new MsgStats();
                        MsgDao msgDao = new MsgDao();
                        for (int i = 1; i <= 24; i++) {
                            //统计每小时内单聊和群聊的消息数量
                            long singleMsgCount = msgDao.getMsgCountByDay(1, yesterdayStartTime + (i - 1) * 60 * 60 * 1000, yesterdayStartTime + i * 60 * 60 * 1000);
                            long groupMsgCount = msgDao.getMsgCountByDay(2, yesterdayStartTime + (i - 1) * 60 * 60 * 1000, yesterdayStartTime + i * 60 * 60 * 1000);
                            stats.getSingleMsgCounts().add(singleMsgCount);
                            stats.getGroupMsgCounts().add(groupMsgCount);
                        }
                        stats.setDay(yesterday);
                        msgStatsDao.addMsgStats(stats);
                    }
                    logger.info("MsgStats ScheduleExecutorService executing!");
                }
            }, initialTime + 1000, 24*60*60*1000, TimeUnit.MILLISECONDS);
            logger.info("MsgStats ScheduleExecutorService start!");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
//        cal.add(Calendar.DATE, -1);
//        SimpleDateFormat matter1 = new SimpleDateFormat("yyyy:MM:dd 00:00:00");
//        String yesterdayStart = matter1.format(cal.getTime());
//        System.out.println(yesterdayStart);
//        SimpleDateFormat matter2 = new SimpleDateFormat("yyyy:MM:dd 23:59:59");
//        String yesterdayEnd = matter2.format(cal.getTime());
//        System.out.println(yesterdayEnd);
//        try {
//            SimpleDateFormat matter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
//            long startTime = matter.parse(yesterdayStart).getTime();
//            System.out.println(startTime + "-------" + new Date(startTime));
//            long endTime = matter.parse(yesterdayEnd).getTime();
//            System.out.println(endTime + "-------" + new Date(endTime));
//            System.out.println(endTime - startTime);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
