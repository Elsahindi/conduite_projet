public class Validator extends User{

    enum Facility2 {
        HOSPITAL,
        RETIREMENT,
        SCHOOL
    }

    private Facility2 facility;

    public Validator(int id, String pswd, Facility2 facility) {
        super(id,pswd);
        this.facility = facility;
    }


    public void login(){};

}
