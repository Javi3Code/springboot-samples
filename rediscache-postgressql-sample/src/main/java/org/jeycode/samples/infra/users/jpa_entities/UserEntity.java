package org.jeycode.samples.infra.users.jpa_entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.Set;
import lombok.Data;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.jeycode.samples.infra.orders.jpa_entities.OrderEntity;

@Data
@ToString
@Entity
@Table(indexes = @Index(name = "idx_user_username", columnList = "username"))
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    final UserEntity that = (UserEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String phoneNumber;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @OrderBy("status = 'COMPLETED' DESC")
  private Set<OrderEntity> orders;

}
