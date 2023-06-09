package org.jeycode.samples.infra.users.adapters;

import static org.jeycode.samples.infra.aaa_core.utils.Caches.ALL_USERS;
import static org.jeycode.samples.infra.aaa_core.utils.Caches.USERS_CACHE;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeycode.samples.domain.orders.models.OrderStatus;
import org.jeycode.samples.domain.users.models.User;
import org.jeycode.samples.domain.users.ports.UserDataPort;
import org.jeycode.samples.infra.aaa_core.annotations.DatabaseAdapter;
import org.jeycode.samples.infra.users.mapper.UserEntityMapper;
import org.jeycode.samples.infra.users.repositories.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@DatabaseAdapter
public class UserDataAdapter implements UserDataPort {

  private final UserEntityMapper userEntityMapper;
  private final UserRepository userRepository;

  @Cacheable(value = USERS_CACHE, key = "#id", unless = "#result == null")
  @Override
  public Optional<User> getBy(final long id) {
    logger.info("getUserById -- db action -- id: [{}]", id);
    return userRepository.queryById(id).map(userEntityMapper::toDto);
  }

  @Cacheable(value = USERS_CACHE, key = ALL_USERS, unless = "#result == null")
  @Override
  public List<User> getAll() {
    logger.info("getAllUsers -- db action");
    return userRepository.findAll().stream()
        .map(userEntityMapper::toDto)
        .toList();
  }

  @Cacheable(value = USERS_CACHE, key = "#username", condition = "#username.length() < 4", unless = "#result == null")
  @Override
  public List<User> getAllByUsernameStartingWith(final String username) {
    logger.info("getAllUsersByUsernameStartsWith -- db action -- username: [{}]", username);
    return userRepository.findByUsernameStartsWithIgnoreCase(username).stream()
        .map(userEntityMapper::toDto)
        .toList();
  }

  @Cacheable(value = USERS_CACHE, key = "#orderStatus",
      condition = "#orderStatus == T(org.jeycode.samples.domain.orders.models.OrderStatus).ACTIVE", unless = "#result == null")
  @Override
  public List<User> getAllByOrderStatus(final OrderStatus orderStatus) {
    logger.info("getAllUsersByOrderStatus -- db action -- orderStatus: [{}]", orderStatus);
    return userRepository.findByOrdersStatus(orderStatus).stream()
        .map(userEntityMapper::toDto)
        .toList();
  }

  @Caching(evict = {
      @CacheEvict(value = USERS_CACHE, key = "#user.username().charAt(0)"),
      @CacheEvict(value = USERS_CACHE, key = "#user.username().substring(0, 2)"),
      @CacheEvict(value = USERS_CACHE, key = "#user.username().substring(0, 3)"),
      @CacheEvict(value = USERS_CACHE, key = ALL_USERS)
  })
  @Transactional
  @Override
  public void register(final User user) {
    logger.info("""
        registerUser -- db action -- user:
                {}""", user);
    final var newUser = userEntityMapper.toEntity(user);
    userRepository.save(newUser);
  }

  @Caching(evict = {
      @CacheEvict(value = USERS_CACHE, key = "#user.username().charAt(0)"),
      @CacheEvict(value = USERS_CACHE, key = "#user.username().substring(0, 2)"),
      @CacheEvict(value = USERS_CACHE, key = "#user.username().substring(0, 3)"),
      @CacheEvict(value = USERS_CACHE, key = ALL_USERS)
  })
  @Transactional
  @Override
  public void update(final User user) {
    logger.info("""
        updateUser -- db action -- user:
               {}""", user);
    final var userEntity = userRepository.getReferenceById(user.id());
    final var updatedUser = userEntityMapper.partialUpdate(user, userEntity);
    userRepository.save(updatedUser);
  }

  @Override
  public boolean existsBy(final String username) {
    return userRepository.existsByUsernameIgnoreCase(username);
  }


}
