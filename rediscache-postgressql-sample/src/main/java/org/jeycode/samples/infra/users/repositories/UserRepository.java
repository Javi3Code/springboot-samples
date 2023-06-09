package org.jeycode.samples.infra.users.repositories;

import java.util.List;
import java.util.Optional;
import org.jeycode.samples.domain.orders.models.OrderStatus;
import org.jeycode.samples.infra.users.jpa_entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> queryById(final long id);

  boolean existsByUsernameIgnoreCase(final String username);

  List<UserEntity> findByUsernameStartsWithIgnoreCase(final String username);

  List<UserEntity> findByOrdersStatus(final OrderStatus status);

}
