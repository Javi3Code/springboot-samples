package org.jeycode.samples.infra.users.repositories;

import java.util.List;
import java.util.Set;
import org.jeycode.samples.domain.orders.models.OrderStatus;
import org.jeycode.samples.infra.users.jpa_entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  UserEntity findByUsernameStartsWithIgnoreCase(final String username);

  List<UserEntity> findByOrdersStatus(final OrderStatus status);

}
