package com.nexters.mnt.repository;

import com.nexters.mnt.entity.Manitto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ManittoRepository extends JpaRepository<Manitto, Long> {

    @Query(value = "select * from manitto_tb where user_TB_id= ?1", nativeQuery = true)
    Optional<List<Manitto>> findByUser(@Param("userId") String userId);

    @Modifying
    @Query(value = "delete from manitto_tb where room_TB_id = ?1", nativeQuery = true)
    void deleteByRoom(@Param("roomId") Long roomId);

    @Modifying
    @Query(value = "update manitto_tb set manitto_id = :manittoId where room_TB_id = :roomId and user_TB_id = :userId and frutto_id = :fruttoId", nativeQuery = true)
    void updateManittoId( @Param("userId")String userId, @Param("manittoId")String manittoId, @Param("roomId")Long roomId, @Param("fruttoId")int fruttoId);

    @Query(value = "select * from manitto_tb where room_tb_id = ?1", nativeQuery = true)
    Optional<List<Manitto>> findByRoom(@Param("roomId")Long roomId);

    @Query(value = "select * from manitto_tb where room_TB_id = ?1 and is_creater = ?2", nativeQuery = true)
    List<Manitto> findByRoomAndIsCreaterIs(@Param("roomId") Long roomId,@Param("isCreater") int isCreater);

    @Query(value = "select m from Manitto m where m.room.id = :roomId and m.user.id = :userId")
    Optional<Manitto> findByRoomAndUser(@Param("userId")String userId, @Param("roomId")Long roomId);

    @Query("delete from Manitto where room.id = :roomId and user.id = :userId")
    void deleteByRoomIdAndUser(@Param("roomId") Long roomId, @Param("userId") String userId);

    @Query(value = "select count(m) from Manitto m where m.room.id = :roomId")
    int countByRoom(@Param("roomId") Long roomId);

    @Query(value = "select m from Manitto m where m.manittoId = :userId and m.room.id = :roomId")
    Optional<Manitto> findByManittoIdAndRoom(@Param("userId") String userId, @Param("roomId")Long roomId);
}
