package com.yufan.job;

import com.yufan.job.service.IJobService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/12 16:26
 * 功能介绍: 定时任务
 */
@Component
public class ScheduledService {

    private Logger LOG = Logger.getLogger(ScheduledService.class);

    @Autowired
    private IJobService iJobService;

    /**
     * 清除支付缓存
     */
    @Scheduled(cron = "0/5 * * * * *")
    public void clearPayCache() {
        iJobService.clearPayCache();
    }
}
