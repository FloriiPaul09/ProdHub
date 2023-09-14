package com.project.config;

import jakarta.persistence.AttributeConverter;

public class SecretCodeConv implements AttributeConverter<String, String>{

	Character[] arrChar = {'P', 'B', '@', 'R', 'X', '!', 'H', 'W', '$', 'U'};
	
	@Override
	public String convertToDatabaseColumn(String secretCode) {
		
		String code = "";
		
		if(secretCode == null) {
			return code;
		}
		
		for(Character c : secretCode.toCharArray()) {
			Integer index = Integer.parseInt(c.toString());
			code += arrChar[index];
		}
		
		return code;
		
	}
	
	@Override
	public String convertToEntityAttribute(String dbData) {
		
		String code = "";
		
		if(dbData == null) {
			return code;
		}
		
		for(Character c : dbData.toCharArray()) {
			for(int i = 0; i < arrChar.length; i++) {
				if(arrChar[i].equals(c)) {
					code += i;
				}
			}
		}
		
		return code;
	}
	
}
