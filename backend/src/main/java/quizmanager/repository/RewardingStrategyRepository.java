package quizmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quizmanager.model.strategy.RewardingStrategy;

@Repository
public interface RewardingStrategyRepository extends JpaRepository<RewardingStrategy, Integer> {

    @Override
    RewardingStrategy getReferenceById(Integer integer);
}
