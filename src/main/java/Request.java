public class Request {

    private Integer idRequest;
    private String idSender;
    private String idDestination;
    private String message;
    private String validatorMessage;
    private String motif;
    private Status status;
    private Facilities facility;

    public Request(int idRequest, String idSender, String message, Facilities facility) {

        this.idRequest = idRequest;
        this.idSender = idSender;
        this.message = message;
        this.facility = facility;

    }
    public Request(int idRequest, String idSender, String message, String validatorMessage, String motif, Facilities facility, Status status, String idDestination) {

        this.idRequest = idRequest;
        this.idSender = idSender;
        this.message = message;
        this.validatorMessage = validatorMessage;
        this.motif = motif;
        this.facility = facility;
        this.status = status;
        this.idDestination = idDestination;

    }

    public int getIdRequest() {return idRequest;}

    public String getIdSender() {return idSender;}

    public String getIdDestination() {
        return idDestination;
    }

    public String getMessage() {
        return message;
    }

    public String getValidatorMessage() {return validatorMessage;}

    public String getMotif() {return motif;}

    public void setIdRequest(int idRequest) {this.idRequest = idRequest;}

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public void setIdDestination(String idDestination) {
        this.idDestination = idDestination;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setValidatorMessage(String validatorMessage) {this.validatorMessage = validatorMessage;}

    public void setMotif(String motif) {
        this.motif = motif;
        this.setStatus(Status.WAITING);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {this.status = status;}

    public void setFacility(Facilities facility) { this.facility = facility; }

    public Facilities getFacility() { return facility;}

    public static Request createRequest(int idRequest, String idSender, String message, Facilities facility) {
        return new Request(idRequest, idSender, message, facility);
    }
    public String toString() {
        return "numéro de la requete : " + getIdRequest() + "identifiant de l'envoyeur : " + getIdSender() + " A destination de : " + getIdDestination() + " Message : " + getMessage() + " Status : " + getStatus() + " Facility : " + getFacility();
    }

}
