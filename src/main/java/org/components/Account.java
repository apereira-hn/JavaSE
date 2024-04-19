// 1.2.1 Creation of the account class
package org.components;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "account")
public abstract class Account {

    private static int count = 0;
    protected String label;
    protected double balance;
    protected int accountNumber;
    protected Client client;
    private String type;


    protected Account(String label, Client client) {
        this.label = label;
        this.client = client;
        this.accountNumber = ++count;
    }

    protected Account() {
        // Default constructor
    }


    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public double getBalance() {
        return balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public Client getClient() {
        return client;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // 1.3.2 Creation of the Flow class
    // Would have used a switch/pattern matching here on Java 14+, for readability
    public void setBalance(Flow flow) {
        if (flow instanceof Credit) {
            this.balance += flow.getAmount();
        } else if (flow instanceof Debit) {
            this.balance -= flow.getAmount();
        } else if (flow instanceof Transfer) {
            if (this.getAccountNumber() == flow.getTargetAccountNumber()) {
                this.balance += flow.getAmount();
            }
            if (this.getAccountNumber() == ((Transfer) flow).getIssuingAccountNumber()) {
                this.balance -= flow.getAmount();
            }
        }
    }


    @Override
    public String toString() {
        return "Account{" +
                "label='" + label + '\'' +
                ", balance=" + balance +
                ", accountNumber=" + accountNumber +
                ", client=" + client +
                '}';
    }
}
