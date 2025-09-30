package vn.com.execise.shoppingservice.domain.exception;

public class DomainException extends RuntimeException {

    private final Integer statusCode;
    private final String errorCode;

    public DomainException(String message, Integer statusCode, String errorCode) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
