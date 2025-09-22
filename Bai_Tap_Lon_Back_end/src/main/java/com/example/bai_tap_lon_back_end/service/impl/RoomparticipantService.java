package com.example.bai_tap_lon_back_end.service.impl;

import com.example.bai_tap_lon_back_end.model.Roomparticipant;
import com.example.bai_tap_lon_back_end.repository.IRoomparticipantRepository;
import com.example.bai_tap_lon_back_end.service.IRoomparticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomparticipantService implements IRoomparticipantService {
    @Autowired
    private IRoomparticipantRepository iRoomparticipantRepository;

    @Override
    public Iterable<Roomparticipant> findAll() {
        return iRoomparticipantRepository.findAll();
    }

    @Override
    public Optional<Roomparticipant> findById(Long id) {
        return iRoomparticipantRepository.findById(id);
    }

    @Override
    public Roomparticipant save(Roomparticipant roomparticipant) {
        return iRoomparticipantRepository.save(roomparticipant);
    }

    @Override
    public void delete(Long id) {
        iRoomparticipantRepository.deleteById(id);
    }
}
