package model;

public class Developer {

    private int id;
    private String name;
    private String specialty;
    private int salary;

    public Developer() {
    }

    public Developer(String name, String specialty, int salary) {
        this.name = name;
        this.specialty = specialty;
        this.salary = salary;
    }

    public Developer(int id, String name, String specialty, int salary) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Developer: " +
                " id = " + id +
                " name = '" + name + '\'' +
                " specialty = '" + specialty + '\'' +
                " salary = '" + salary + '\'' +
                '}';
    }
}
