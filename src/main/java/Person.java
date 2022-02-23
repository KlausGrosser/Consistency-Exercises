public class Person {
    private Integer ID;
    private String name;
    private String lastName;
    private Integer age;

    public Person(Integer ID, String name, String lastName, Integer age) {
        this.ID = ID;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }
}
