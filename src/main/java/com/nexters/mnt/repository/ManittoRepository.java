package com.nexters.mnt.repository;

import com.nexters.mnt.domain.Manitto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ManittoRepository extends JpaRepository<Manitto, Long> {

    @Query(value = "select * from manitto_TB where user_TB_id= ?1", nativeQuery = true)
    Optional<List<Manitto>> findByUser(String userId);

    @Query(value = "delete from manitto_TB where room_TB_id = :?1", nativeQuery = true)
    void deleteByRoom(Long roomId);

    @Modifying
    @Query(value = "update manitto_TB set manitto_id = :manittoId where room_TB_id = :roomId and user_TB_id = :userId", nativeQuery = true)
    void updateManittoId( @Param("userId")String userId, @Param("manittoId")String manittoId, @Param("roomId")Long roomId);

    @Query(value = "select * from manitto_TB where room_TB_id = ?1", nativeQuery = true)
    Optional<List<Manitto>> findByRoom(@Param("roomId")Long roomId);

    @Query(value = "select * from manitto_TB where room_TB_id = ?1 and is_creater = ?2", nativeQuery = true)
    List<Manitto> findByRoomAndIsCreaterIs(@Param("roomId") Long roomId,@Param("isCreater") int isCreater);

    @Query(value = "select m from Manitto m where m.room.id = :roomId and m.user.id = :userId")
    Manitto findByRoomAndUser(@Param("userId")String userId, @Param("roomId")Long roomId);
}
