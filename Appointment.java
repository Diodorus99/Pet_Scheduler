import java.io.Serializable;
import java.time.LocalDate ;
import java.time.LocalTime ;

public class Appointment implements Serializable {
    private String type ;
    private LocalDate date ;
    private LocalTime time ;
    private String notes ;
    public Appointment() {
        this.date = LocalDate.now() ;
        this.time = LocalTime.now() ;
    }
    public Appointment( String type , LocalDate date , LocalTime time ) {
        this.type = type ;
        this.date = date ;
        this.time = time ;    
    }
    public Appointment( String type, LocalDate date , LocalTime time , String notes) {
        this.type = type ;
        this.date = date ;
        this.time = time ;
        this.notes = notes ;
    }
    //Setters
    public void setType( String type ) { this.type = type ; }
    public void setDate( LocalDate date ) { this.date = date ; }
    public void setTime( LocalTime time ) { this.time = time ; }
    public void setNotes( String notes ) { this.notes = notes ; }
    //Getters
    public String getType() { return type ; } 
    public LocalDate getDate() { return date ; } 
    public LocalTime getTime() { return time ; } 
    public String getNotes() { return notes ; } 

    @ Override
    public String toString() {
        if (notes != null) {
            return "Appointment: "+type+"\ndate: "+date+" time: "+time+"\nnotes: "+notes ;
        } else { return "Appointment: "+type+"\ndate: "+date+" time: "+time ; }   
    } 
}
