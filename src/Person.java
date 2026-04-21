public abstract class Person {

    private String personId;
    private String firstName;
    private String lastName;
    private String email;
    private int age;


    public Person(String personId, String firstName, String lastName,
                  String email, int age) {


        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new InvalidEmailException(email == null ? "null" : email);
        }


        if (age < 16 || age > 100) {
            throw new InvalidAgeException(age);
        }

        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public abstract String getRole();
    public abstract void displayInfo();

    // Getters
    public String getPersonId() { return personId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getEmail() { return email; }
    public int getAge() { return age; }

    // Setters with validation
    public void setEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new InvalidEmailException(email == null ? "null" : email);
        }
        this.email = email;
    }

    public void setAge(int age) {
        if (age < 16 || age > 100) {
            throw new InvalidAgeException(age);
        }
        this.age = age;
    }

    @Override
    public String toString() {
        return "[" + getRole() + "] " + getFullName() + " (ID: " + personId + ")";
    }
}