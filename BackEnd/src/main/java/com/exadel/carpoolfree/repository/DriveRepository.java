package com.exadel.carpoolfree.repository;

import com.exadel.carpoolfree.model.Drive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface DriveRepository extends JpaRepository<Drive, Long> {

    @Query(value = "SELECT d " +
            "from Drive d " +
            "left join fetch d.driver dr  "+
            "where d.startTime >= :startTime")
    List<Drive> findAllDriveInFuture(LocalDateTime startTime);

    @Query(value = "SELECT d " +
            "from Drive d " +
            "left join fetch d.driver dr  " +
            "where d.startTime >= :startTime and d.startTime <=:finTime")
    List<Drive> findAllDriveInDateRange(LocalDateTime startTime, LocalDateTime finTime);

    List<Drive> findByDriverId(Long driverId);

}
