package homework4;

import java.io.Serializable;

class Message implements Serializable {
    private String clientName;
    private String message;

    String getMessage() {
        return message;
    }

    String getClientName() {
        return clientName;
    }

    void setMessage(String message) {
        this.message = message;
    }

    void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
