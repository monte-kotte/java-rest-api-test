package monte.service.user.mapper;

import monte.api.model.user.User;
import monte.service.user.repository.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToApi(UserEntity dbEntity);

    UserEntity mapToDb(User apiEntity);

}
