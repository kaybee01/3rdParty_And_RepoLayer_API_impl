package org.scaler.fakestore.ExceptionAdvice;

import org.scaler.fakestore.Exception.BadRequestException;
import org.scaler.fakestore.Exception.ProductNotFound;
import org.scaler.fakestore.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<ExceptionResponseDto>handleProductNotFound(){
        ExceptionResponseDto exceptionResponseDto =new ExceptionResponseDto();

        exceptionResponseDto.setStatus(HttpStatus.NOT_FOUND);
        exceptionResponseDto.setMessage("Product Not Found");

        return new ResponseEntity<>(exceptionResponseDto , HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponseDto> handleBadRequest(){
        ExceptionResponseDto exceptionResponseDto =new ExceptionResponseDto();

        exceptionResponseDto.setStatus(HttpStatus.BAD_REQUEST);
        exceptionResponseDto.setMessage("Bad Request..!");

        return new ResponseEntity<>(exceptionResponseDto , HttpStatus.BAD_REQUEST);
    }
}
