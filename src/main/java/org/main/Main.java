// 1.1.2 Creation of main class for tests
package org.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.components.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        displayClients(generateClients(1));
        //1.2.3 Creation of the tables account
        displayAccounts(generateAccounts(clients));
        // 1.3.1 Adaptation of the table of accounts
        displayAccountsSorted(convertAccountToHashMap(accounts));
        // 1.3.5 Updating accounts
        updateAccounts(generateFlows(accounts), accountHashMap);
        // 2.1 JSON file of flows
        try {
            System.out.println("JSON");
            updateAccounts(getFlowsFromJson("src/main/resources/flows.json"), accountHashMap);
        } catch (IOException e) {
            logger.severe("\nError loading flows from JSON file: " + e.getMessage());
        }
        // 2.2 XML file of account
        try {
            System.out.println("XML");
            displayAccountsSorted(convertAccountToHashMap(getAccountsFromXml("src/main/resources/accounts.xml")));
        } catch (IOException | JAXBException e) {
            logger.severe("\nError loading flows from XML file: " + e.getMessage());
        }
    }

    static Logger logger = Logger.getLogger(Main.class.getName());

    static ArrayList<Client> clients = new ArrayList<>();

    public static List<Client> generateClients(int numberOfClients) {
        for (int i = 1; i <= numberOfClients; i++) {
            clients.add(new Client("Name" + i, "FirstName" + i));
        }
        return clients;
    }

    public static void displayClients(List<Client> clients) {
        String allClients = clients.stream()
                .map(Client::toString)
                .collect(Collectors.joining("\n"));
        logger.info("\n" + allClients);
    }

    //1.2.3 Creation of the tables account
    static ArrayList<Account> accounts = new ArrayList<>();

    public static List<Account> generateAccounts(List<Client> clients) {
        for (Client client : clients) {
            accounts.add(new SavingsAccount("Savings Account for " + client.getName(), client));
            accounts.add(new CurrentAccount("Current Account for " + client.getName(), client));
        }
        return accounts;
    }

    public static void displayAccounts(List<Account> accounts) {
        String allAccounts = accounts.stream()
                .map(Account::toString)
                .collect(Collectors.joining("\n"));
        logger.info("\n" + allAccounts);
    }

    // 1.3.1 Adaptation of the table of accounts
    // Using HashMap instead of Hashtable since there's no need for synchronization
    static HashMap<Integer, Account> accountHashMap = new HashMap<>();

    public static Map<Integer, Account> convertAccountToHashMap(List<Account> accounts) {
        for (Account account : accounts) {
            accountHashMap.put(account.getAccountNumber(), account);
        }
        return accountHashMap;
    }

    public static void displayAccountsSorted(Map<Integer, Account> accountHashMap) {
        List<Account> sortedAccounts = accountHashMap.values().stream()
                .sorted(Comparator.comparingDouble(Account::getBalance))
                .collect(Collectors.toList());
        String allAccountsSorted = sortedAccounts.stream()
                .map(Account::toString)
                .collect(Collectors.joining("\n"));
        logger.info("\n" + allAccountsSorted);
    }

    // 1.3.4 Creation of the flow array
    static List<Flow> flows = new ArrayList<>();

    public static List<Flow> generateFlows(List<Account> accounts) {

        flows.add(new Debit("Debit from account 1", "Debit1", 50, 1, true, LocalDate.now().plusDays(2)));

        for (Account account : accounts) {
            if (account instanceof CurrentAccount) {
                flows.add(new Credit("Credit to current account", "CreditCurrent" + account.getAccountNumber(), 100.50, account.getAccountNumber(), true, LocalDate.now().plusDays(2)));
            }
        }

        for (Account account : accounts) {
            if (account instanceof SavingsAccount) {
                flows.add(new Credit("Credit to savings account", "CreditSavings" + account.getAccountNumber(), 1500, account.getAccountNumber(), true, LocalDate.now().plusDays(2)));
            }
        }

        flows.add(new Transfer("Transfer from account 1 to account 2", "Transfer1to2", 50, 2, true, LocalDate.now().plusDays(2), 1));

        return flows;
    }

    // 1.3.5 Updating accounts
    public static void updateAccounts(List<Flow> flows, Map<Integer, Account> accountHashMap) {
        for (Flow flow : flows) {
            Account account = accountHashMap.get(flow.getTargetAccountNumber());
            if (account != null) {
                account.setBalance(flow);
            }
        }

        Optional<Account> negativeBalanceAccount = accountHashMap.values().stream()
                .filter(account -> account.getBalance() < 0)
                .findFirst();

        if (negativeBalanceAccount.isPresent()) {
            logger.warning("\nThere's an account with a negative balance.");
        }

        displayAccountsSorted(accountHashMap);
    }

    // 2.1 JSON file of flows
    public static List<Flow> getFlowsFromJson(String filePath) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Path path = Paths.get(filePath);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return objectMapper.readValue(reader, objectMapper.getTypeFactory().constructCollectionType(List.class, Flow.class));
        }
    }

    // 2.2 XML file of account
    public static List<Account> getAccountsFromXml(String filePath) throws IOException, JAXBException {
        Path path = Paths.get(filePath);
        JAXBContext jaxbContext = JAXBContext.newInstance(AccountsWrapper.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            AccountsWrapper accounts = (AccountsWrapper) unmarshaller.unmarshal(reader);
            return accounts.getAccounts();
        }
    }
}