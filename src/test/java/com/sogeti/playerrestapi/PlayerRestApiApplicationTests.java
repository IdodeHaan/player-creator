package com.sogeti.playerrestapi;

import com.sogeti.playerrestapi.entity.PhoneNumber;
import com.sogeti.playerrestapi.entity.PhoneNumberType;
import com.sogeti.playerrestapi.entity.Player;
import com.sogeti.playerrestapi.repo.PhoneNumberRepository;
import com.sogeti.playerrestapi.repo.PlayerRepository;
import com.sogeti.playerrestapi.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;

@SpringBootTest
class PlayerRestApiApplicationTests {

	@Autowired
	PlayerService playerService;

	@Autowired
	PlayerRepository repository;
	@Autowired
	PhoneNumberRepository phoneNumberRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void createPlayer() {
		Player player = new Player();
		player.setFirstName("Jan");
		player.setLastName("Glijer");
		player.setEmail("jan@gmail.com");
		player.setAge(32);
		player.setRanking(6.8);
		playerService.createPlayer(player);
	}

	@Test
	public void findByExample() {
		Player player = new Player();
		player.setFirstName("a");
		player.setLastName("g");
//		player.setEmail("anton@gmail.com");
//		player.setAge(58);
//		player.setRanking(null);

		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
//				.withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withIgnorePaths("lastName", "email", "age", "ranking")
				;
		Example example = Example.of(player, matcher);

		List<Player> playerList = repository.findAll(example);
//		Optional<Player> playerList = repository.findOne(example);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		if (playerList.isPresent()) {
//			System.out.println("er is een player");
//		} else {
//			System.out.println("er is helaas geen player");
//		}
		playerList.forEach(player1 -> System.out.println(player1.getId()));
	}

	@Test
	public void addPhoneNumberToPlayer() {
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setNumber("0611223344");
		phoneNumber.setType(PhoneNumberType.WORK);
		phoneNumberRepository.save(phoneNumber);
		System.out.println(phoneNumber);
	}

	@Test
	public void getPhoneNumber() {
		System.out.println(phoneNumberRepository.findById(8));
	}

}
