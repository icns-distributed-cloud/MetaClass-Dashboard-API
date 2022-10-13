package com.classMetabus.web.Admin.service;

import com.classMetabus.web.Admin.advice.DuplicateException;
import com.classMetabus.web.Admin.domain.Instructor;
import com.classMetabus.web.Admin.domain.Lecture;
import com.classMetabus.web.Admin.domain.Map;
import com.classMetabus.web.Admin.dto.map.*;
import com.classMetabus.web.Admin.repository.LectureRepository;
import com.classMetabus.web.Admin.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapDaoService {
    private final MapRepository mapRepository;
    private final LectureRepository lectureRepository;

    @Transactional
    public boolean create(CreateMapRequest request){
         mapRepository.findByName(request.getName()).ifPresent(m->{
            String message = "강의실 맵 생성을 실패했습니다.";
            throw new DuplicateException(message);
        });
        Instructor instructor = Instructor.builder()
                .id(request.getInstructorId())
                .build();
        Map map = Map.builder()
                .name(request.getName())
                .type(request.getType())
                .maxUser(request.getMaxUser())
                .deleted(false)
                .instructor(instructor)
                .build();

        mapRepository.save(map);
        return true;
    }

    @Transactional
    public Boolean deleteById(DeleteMapRequest request){
        Optional<Map> map = mapRepository.findByIdAndDeletedEquals(request.getId(),false);
        List<Lecture> lecture = lectureRepository.findByMapId(request.getId());
        if(lecture.size()>0)
            return false;

        Map deletedLecture = map.get();
        deletedLecture.setDeleted(true);
        deletedLecture.setName(map.get().getName()+"_"+LocalDateTime.now());
        mapRepository.save(deletedLecture);
        return true;
    }

    @Transactional
    public List<MapListResponse> ListByInstructor(MapListRequest request){
        return mapRepository.findByInstructorIdAndDeletedEqualsOrderByCreatedTime(request.getInstructorId(),false).stream().map(MapListResponse::new).collect(Collectors.toList());
    }
    @Transactional
    public Boolean updateById(UpdateMapRequest request){
        Optional<Map> map = mapRepository.findById(request.getId());
        Optional<Map> find4name = mapRepository.findByNameAndIdIsNot(request.getName(),request.getId());

        if(map.isEmpty() ||
                find4name.isPresent() ||
                map.get().getDeleted().equals(true))
            return false;

        Map updatedMap = map.get();
        updatedMap.setType(request.getType());
        updatedMap.setMaxUser(request.getMaxUser());
        updatedMap.setName(request.getName());
        mapRepository.save(updatedMap);
        return true;
    }
}