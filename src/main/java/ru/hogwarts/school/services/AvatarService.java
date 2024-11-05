package ru.hogwarts.school.services;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {
    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;
    Avatar findAvatar(Long studentId);
    List<Avatar> getAvatars (Integer pageNumber, Integer pageSize);
}
