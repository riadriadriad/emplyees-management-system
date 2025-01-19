package com.test.employeeRecordManagement.services.implementations;

import com.test.employeeRecordManagement.dtos.Response;
import com.test.employeeRecordManagement.dtos.UserDto;
import com.test.employeeRecordManagement.entities.AppUser;
import com.test.employeeRecordManagement.exceptions.BusinessException;
import com.test.employeeRecordManagement.repositories.UserRepository;
import com.test.employeeRecordManagement.services.UserService;
import com.test.employeeRecordManagement.utils.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }


    @Override
    public AppUser getCurrentUser() {
        return this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public Page<AppUser> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public AppUser getUserById(Long id) throws BusinessException {
        return this.userRepository.findById(id).orElseThrow(
                ()->new BusinessException("User not Found", HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public AppUser createUser(UserDto userDto) {
        AppUser user=mapper.map(userDto,AppUser.class);
        logger.info("User created with username: "+user.getUsername()+ " by user: "+getCurrentUser().getUsername());
        return this.userRepository.save(user);
    }

    @Override
    public AppUser updateUser(Long id,UserDto userDto) throws BusinessException {
        AppUser user=getUserById(id);
        user=mapper.map(userDto,AppUser.class);
        user.setId(id);
        logger.info("User updated with username: "+user.getUsername()+ " by user: "+getCurrentUser().getUsername());
        return this.userRepository.save(user);
    }

    @Override
    public Response deleteUser(Long id) throws BusinessException {
        AppUser user=getUserById(id);
        this.userRepository.delete(user);
        logger.info("User deleted with username: "+user.getUsername()+ " by user: "+getCurrentUser().getUsername());
        return new Response();
    }
}
