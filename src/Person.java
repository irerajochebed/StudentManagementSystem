
public abstract class Person {


    private String personId;
    private String firstName;
    private String lastName;
    private String email;
    private int age;


    public Person(String personId, String firstName, String lastName,
                  String email, int age) {
        this.personId  = personId;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email     = email;
        this.age       = age;
    }


    public abstract String getRole();

    public abstract void displayInfo();


    public String getPersonId()  { return personId;  }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName;  }
    public String getFullName()  { return firstName + " " + lastName; }
    public String getEmail()     { return email;     }
    public int    getAge()       { return age;        }


    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        } else {
            System.out.println("Invalid email address provided.");
        }
    }

    public void setAge(int age) {
        if (age > 0 && age < 120) {
            this.age = age;
        } else {
            System.out.println("Invalid age provided.");
        }
    }

    @Override
    public String toString() {
        return "[" + getRole() + "] " + getFullName() + " (ID: " + personId + ")";
    }
}