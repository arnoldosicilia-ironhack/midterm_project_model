package com.deingun.bankingsystem.repository;

import com.deingun.bankingsystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findById(Long id);

    List<Transaction> findAll();

    List<Transaction> findAllByPaymasterUsername(String username);

    List<Transaction> findAllByOriginAccountId(Long id);


    @Query(value = "SELECT Count,origin_account_id,time_stamp FROM (SELECT DISTINCT count(left(time_stamp,10))as Count,origin_account_id,time_stamp " +
            "FROM transactions where left(time_stamp,10) <> CURDATE() GROUP BY origin_account_id, left(time_stamp,10))AS table_alias " +
            "GROUP BY LEFT(time_stamp,10) ORDER BY Count DESC LIMIT 1;", nativeQuery = true)
    List<Object[]> maxTotalTransactionOneDay ();

    @Query(value = "SELECT Count,origin_account_id,time_stamp FROM (SELECT DISTINCT count(left(time_stamp,10))as Count,origin_account_id,time_stamp " +
            "FROM transactions where left(time_stamp,10) = CURDATE() AND origin_account_id = :id GROUP BY origin_account_id, left(time_stamp,10))AS table_alias " +
            "GROUP BY LEFT(time_stamp,10) ORDER BY Count DESC LIMIT 1;", nativeQuery = true)
    List<Object[]> totalTransactionTodayByAccount (@Param("id") Long id);


    @Query(value = "select origin_account_id,time_stamp from transactions where origin_account_id = :originAccountId AND time_stamp = :timeStamp", nativeQuery = true)
    List<Object[]> getTransactionByAccountAndTimeStamp (@Param("originAccountId") Long accountId, @Param("timeStamp") LocalDateTime timeStamp);

}
