package ma.enset.ebanking.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Date createdAt;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<BankAccount> bankAccounts;

    public Customer() { }

    public Customer(String name, String email, Date createdAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    // Getters and setters...
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public List<BankAccount> getBankAccounts() { return bankAccounts; }
    public void setBankAccounts(List<BankAccount> bankAccounts) { this.bankAccounts = bankAccounts; }
}
