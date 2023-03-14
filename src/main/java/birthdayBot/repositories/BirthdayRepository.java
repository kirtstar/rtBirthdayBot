package birthdayBot.repositories;

import birthdayBot.entities.Birthday;
import birthdayBot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BirthdayRepository extends JpaRepository<Birthday, Long>, JpaSpecificationExecutor<Birthday> {
    List<Birthday> findAllByUser(User user);
}
