package com.classMetabus.web.Admin.service;

import com.classMetabus.web.Admin.advice.DuplicateException;
import com.classMetabus.web.Admin.domain.IP;
import com.classMetabus.web.Admin.domain.Server;
import com.classMetabus.web.Admin.dto.ip.CreateIpRequest;
import com.classMetabus.web.Admin.dto.ip.IpListResponse;
import com.classMetabus.web.Admin.repository.IpRepository;
import com.classMetabus.web.Admin.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IpDaoService {
    private final IpRepository ipRepository;
    private final ServerRepository serverRepository;
    @Transactional
    public boolean create(CreateIpRequest request){
        ipRepository.findByAddressOrName(request.getAddress(),request.getName()).ifPresent(m->{
            String message = "아이피 등록을 실패했습니다.";
            throw new DuplicateException(message);
        });

        IP ip = IP.builder()
                .address(request.getAddress())
                .name(request.getName())
                .maxUser(request.getMaxUser())
                .deleted(false)
                .build();

        ipRepository.save(ip);
        return true;
    }

    @Transactional
    public boolean deleteById(Integer id){
        LocalDateTime Time = LocalDateTime.now();
        Optional<Server> server = serverRepository.findByIp_IdAndIp_DeletedEquals(id,false);
        //if(!(server.isPresent() || server.get().getLecture().getEndTime().isAfter(Time) == true)){
        if(server.isEmpty()
                || (server.get().getLecture().getEndTime().isBefore(Time) == true)){
            Optional<IP> ip = ipRepository.findById(id);

            IP deletedIp = ip.get();
            deletedIp.setDeleted(true);
            deletedIp.setAddress(ip.get().getAddress()+"_"+ LocalDateTime.now());
            deletedIp.setName(ip.get().getName()+"_"+ LocalDateTime.now());
            ipRepository.save(deletedIp);

            return true;
        }
        else return false;
    }
    @Transactional
    public List<IpListResponse> list(){
        return ipRepository.findByDeletedEquals(false).stream().map(IpListResponse::new).collect(Collectors.toList());
    }
}
