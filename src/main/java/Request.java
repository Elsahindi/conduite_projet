public class Request {

    private String idSender;
    private String idDestination;
    private String message;
    private Status status;
    private Facilities facility;

    public Request(String idSender, String message, Facilities facility) {

        this.idSender = idSender;
        this.message = message;
        this.facility = facility;

    }
    public Request(String idSender, String message, Facilities facility, Status status, String idDestination) {

        this.idSender = idSender;
        this.message = message;
        this.facility = facility;
        this.status = status;
        this.idDestination = idDestination;

    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setFacility(Facilities facility) { this.facility = facility; }

    public Facilities getFacility() { return facility;}

}
