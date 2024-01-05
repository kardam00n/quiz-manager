package quizmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import quizmanager.model.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {
    @Modifying
    @Transactional
    @Query("update Record r set r.prize.id = ?2 where r.id = ?1")
    void updatePrizeId(Integer RecordId, Integer prizeId);

}
