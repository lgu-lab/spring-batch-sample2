package org.demo.batch.job1.step1;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.demo.batch.job1.model.Exam;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

public class ExamItemPreparedStatementSetter implements ItemPreparedStatementSetter<Exam> {

	public void setValues(Exam result, PreparedStatement ps) throws SQLException {
		ps.setString(1, result.getStudentName());
		ps.setDate(2, new java.sql.Date(result.getDob().toDate().getTime())); // Date conversion 
		ps.setDouble(3, result.getPercentage());
	}

}