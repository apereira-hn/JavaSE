// 1.1.1 Creation of the client class
package org.components;

public class Client {

    private static int count = 0;
    private String name;
    private String firstName;
    protected int clientNumber;

    public Client(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
        this.clientNumber = ++count;
    }

    public Client() {
        // Default constructor
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", clientNumber=" + clientNumber +
                '}';
    }
}
