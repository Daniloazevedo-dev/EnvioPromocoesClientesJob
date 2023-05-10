package com.springbatch.enviopromocoesclientes.config;

import com.springbatch.enviopromocoesclientes.job.EnvioPromocoesClientesScheduleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail quartzJobDetail() {
        return JobBuilder
                .newJob(EnvioPromocoesClientesScheduleJob.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger jobTrigger() {
        SimpleScheduleBuilder simpleScheduleBuilder =
                SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInMilliseconds(60)
                        .withRepeatCount(2);
        return TriggerBuilder
                .newTrigger()
                .forJob(quartzJobDetail())
                .withSchedule(simpleScheduleBuilder)
                .build();
    }

}
