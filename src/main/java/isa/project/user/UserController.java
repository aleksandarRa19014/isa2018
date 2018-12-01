package isa.project.user;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired 
	private HttpSession httpSession;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(
			value = "details/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
		User user = userService.findById(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "update",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(@RequestBody User user) throws Exception {
		Long userId = user.getId();
		User updatedUser = userService.updateUser(userId, user);
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}
	
	@GetMapping(value = "/get/friends/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public List<User> getFriends(@PathVariable("userId") Long id) {
		List<User> friends = userService.findFriends(id);
		return friends;
	}
	
	@GetMapping(value = "/get/non/friends/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public List<User> getNonFriends(@PathVariable("userId") Long id) {
		User u = userService.findById(id);
		List<User> friends = userService.findFriends(id);
		List<User> nonFriends = (List<User>) userService.findAll();
		nonFriends.removeAll(friends);
		nonFriends.remove(u);
		return nonFriends;
	}
	
}
