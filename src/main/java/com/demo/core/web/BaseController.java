package com.demo.core.web;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.demo.core.exception.MultiLangException;
import com.demo.core.simplification.Trimmer;
import com.demo.core.validation.exception.ValidationException;
import com.demo.core.validation.validator.ValidateError;
import com.demo.utils.MessageResolver;
import com.demo.utils.SimpleDateEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

public class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private LocaleResolver localeResolver;

    public BaseController() {
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new SimpleDateEditor());
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity exp(HttpServletRequest request, Exception ex) {
        logger.error("BaseController.exp() => Error:", ex);
        ResponseEntity responseEntity = new ResponseEntity();
        String message = null;
        MessageResolver messageResolver = new MessageResolver(this.messageSource, this.localeResolver.resolveLocale(request));
        if (ex instanceof MultiLangException) {
            MultiLangException mex = (MultiLangException)ex;
            message = messageResolver.getMessage(mex.getMessageCode(), mex.getArgs());
            responseEntity.setCode(mex.getErrorCode());
        } else if (ex instanceof ValidationException) {
            ValidationException vex = (ValidationException)ex;
            responseEntity.setCode("1001");
            List<ValidateError> errors = vex.getErrors();
            StringBuilder messageBuilder = new StringBuilder();
            Iterator var9 = errors.iterator();

            while(var9.hasNext()) {
                ValidateError error = (ValidateError)var9.next();
                String msgCode = error.getErrorMsg();
                Object[] args = error.getArgs();
                messageBuilder.append(messageResolver.getMessage(msgCode, args)).append("\r\n");
            }

            message = messageBuilder.toString();
        } else {
            responseEntity.setCode(ResponseState.ERROR.getValue());
            message = ex.getMessage();
        }

        responseEntity.setMsg(message);
        return responseEntity;
    }

    public ResponseEntity success(Object data) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setData(data);
        responseEntity.setCode(ResponseState.SUCCESS.getValue());
        return responseEntity;
    }

    public ResponseEntity success(Trimmer trimmer) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (trimmer != null) {
            responseEntity.setData(trimmer.trim());
        }

        responseEntity.setCode(ResponseState.SUCCESS.getValue());
        return responseEntity;
    }

    public ResponseEntity fail(String msg) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(ResponseState.ERROR.getValue());
        responseEntity.setMsg(msg);
        return responseEntity;
    }
}
