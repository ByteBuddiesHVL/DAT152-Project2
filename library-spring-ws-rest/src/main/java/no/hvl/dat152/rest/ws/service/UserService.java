/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.OrderNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;
import no.hvl.dat152.rest.ws.model.User;
import no.hvl.dat152.rest.ws.repository.UserRepository;

/**
 * @author tdoy
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAllUsers(){
        return (List<User>) userRepository.findAll();
	}
	
	public User findUser(Long userid) throws UserNotFoundException {
        return userRepository.findById(userid)
				.orElseThrow(()-> new UserNotFoundException("User with id: "+userid+" not found"));
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public void deleteUser(Long id) throws UserNotFoundException {
		User user = findUser(id);
		userRepository.delete(user);
	}
	
	public User updateUser(User user, Long id) {
        return userRepository.updateUserByUserid(id, user);
	}
	
	public Set<Order> getUserOrders(Long userid) {
		return userRepository.retrieveOrdersByUserid(userid);
	}
	
	public Order getUserOrder(Long userid, Long oid) {
		return userRepository.retrieveOrderByUseridAndOrderid(userid,oid);
	}
	
	public void deleteOrderForUser(Long userid, Long oid) throws UserNotFoundException, OrderNotFoundException {
		User user = findUser(userid);
		Order order = user.getOrders().stream().filter(o -> Objects.equals(o.getId(), oid)).findFirst()
				.orElseThrow(()-> new OrderNotFoundException("Order with id: "+oid+" not found"));
		user.removeOrder(order);
		saveUser(user);
	}
	
	public User createOrdersForUser(Long userid, Order order) throws UserNotFoundException {
		User user = findUser(userid);
		user.addOrder(order);
		return saveUser(user);
	}
}