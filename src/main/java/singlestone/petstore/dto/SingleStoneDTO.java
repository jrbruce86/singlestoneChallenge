package singlestone.petstore.dto;

/**
 * DTO expected from singlestone that wraps all incoming data, if the dto does not meet this format an exception will be thrown
 * @param <T> the type of data expected being sent by singlestone
 */
public class SingleStoneDTO<T> {
    private Integer statusCode;
    private T body;

    public Integer getStatusCode() {
        return statusCode;
    }

    public SingleStoneDTO<T> setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public T getBody() {
        return body;
    }

    public SingleStoneDTO<T> setBody(T body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        return "SingleStoneDTO{" +
                "statusCode=" + statusCode +
                ", body=" + body +
                '}';
    }
}