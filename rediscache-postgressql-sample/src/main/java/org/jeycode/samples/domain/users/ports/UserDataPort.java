package org.jeycode.samples.domain.users.ports;

import java.util.List;
import org.jeycode.samples.domain.orders.models.OrderStatus;
import org.jeycode.samples.domain.users.models.User;

public interface UserDataPort {

  List<User> getAll();

  User getBy(final long id);

  List<User> getAllByUsernameBeginsWith(final String username);

  List<User> getAllByOrderStatus(final OrderStatus orderStatus);

  void register(final User user);

  void update(final User user);

}
