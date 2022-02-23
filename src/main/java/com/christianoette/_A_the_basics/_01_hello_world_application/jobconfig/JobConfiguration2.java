package com.christianoette._A_the_basics._01_hello_world_application.jobconfig;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfiguration2 {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;
    
    

    public JobConfiguration2(JobBuilderFactory jobBuilderFactory, 
    						StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}



	@Bean
    public Job otherJob() {
        Step step = stepBuilderFactory.get("step")
                .tasklet((contribution, chunkContext) -> {
                    Map<String, Object> jobParameters = chunkContext.getStepContext()
                            .getJobParameters();
                    Object outputText = jobParameters.get("outputText");
                    System.out.println("Another job " + outputText);
                    return RepeatStatus.FINISHED;
                }).build();

        return jobBuilderFactory.get("helloWorldJob")
                .start(step)
                .build();
    }

}
