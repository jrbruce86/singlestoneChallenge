package singlestone.petstore.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ServerResponseFactoryService {

    /**
     * Creates a server response with null message and payload
     * @param httpStatus the status
     * @return the response
     */
    public ResponseEntity createServerResponse(final HttpStatus httpStatus) {
        return createServerResponse(httpStatus, null, null);
    }

    /**
     * Creates a service response with null payload
     * @param httpStatus the status
     * @param message the message
     * @return the response
     */
    public ResponseEntity createServerResponse(final HttpStatus httpStatus, final String message) {
        return createServerResponse(httpStatus, message, null);
    }

    /**
     * Creates a service response with null message
     * @param httpStatus the status
     * @param payload the payload
     * @return the response
     */
    public ResponseEntity createServerPayloadOnlyResponse(final HttpStatus httpStatus, final Object payload) {
        return createServerResponse(httpStatus, null, payload);
    }

    /**
     * Creates a server response
     * @param httpStatus the status
     * @param message the message
     * @param payload the payload
     * @return the response
     */
    public ResponseEntity createServerResponse(final HttpStatus httpStatus, final String message, Object payload) {
        return ResponseEntity.status(httpStatus).body(createResponseBody(message, payload));
    }

    private static class ResponseBody {
        private final String message;
        private final Object payload;

        public ResponseBody(String message, Object payload) {
            this.message = message;
            this.payload = payload;
        }

        public String getMessage() {
            return message;
        }

        public Object getPayload() {
            return payload;
        }

        @Override
        public String toString() {
            return "ResponseBody{" +
                    "message='" + message + '\'' +
                    ", payload=" + payload +
                    '}';
        }
    }

    private ResponseBody createResponseBody(final String message, final Object payload) {
        return new ResponseBody(message, payload);
    }

}
