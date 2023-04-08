package mfc.controllers;

import mfc.controllers.dto.ErrorDTO;
import mfc.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {AdminController.class,
        CatalogController.class, CustomerController.class,
        PayoffController.class, StoreController.class,
        StoreOwnerController.class, SurveyController.class})
public class GlobalControllerAdvice {

    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process request");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @ExceptionHandler({AlreadyRegisteredStoreException.class, AlreadyExistingAccountException.class, AlreadyExistingPayoffException.class, AlreadyExistingStoreException.class, AlreadyExistingSurveyException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDTO handleExceptions(Exception e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("This entity already exists");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @ExceptionHandler({AccountNotFoundException.class, StoreNotFoundException.class, PayoffNotFoundException.class, SurveyNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleExceptionsNotFound(Exception e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("This entity does not exist");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @ExceptionHandler({CredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleExceptionsUnauthorized(CredentialsException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Wrong credentials");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @ExceptionHandler({NegativeCostException.class, NegativePointCostException.class, NoCreditCardException.class, NegativeRefillException.class,
            NoMatriculationException.class, VFPExpiredException.class, InsufficientBalanceException.class, NoPreviousPurchaseException.class,
            InvalidAnswerException.class, AlreadyAnsweredException.class})
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ErrorDTO handleExceptionsNegativeCost(Exception e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Precondition not satisfied");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @ExceptionHandler({PaymentException.class, ParkingException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorDTO handleExceptionsPayment(Exception e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("External Service error");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }
}
