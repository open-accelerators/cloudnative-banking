package com.redhat.bankdemo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

@Entity
@Cacheable
public class Transaction extends PanacheEntity {

  public Long transactionId;
  public BigDecimal amount;
  public BigDecimal balance;
  public Date timeStamp;
  public String accountId;
  public String description;
  
  public Transaction() {

  }

  public static List<Transaction> listByAccountId(String accountId) {
    return Transaction.list("accountId", Sort.by("timeStamp"), accountId);
  }

  public static List<Transaction> listByAccountAndPeriod(String accountId, int year, int month) {
    return Transaction.list(
        "accountId = :accountId AND YEAR(timeStamp) = :year AND MONTH(timeStamp) = :month",
        Sort.by("timestamp"), Parameters.with("accountId", accountId).and("year", year).and("month", month));
  }

}
