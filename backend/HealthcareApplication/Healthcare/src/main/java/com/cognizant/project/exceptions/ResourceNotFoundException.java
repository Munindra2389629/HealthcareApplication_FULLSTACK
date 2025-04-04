package com.cognizant.project.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message){
		super(message);
	}
	
	public ResourceNotFoundException(String resourceName,String fieldName,String fieldValue){
		super(resourceName+" not found with "+fieldName+" : "+fieldValue);
	}
}
