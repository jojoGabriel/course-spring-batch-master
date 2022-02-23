package com.christianoette._A_the_basics._01_hello_world_application;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.christianoette._A_the_basics._01_hello_world_application.jobconfig.MyJob;

@Component
public class TriggerJobService {
	

	private final JobLauncher jobLauncher;
	private final Job job;
	
	
	
	public TriggerJobService(JobLauncher jobLauncher, @MyJob Job job) {
		this.jobLauncher = jobLauncher;
		this.job = job;
	}



	public void runJob() throws JobExecutionAlreadyRunningException, 
					JobRestartException, 
					JobInstanceAlreadyCompleteException, 
					JobParametersInvalidException, InterruptedException {
		
		JobParameters jobParameters = new JobParametersBuilder()
				.addParameter("outputText", new JobParameter("first spring batch app"))
				.toJobParameters();
		
		jobLauncher.run(job, jobParameters);
		
		
		Thread.sleep(2000);
		
		JobParameters jobParameters2 = new JobParametersBuilder()
				.addParameter("outputText", new JobParameter("second run"))
				.toJobParameters();
		
		jobLauncher.run(job, jobParameters2);
		
		
	}

}
