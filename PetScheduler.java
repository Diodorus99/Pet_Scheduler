import java.io.*;
import java.util.*;
import java.time.temporal.ChronoUnit ;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PetCareScheduler {
    private static Scanner scnr = new Scanner(System.in) ;  
    private static Map<String , Pet> pets = new HashMap<>() ;
    public static void main(String[] args) {
        loadPetsFromFile();
        boolean running = true;
        while (running) {
            System.out.println("\n=== Pet Care Scheduler ===");
            System.out.println("1. Register pets");
            System.out.println("2. Schedule appointments");
            System.out.println("3. Store data");
            System.out.println("4. Display records");
            System.out.println("5. Generate Reports");
            System.out.println("6. Save and Exit");
            System.out.print("Choose an option: ");

            String choice = scnr.nextLine();
            switch (choice) {
                case "1":
                    registerPet();
                    break;
                case "2":
                    scheduleAppointment();
                    break;
                case "3":
                    storeData();
                    break;
                case "4":
                    displayRecords();
                    break;
                case "5":
                    generateReports();
                    break;
                case "6":
                    storeData();
                    running = false;
                    System.out.println("Data saved. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-6.");
            }
        }
    }
    // Register pet
    private static void registerPet() {
        // Ask for attribute values
        System.out.print("\nEnter pet ID: ");
        String PetId = scnr.nextLine().trim();  
        if (pets.containsKey(PetId)) {
            System.out.println("Error: Pet ID already registered.");
            return;
        }
        System.out.print("Enter pet name: ");
        String name = scnr.nextLine().trim() ;
        System.out.print("Enter pet species/breed: ");
        String species = scnr.nextLine().trim() ;
        int age = 0;
        while (true) {
            try {
                System.out.print("Enter pet age: ");;
                age = Integer.parseInt(scnr.nextLine().trim()) ;
                if (age<=0) throw new IllegalArgumentException();
                break ;
            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Must be a positive number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid age. Must be a positive number.");
            }
        }    
        System.out.print("Enter Owner's name: ");
        String owner = scnr.nextLine().trim() ;
        System.out.print("Enter contact: ");
        String contact = scnr.nextLine().trim() ;
        // Create and register pet
        Pet pet = new Pet(PetId , name , species , age , owner, contact) ;
        pets.put( PetId , pet ) ;
        System.out.println("Pet successfully registered.") ; 
    }
    // Schedule appointment
    private static void scheduleAppointment() {
        if (pets.isEmpty()) {
            System.out.println("\nNo pets registered yet.");
            return;
        }
        // ask for pet id
        System.out.print("\nEnter pet ID: ");
        String PetId = scnr.nextLine().trim();
        // get pet from registered list
        Pet pet = pets.get(PetId);
        if (pet == null) {
            System.out.println("Error: Pet ID not found.");
            return;
        }
        // ask for attributes for appointment
        String type = "";
        while (true) {
            try {
                System.out.println("Enter appointment type (vet visit/vaccination/grooming appointment):") ;
                type = scnr.nextLine().trim() ;
                if ( !( type.equalsIgnoreCase("vet visit") || type.equalsIgnoreCase("vaccination") || 
                type.equalsIgnoreCase("grooming appointment") ) ) throw new IllegalArgumentException();
                break ; 
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid type. Must be either vet visit, vaccination or grooming appointment.");
            }
        }
        LocalDate date = LocalDate.now() ;
        while (true) {
            try {
                System.out.print("Enter date for appointment (format: dd/MM/yyyy): ") ;
                String dateStr = scnr.nextLine().trim() ;
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
                date = LocalDate.parse(dateStr , dateFormat) ;
                if (date.isBefore(LocalDate.now()) ) throw new IllegalArgumentException();
                break ;
            } catch (DateTimeParseException dfe) {
                System.out.println("Incorrect format for date.") ;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date. Appointment must be scheduled for future");
            }
        }    
        LocalTime time = LocalTime.now() ;
        while (true) {
            try {
                System.out.print("Enter time for appointment (fomat HH:mm): ") ;
                String timeStr = scnr.nextLine() ;
                DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm") ;
                time = LocalTime.parse(timeStr , timeFormat) ;
                if ((date.equals(LocalDate.now()) && time.isAfter(LocalTime.now()) ) ||
                (date.equals(LocalDate.now()) && time.equals(LocalTime.now())) ) throw new IllegalArgumentException();
                break ;
            } catch (DateTimeParseException dfe) {
                System.out.println("Incorrect format for time.") ;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid time. Appointment must be scheduled for future");
            }
        }
        System.out.print("Enter any notes you wish to add (optional): ") ;
        String notes = scnr.nextLine() ;
        // Create appointment
        Appointment appointment = new Appointment() ;
        if ( notes.length()==0 ) {
            appointment = new Appointment( type , date , time) ;
        } else { appointment = new Appointment( type , date , time , notes ) ; }
        // Add appoinment to Pet's appointment list
        pet.addAppointment(appointment) ;
        System.out.println("Appointment succesfully scheduled") ;
    }
    // Store data
    private static void storeData() {
        try {
            // Create a FileOutputStream
            ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream("pets.ser") );
            // Write map to the file
            out.writeObject(pets);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        } 
    }
    // Display records
    private static void displayRecords() {
        if (pets.isEmpty()) {
            System.out.println("\nNo pets registered.");
            return;
        }
        System.out.println("\nDo you wish to display"+
        "\n1 - All registered pets"+
        "\n2 - All appointments for a specific pet" +
        "\n3 - Upcoming appointments for all pets" +
        "\n4 - Past appointments history for each pet" ) ;
        String choice = scnr.nextLine().trim() ;
        switch (choice) {
            // display all registered pets
            case "1" :
                displayAllPets() ;
                break ;
            // display all appointments for specific pet    
            case "2" :
                displayAllAppointments() ;
                break ;
            // display upcoming appointments for all pets
            case "3" :
                dislplayFutureAppointments() ;
                break ;
            // display past appointments for each pet
            case "4" :
                displayAppointmentHistory() ;
                break ;
            default :
                System.out.println("Option not available. Choose a valid option") ;
                break ;
        }
    } 
    // displayAllPets()
    private static void displayAllPets() { 
        System.out.println("Registered pets:\n") ;
        for (Pet pet : pets.values() ) {
            System.out.println(pet) ;
        }
    }
    // displayAllAppointments()
    private static void displayAllAppointments() {
        String PetId = "";
        Pet pet = pets.get(PetId) ;
        while (true) {
            try {
                System.out.print("Enter pet ID: ") ;
                PetId = scnr.nextLine() ;
                pet = pets.get(PetId);
                if (pet == null) throw new IllegalArgumentException() ; 
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Pet ID not found.");
            }  
        }
        if (pet.getAppointments().isEmpty()) {
            System.out.println("No appointments found");
        } else {
            System.out.println("\nAppointments for pet (Pet ID: "+PetId+" ):\n") ;
            for (Appointment i : pet.getAppointments()) {
                System.out.println(i);
            }
        }
    }
    // displayFutureAppointments()
    private static void dislplayFutureAppointments() {
        for (Pet pet : pets.values() ) {
            if (pet.getAppointments().isEmpty()) {
                System.out.println("No appointments for pet (Pet ID: " +pet.getPetId()+" )");
            } else {
                System.out.println("Upcomming appointments for pet (Pet ID: "+pet.getPetId()+" ):\n") ;
                int count = 0 ;
                for (Appointment i : pet.getAppointments()) {
                    if (i.getDate().isAfter(LocalDate.now()) || 
                    (i.getDate().equals(LocalDate.now()) && i.getTime().isAfter(LocalTime.now()) ) ) {
                        System.out.println(i);
                        count += 1;                        
                    }
                }
                if (count==0) { System.out.println("No upcomming appointments found") ; }
            }
        }
    }
    // displayAppointmentHistory()
    private static void displayAppointmentHistory() {
        for (Pet pet : pets.values() ) {
            if (pet.getAppointments().isEmpty()) {
                System.out.println("No appointments for pet (Pet ID: " +pet.getPetId()+" )");
            } else {
                System.out.println("Appointment history for pet (Pet ID: "+pet.getPetId()+" ):\n") ;
                int count = 0 ;
                for (Appointment i : pet.getAppointments()) {
                    if (i.getDate().isBefore(LocalDate.now()) || 
                    (i.getDate().equals(LocalDate.now()) && i.getTime().isBefore(LocalTime.now()) ) ) {
                        System.out.println(i);
                        count += 1;                        
                    }
                }
                if (count==0) { System.out.println("No past appointments found") ; }
            }
        }
    }
    // Generate reports
    private static void generateReports() {
        if (pets.isEmpty()) {
            System.out.println("\nNo pets registered. Nothing to report");
            return; 
        }    
        // Upcomming appointments in next week 
        System.out.println("Pets with upcomming appointments in the next week: ") ;
        int count = 0 ;
        for (Pet pet : pets.values() ) {
            for (Appointment i : pet.getAppointments()) {
                if (ChronoUnit.DAYS.between(LocalDate.now() , i.getDate())<=7) {
                    System.out.println("\n"+pet);
                    count += 1;                        
                    break;
                }
            }   
        }
        if (count==0) { System.out.println("No upcomming appointments in the next week") ; }
        // No vet visit in last 6 months
        System.out.println("\nPets overdue for a vet visit: ") ;
        for (Pet pet : pets.values() ) {
            boolean check = false ;
            for (Appointment i : pet.getAppointments()) {
                String type = i.getType() ;
                if ( type.equalsIgnoreCase("vet visit") ) {
                    if (i.getDate().isBefore(LocalDate.now()) && ChronoUnit.MONTHS.between(i.getDate() , LocalDate.now()) <= 6  )  {
                        check = true;
                    }                        
                }
            }   
            if ( !check ) { System.out.println(pet) ; }
        } 
    }       
    @SuppressWarnings("unchecked")
    private static void loadPetsFromFile() {
        try (
            // Open an ObjectInputStream to read from the file
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("pets.ser"))
        ) {
            pets = (Map<String, Pet>) in.readObject();
            System.out.println("Household data loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

}
