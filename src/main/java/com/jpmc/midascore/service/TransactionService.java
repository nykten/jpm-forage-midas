package com.jpmc.midascore.service;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.repository.TransactionRepository;
import org.h2.engine.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.repository.TransactionRepository;



@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final IncentiveService incentiveService;

    public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository, IncentiveService incentiveService) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.incentiveService = incentiveService;
    }

    public float getUserBalance(String userName) {
        UserRecord user = userRepository.findByName(userName);
        if (user == null) {
//            System.out.println("User " + userName + " not found");
            logger.warn("User not found: {}", userName);
            return 0.0f;
        }
        return user.getBalance();
    }

    public float getUserBalanceById(Long userId) {
        UserRecord user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            System.out.println("User ID " + userId + " not found");
            return 0.0f;
        }
        return user.getBalance();
    }

    @Transactional
    public void processTransaction(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId()).orElse(null);
        UserRecord recipient = userRepository.findById(transaction.getRecipientId()).orElse(null);

        boolean isValid = validateTransaction(sender, recipient,transaction.getAmount());

        // get incentive amount
        Incentive incentive = incentiveService.getIncentive(transaction);
        float incentiveAmount = incentive.getAmount();

        TransactionRecord transactionRecord = new TransactionRecord(sender, recipient,transaction.getAmount(), incentiveAmount, isValid);
        transactionRepository.save(transactionRecord);

        if (isValid) {
            sender.setBalance(sender.getBalance() - transaction.getAmount());
            recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);

            userRepository.save(sender);
            userRepository.save(recipient);
            logger.info("Transaction processed successfully: {}", transactionRecord);
        }
        else {
            logger.info("Transaction processing failed: {}, discarded.", transactionRecord);
        }
    }

    private boolean validateTransaction(UserRecord sender, UserRecord recipient, float amount) {
        if (sender == null || recipient == null) {
//            System.out.println("Invalid transaction: sender or recipient not found");
            logger.warn("Invalid transaction: sender/recipient is null.");
            return false;
        }
        if (sender.getBalance() < amount) {
//            System.out.println("Invalid transaction: sender " + sender.getName() + "balance insufficient: " + sender.getBalance());
            logger.warn("Invalid transaction: sender balance is less than the amount of balance.");
            return false;
        }
        return true;
    }


}
