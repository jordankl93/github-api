package com.trustly.githubapi.exception;

import com.trustly.githubapi.model.response.GenericError;

public class BusinessException extends Exception {
	
	private final GenericError erro;

	private static final long serialVersionUID = 1L;

	public BusinessException(GenericError erro, Throwable cause) {
		super(erro.toString(), cause);
		this.erro = erro;
	}

	public BusinessException(GenericError erro) {
		super(erro.toString());
		this.erro = erro;
	}

	public BusinessException(Throwable cause) {
		super(cause);
		if (cause instanceof BusinessException) {
			this.erro = ((BusinessException) cause).getErro();
		} else {
			this.erro = new GenericError("500", cause.getMessage());
		}
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		if (cause instanceof BusinessException) {
			this.erro = ((BusinessException) cause).getErro();
		} else {
			this.erro = new GenericError("500", message);
		}
	}
	
	public GenericError getErro() {
		return erro;
	}
	
	@Override
	public String getMessage() {

		return erro.getMessage();
	}
	
	public String getCode() {
		return erro.getCode();
	}
	

}

