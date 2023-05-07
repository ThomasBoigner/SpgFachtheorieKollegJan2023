package at.spengergasse.part2.persistence;

import at.spengergasse.part2.domain.ListenedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListenedItemRepository extends JpaRepository<ListenedItem, Long> {
}
