package ru.hogwarts.school.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Avatar;

import java.util.Optional;
public interface AvatarRepository extends JpaRepository <Avatar, Long> {

    Optional <Avatar> getByStudentId (long studentId);


}
