public class Client extends User{

    enum Facility {
        HOSPITAL,
        RETIREMENT,
        SCHOOL
    }

    private Facility facility;

    public Client(int id, String pswd, Facility facility) {
        super(id,pswd);
        this.facility = facility;
    }



    public void login(){};

}
