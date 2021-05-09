package com.smartiq.smartiqtask.Repository;

import com.smartiq.smartiqtask.Model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarDAO extends JpaRepository<Car, Long> {
}
