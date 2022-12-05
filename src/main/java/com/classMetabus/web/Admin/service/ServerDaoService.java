package com.classMetabus.web.Admin.service;

import com.classMetabus.web.Admin.advice.DuplicateException;
import com.classMetabus.web.Admin.domain.IP;
import com.classMetabus.web.Admin.domain.Lecture;
import com.classMetabus.web.Admin.domain.Server;
import com.classMetabus.web.Admin.dto.server.*;
import com.classMetabus.web.Admin.repository.IpRepository;
import com.classMetabus.web.Admin.repository.LectureRepository;
import com.classMetabus.web.Admin.dto.server.FindLectureInfoResponse;
import com.classMetabus.web.Admin.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.classMetabus.web.Admin.advice.isNull.isNull;

@Service
@RequiredArgsConstructor
public class ServerDaoService {
    private final ServerRepository serverRepository;
    private final LectureRepository lectureRepository;
    private final IpRepository ipRepository;

    @Transactional
    public boolean create(CreateServerIPRequest request){
        serverRepository.findByLectureIdAndLecture_DeletedEquals(request.getLectureId(),false).ifPresent(m->{
            String message = "서버 내 강의실 등록을 실패했습니다.";
            throw new DuplicateException(message);
        });

        Integer sumUser = isNull(serverRepository.getSumTotalUser(request.getIpId()));
        Integer lectureUser = isNull(lectureRepository.findMap_MaxUserById(request.getLectureId()).get().getMap().getMaxUser());
        Integer TotalUser = isNull(ipRepository.findMaxUserById(request.getIpId()).get().getMaxUser());

        if(TotalUser < sumUser + lectureUser) return false;

        Lecture lecture = Lecture.builder()
                .id(request.getLectureId())
                .build();
        IP ip = IP.builder()
                .id(request.getIpId())
                .build();
        Server server = Server.builder()
                .ip(ip)
                .lecture(lecture)
                .build();
        serverRepository.save(server);

        return true;
    }
    @Transactional
    public List<ServerIPListResponse> ServerIPList(ServerIPListRequest request) {
        List<ServerIPListResponse> lists;
        if(request.getInstructorId().equals(0)){
            lists = serverRepository.findAll().stream().map(ServerIPListResponse::new).collect(Collectors.toList());
        }
        else{ lists = serverRepository.findByLecture_Instructor_Id(request.getInstructorId()).stream().map(ServerIPListResponse::new).collect(Collectors.toList());}
        return lists;
    }
    @Transactional
    public boolean deleteById(DeleteServerIPRequest request){
        Optional<Server> ops = serverRepository.findTop1ById(request.getId());
        if(ops.isEmpty())
            return false;

        serverRepository.deleteById(request.getId());
        return true;
    }
    @Transactional
    public List<ServerIPListResponse> getServerIPByLectureId(Integer lectureId) {
        return serverRepository.findByLectureIdAndLecture_DeletedEquals(lectureId,false).stream().map(ServerIPListResponse::new).collect(Collectors.toList());
    }
    @Transactional
    public List<FindLectureInfoResponse> findLectureInfo(Integer instructorId){
        List<Integer> servers = serverRepository.findByDistinctServerList();
        //servers 값이 null 인경우, JPA IsNotIN이 적용되지않음
        if(servers.isEmpty()) servers.add(0); 
        LocalDateTime localDateTime = LocalDateTime.now();
        //List<Lecture> s2 = lectureRepository.findByIdIsNotInAndInstructor_IdAndDeletedEqualsAndStartTimeIsAfter(servers,instructorId,false,localDateTime);
        return lectureRepository.findByIdIsNotInAndInstructor_IdAndDeletedEqualsAndEndTimeIsAfter(servers,instructorId,false,localDateTime).stream().map(FindLectureInfoResponse::new).collect(Collectors.toList());
    }
}
