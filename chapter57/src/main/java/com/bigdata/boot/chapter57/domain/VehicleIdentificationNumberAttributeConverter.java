package com.bigdata.boot.chapter57.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA {@link AttributeConverter} for {@link VehicleIdentificationNumber}.
 *
 * @author Phillip Webb
 */
@Converter(autoApply = true)
public class VehicleIdentificationNumberAttributeConverter
		implements AttributeConverter<VehicleIdentificationNumber, String> {

	@Override
	public String convertToDatabaseColumn(VehicleIdentificationNumber attribute) {
		return attribute.toString();
	}

	@Override
	public VehicleIdentificationNumber convertToEntityAttribute(String dbData) {
		return new VehicleIdentificationNumber(dbData);
	}

}