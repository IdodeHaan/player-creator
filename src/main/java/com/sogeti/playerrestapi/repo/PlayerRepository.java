package com.sogeti.playerrestapi.repo;

import com.sogeti.playerrestapi.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    public Page<Player> findPlayerByLastNameContaining(String name, Pageable pageable);
    public Page<Player> findPlayerByEmailContaining(String name,Pageable pageable);
    public Page<Player> findPlayerByLastNameContainsAndEmailContains(String lastName, String email, Pageable pageable);
    public Page<Player> findPlayerByAgeBetween(Integer age1, Integer age2, Pageable pageable);

}


