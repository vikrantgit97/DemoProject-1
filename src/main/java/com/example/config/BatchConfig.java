package com.example.config;

import com.example.entity.OrderDetails;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Configuration
//@EnableBatchProcessing
public class BatchConfig {

   /* @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step insertOrderDetailsStep() {
        return stepBuilderFactory.get("insertOrderDetailsStep")
                .<OrderDetails, OrderDetails>chunk(100)
                .reader(orderDetailsReader())
                .writer(orderDetailsWriter())
                .build();
    }

    @Bean
    public JpaItemWriter<OrderDetails> orderDetailsWriter() {
        JpaItemWriter<OrderDetails> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public ItemReader<OrderDetails> orderDetailsReader() {
        List<OrderDetails> orderDetailsList =  null;
        //getOrderDetailsList();
        // get list of OrderDetails from previous code
        return new ListItemReader<>(orderDetailsList);
    }

    @Bean
    public Job insertOrderDetailsJob() {
        return jobBuilderFactory.get("insertOrderDetailsJob")
                .incrementer(new RunIdIncrementer())
                .flow(insertOrderDetailsStep())
                .end()
                .build();
    }


    public void insertOrderDetails(List<OrderDetails> orderDetailsList) {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("jobName", "insertOrderDetailsJob")
                .toJobParameters();
        try {
            JobExecution jobExecution = jobLauncher.run(insertOrderDetailsJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
        }
        // handle jobExecution status and exceptions as needed
    }*/


}
