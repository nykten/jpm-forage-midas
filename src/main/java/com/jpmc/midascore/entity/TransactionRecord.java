package com.jpmc.midascore.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// need to create this new entity class

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue()
    private long id;

    // a user as UserRecord obj as the sender
    @ManyToOne
    private UserRecord sender;

    // another user as UserRecord obj as the recipient
    @ManyToOne
    private UserRecord recipient;

    @Column(nullable = false)
    private float amount;

    @Column
    private float incentiveAmount;

    @Column
    private LocalDateTime timestamp;

    @Column
    private boolean valid;

    protected TransactionRecord() {}

    public TransactionRecord(UserRecord sender, UserRecord recipient, float amount, float incentiveAmount, boolean valid) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.incentiveAmount = incentiveAmount;
        this.timestamp = LocalDateTime.now();
        this.valid = valid;
    }

    public long getId() {
        return id;
    }

    public UserRecord getSender() {
        return sender;
    }

    public UserRecord getRecipient() {
        return recipient;
    }

    public float getAmount() {
        return amount;
    }

    public float getIncentiveAmount() {
        return incentiveAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        return String.format("TransactionRecord[id=%d, sender='%s', recipient='%s', amount='%f', incentiveAmount='%f', valid'%b', timestamp='%s']",
                id, sender.getName(), recipient.getName(), amount, incentiveAmount, valid);

    }
}
