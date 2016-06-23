package com.excilys.entity;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA class converting automatically Java LocalDate (program) to SQL Timestamp (DB) 
 * and SQL Timestamp (DB) to Java LocalDate (program)
 * @author pqwarlot
 *
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Timestamp> {

	@Override
    public Timestamp convertToDatabaseColumn(LocalDate locDate) {
    	return (locDate == null ? null : Timestamp.valueOf(locDate.toString() + " 00:00:00"));
    }

    @Override
    public LocalDate convertToEntityAttribute(Timestamp sqlTimestamp) {
    	return (sqlTimestamp == null) ? null : sqlTimestamp.toLocalDateTime().toLocalDate();
    }
}
