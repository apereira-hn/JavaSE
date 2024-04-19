// 1.2.2 Creation of the CurrentAccount and SavingsAccount
package org.components;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "SavingsAccount")
public class SavingsAccount extends Account {
    public SavingsAccount(String label, Client client) {
        super(label, client);
    }

    public SavingsAccount() {
        // Default constructor
    }
}
