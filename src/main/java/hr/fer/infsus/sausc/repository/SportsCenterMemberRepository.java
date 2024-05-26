package hr.fer.infsus.sausc.repository;

import hr.fer.infsus.sausc.model.db.SportsCenterMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportsCenterMemberRepository extends JpaRepository<SportsCenterMember, Long> {
}
