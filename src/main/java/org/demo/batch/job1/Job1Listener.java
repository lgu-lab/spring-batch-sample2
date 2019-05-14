package org.demo.batch.job1;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class Job1Listener implements JobExecutionListener{

	private final static String CREATE_TABLE = 
//			"DROP TABLE people IF EXISTS;\r\n" + 
//			"\r\n" + 
			"CREATE TABLE EXAM_RESULT (" + 
			"    STUDENT_NAME   VARCHAR(100) NOT NULL PRIMARY KEY, " + 
			"    DOB            DATE, " + 
			"    PERCENTAGE     DECIMAL " + 
			");";
	
	private DateTime startTime, stopTime;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		startTime = new DateTime();
		System.out.println("ExamResult Job starts at :"+startTime);
		
		System.out.println("CREATE TABLE : ");
		System.out.println("---");
		System.out.println(CREATE_TABLE);
		System.out.println("---");
		
		jdbcTemplate.execute(CREATE_TABLE);
		
		System.out.println("TEST TABLE : ");
		int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM EXAM_RESULT", Integer.class);
		System.out.println("TEST TABLE RESULT = " + result);
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		stopTime = new DateTime();
		System.out.println("ExamResult Job stops at :"+stopTime);
		System.out.println("Total time take in millis :"+getTimeInMillis(startTime , stopTime));
		
		// Check Job status :
		if(jobExecution.getStatus() == BatchStatus.COMPLETED){
			System.out.println("ExamResult job completed successfully");
			//Here you can perform some other business logic like cleanup
		}
		else if(jobExecution.getStatus() == BatchStatus.FAILED){
			System.out.println("ExamResult job failed with following exceptions ");
			// trace d'exeception
			List<Throwable> exceptionList = jobExecution.getAllFailureExceptions();
			for(Throwable th : exceptionList){
				System.err.println("exception :" +th.getLocalizedMessage());
			}
		}
	}
	
	private long getTimeInMillis(DateTime start, DateTime stop){
		return stop.getMillis() - start.getMillis();
	}
	
}
