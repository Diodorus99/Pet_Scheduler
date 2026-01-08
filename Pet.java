import java.io.Serializable ;
import java.time.LocalDate ;
import java.util.List ;
import java.util.ArrayList ;

public class Pet implements Serializable {
    private String PetId ;
    private String name ;
    private String species ;
    private int age ;
    private String owner ;
    private String contact ;
    private LocalDate regDate ;
    private List<Appointment> appointments ;
    
    public Pet( String PetId , String name , String species , 
    int age , String owner , String contact ) {
        this.PetId = PetId ;
        this.name = name ;
        this.species = species ;
        this.age = age ; 
        this.owner = owner ;
        this.contact = contact ;
        this.regDate = LocalDate.now() ;
        this.appointments = new ArrayList<>() ;
    }
    //Setters
    public void setPetId(String PetId) { this.PetId = PetId ; }
    public void setName(String name) { this.name = name ; }
    public void setSpecies(String species) { this.species = species ; }
    public void setAge(int age) { this.age = age ; }
    public void setOwner(String owner) { this.owner = owner ; } 
    public void setContact(String contact) { this.contact = contact ; } 
    public void setRegDate(LocalDate regDate) { this.regDate = regDate ; }
    //Getters
    public String getPetId() { return PetId ; }
    public String getName() { return name ; }
    public String getSpecies() { return species ; }
    public int getAge() { return age ; }
    public String getOwner() { return owner ; }
    public String getContact() { return contact ; }
    public LocalDate getRegDate() { return regDate ; }
    public List<Appointment> getAppointments() { return appointments ; }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment) ;
    }
    @ Override
    public String toString() {
        return "Pet ID: "+PetId+", name: "+name+"\nspecies/breed: "+species+", age: "+age+"\nOwner: "+owner+", contact: "+contact ;  
    }
}
