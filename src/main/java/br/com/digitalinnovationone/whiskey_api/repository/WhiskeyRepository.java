package br.com.digitalinnovationone.whiskey_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.digitalinnovationone.whiskey_api.entity.Whiskey;

@Repository
public interface WhiskeyRepository extends JpaRepository<Whiskey, Long>{

    Optional<Whiskey> findByName(String name);

}
