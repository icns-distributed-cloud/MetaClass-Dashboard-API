package com.classMetabus.web.Admin.service;

import com.classMetabus.web.Admin.domain.Content;
import com.classMetabus.web.Admin.domain.Instructor;
import com.classMetabus.web.Admin.domain.Lecture;
import com.classMetabus.web.Admin.repository.ContentsRepository;
import com.classMetabus.web.Admin.repository.LectureRepository;
import com.classMetabus.web.Admin.dto.content.ContentListRequest;
import com.classMetabus.web.Admin.dto.content.DeleteContentRequest;
import com.classMetabus.web.Admin.dto.content.GetContentInfoByIdRequest;
import com.classMetabus.web.Admin.dto.content.UpdateIdByContentIdRequest;
import com.classMetabus.web.Admin.dto.content.ContentListResponse;
import com.classMetabus.web.Admin.dto.content.CreateContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentsDaoService {
    private final ContentsRepository contentsRepository;
    private final LectureRepository lectureRepository;

    @Value("${app.upload.dir:${user.home}}")
    private String uploadDir;
    @Transactional
    public CreateContentResponse create(MultipartFile file,String directory){
        Optional<Content> opt = contentsRepository.findByName(file.getOriginalFilename());
        CreateContentResponse response = new CreateContentResponse();
        if(opt.isPresent())
            return response;

        Content content = Content.builder()
                .name(file.getOriginalFilename())
                .deleted(false)
                .directory(directory.replace(uploadDir,""))
                .build();
        contentsRepository.save(content);

        Optional<Content> afterSaved = contentsRepository.findByName(file.getOriginalFilename());
        if(afterSaved.isPresent())
        {
            response.setContentId(afterSaved.get().getId());
            response.setDirectory(afterSaved.get().getDirectory());
            return response;
        }
        return response;
    }

    @Transactional
    public boolean deleteById(DeleteContentRequest request){
        Optional<Content> content = contentsRepository.findById(request.getId());
        List<Lecture> lecture = lectureRepository.findByContentId(request.getId());
        if(content.get().getDeleted() == true || lecture.size() > 0)
            return false;

        Content deletedContent = content.get();
        deletedContent.setDeleted(true);
        deletedContent.setName(content.get().getName()+ "_"+LocalDateTime.now());
        contentsRepository.save(deletedContent);
        return true;
    }

    @Transactional
    public List<ContentListResponse> contentList(ContentListRequest request) {
       return contentsRepository.findByInstructor_IdAndDeletedEquals(request.getInstructorId(),false).stream().map(ContentListResponse::new).collect(Collectors.toList());
    }
    @Transactional
    public Boolean updateIdByContentId(UpdateIdByContentIdRequest request){
        Optional<Content> ops = contentsRepository.findById(request.getContentId());
        if(ops.isEmpty() || request.getContentName().equals(""))
            return false;

        Content updatedContent = ops.get();
        Instructor instructor = new Instructor();
        instructor.setId(request.getInstructorId());
        updatedContent.setInstructor(instructor);
        updatedContent.setName(request.getContentName());
        updatedContent.setPlayTime(request.getPlayTime());

        contentsRepository.save(updatedContent);
        return true;
    }
    @Transactional
    public Optional<ContentListResponse> getContentInfoById(GetContentInfoByIdRequest request) {
        return contentsRepository.findById(request.getId()).map(ContentListResponse::new);
    }
}
