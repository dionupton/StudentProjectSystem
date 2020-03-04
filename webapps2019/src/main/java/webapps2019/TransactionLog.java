package webapps2019;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/*
    The TransactionLog class which holds details for each student and supervisor transaction. 
*/

@NamedQuery(name = "getAllTransactionLogs", query = "SELECT c FROM TransactionLog c")

@Entity
public class TransactionLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    SystemUser user;

    String transaction_log;

    enum transactionType {
        LOGIN, ACTION
    }

    transactionType TransactionType;

    Timestamp transactionTime;

    public TransactionLog() {

    }

    public TransactionLog(String t, SystemUser su, Timestamp time) {
        transaction_log = t;
        user = su;
        TransactionType = transactionType.ACTION;
        transactionTime = time;
    }

    public TransactionLog(String t, SystemUser su, Timestamp time, Boolean tr) {
        transaction_log = t;
        user = su;
        TransactionType = transactionType.LOGIN;
        transactionTime = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SystemUser getUser() {
        return user;
    }

    public void setUser(SystemUser user) {
        this.user = user;
    }

    public String getTransaction_log() {
        return transaction_log;
    }

    public void setTransaction_log(String transaction_log) {
        this.transaction_log = transaction_log;
    }

    public transactionType getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(transactionType TransactionType) {
        this.TransactionType = TransactionType;
    }

    public transactionType returnLogin() {
        return transactionType.LOGIN;
    }

    public transactionType returnAction() {
        return transactionType.ACTION;
    }

    public Timestamp getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Timestamp transactionTime) {
        this.transactionTime = transactionTime;
    }

}
