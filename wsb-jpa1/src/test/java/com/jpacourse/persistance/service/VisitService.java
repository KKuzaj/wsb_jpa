package com.jpacourse.persistance.service;

import com.jpacourse.dto.VisitTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {

//    @Autowired
//    private VisitRepository visitRepository;
//
//    public List<VisitTO> getVisitsByPatientId(Long patientId) {
//        return visitRepository.findByPatientEntityId(patientId)
//                .stream()
//                .map(visit -> new VisitTO())
//                .collect(Collectors.toList());
//    }
}