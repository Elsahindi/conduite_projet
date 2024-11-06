public class Request {

    private String idSender;

    private String idDestination;

    private String message;

    private String status;

    public String getIdSender() {
        return idSender;
    }

    public String getIdDestination() {
        return idDestination;
    }

    public String getMessage() {
        return message;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public void setIdDestination(String idDestination) {
        this.idDestination = idDestination;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
