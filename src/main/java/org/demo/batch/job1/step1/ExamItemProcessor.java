package org.demo.batch.job1.step1;

import org.demo.batch.job1.model.Exam;
import org.springframework.batch.item.ItemProcessor;

public class ExamItemProcessor implements ItemProcessor<Exam, Exam>{
	
	@Override
	public Exam process(Exam result) throws Exception {
		System.out.println("Processing result :"+result);
		
		// Process only if more than 5%
		if(result.getPercentage() < 5) {
			return null; // Do not process
		}
		
		return result; // Process
	}

}
