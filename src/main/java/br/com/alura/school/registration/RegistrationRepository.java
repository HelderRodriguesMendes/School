package br.com.alura.school.registration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByUser_idAndCourse_id(Long userId, Long courseId);

    @Transactional
    @Query(value = "SELECT count(*) FROM REGISTRATION where user_id = ?1", nativeQuery = true)
    Optional<Integer> getQuantityRecords(Long userId);
}
