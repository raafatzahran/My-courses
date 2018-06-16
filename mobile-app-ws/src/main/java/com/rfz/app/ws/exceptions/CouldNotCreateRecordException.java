package com.rfz.app.ws.exceptions;

import com.rfz.app.ws.ui.model.response.ErrorMessage;
import com.rfz.app.ws.ui.model.response.ErrorMessages;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CouldNotCreateRecordException extends RuntimeException{

    private static final long serialVersionUID = -5865565853821170976L;

    public CouldNotCreateRecordException(String message)
    {
        super(message);
    }

}
