package jpql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Table(name = "ORDERS")
@Entity
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private int orderAmount;

    @Embedded
    private Address address;
}
