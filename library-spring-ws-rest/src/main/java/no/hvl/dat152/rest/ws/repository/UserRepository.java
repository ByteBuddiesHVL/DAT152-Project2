/**
 * 
 */
package no.hvl.dat152.rest.ws.repository;

import no.hvl.dat152.rest.ws.model.Order;
import org.springframework.data.repository.CrudRepository;

import no.hvl.dat152.rest.ws.model.User;

import java.util.Set;

/**
 * 
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User updateUserByUserid(Long userid, User user);
    Set<Order> retrieveOrdersByUserid(Long userid);
    Order retrieveOrderByUseridAndOrderid(Long userid, Long orderid);
}
