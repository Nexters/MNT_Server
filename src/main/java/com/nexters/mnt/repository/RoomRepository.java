package com.nexters.mnt.repository;

import com.nexters.mnt.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
