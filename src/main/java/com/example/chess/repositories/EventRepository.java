package com.example.chess.repositories;

import com.example.chess.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event,String> {

}
