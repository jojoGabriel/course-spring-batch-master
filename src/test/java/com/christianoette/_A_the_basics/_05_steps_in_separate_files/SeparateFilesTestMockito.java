package com.christianoette._A_the_basics._05_steps_in_separate_files;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.christianoette._A_the_basics._05_steps_in_separate_files.config.BatchConfig;
import com.christianoette._A_the_basics._05_steps_in_separate_files.config.ReaderConfiguration;
import com.christianoette._A_the_basics._05_steps_in_separate_files.dto.InputData;
import com.christianoette._A_the_basics._05_steps_in_separate_files.processor.UpperCaseJsonProcessor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedList;

@SpringBootTest(classes = {BatchConfig.class,  
		UpperCaseJsonProcessor.class, SeparateFilesTestMockito.TestConfig.class})
@EnableBatchProcessing
// @Disabled // TODO Remove disabled, if test won't start in your ide!
class SeparateFilesTestMockito {

	@Autowired
	private Job job;
	
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	
	@MockBean
	private ItemStreamReader<InputData> itemReader;
	
    @Test
    // @Disabled
    void testJob() throws Exception {
    	
    	InputData inputData = new InputData();
    	inputData.value = "My test data with mockito and in memory reader";
    	
    	Mockito.when(itemReader.read())
    			.thenReturn(inputData)
    			.thenReturn(null);
    	    	
    	JobParameters jobParams = new JobParametersBuilder()
    			.addParameter("outputPath", new JobParameter("output/output.json"))
    			.toJobParameters();
    	JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParams);
    	
    	BatchStatus exitStatus = jobExecution.getStatus();
    	assertThat(exitStatus).isEqualTo(BatchStatus.COMPLETED);
    }
    
    @Configuration
    static class TestConfig {
    	
    	@Bean
    	public JobLauncherTestUtils jobLauncherTestUtils() {
    		return new JobLauncherTestUtils();
    	}
    	
    }
}
