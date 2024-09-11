package com.kolos.bookstore.data.repository.impl;

import com.kolos.bookstore.data.dao.UserDao;
import com.kolos.bookstore.data.entity.User;
import com.kolos.bookstore.data.mapper.DataMapper;
import com.kolos.bookstore.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;
    private final DataMapper dataMapper;


    @Override
    public User find(Long id) {
        com.kolos.bookstore.data.dto.UserDto byId = userDao.find(id);
        return dataMapper.toEntity(byId);
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        return userDao.findAll(limit, offset)
                .stream()
                .map(dataMapper::toEntity)
                .collect(Collectors.toList());
    }


    @Override
    public User findByEmail(String email) {
        com.kolos.bookstore.data.dto.UserDto byEmail = userDao.findByEmail(email);
        if (byEmail == null) {
            return null;
        }
        return dataMapper.toEntity(byEmail);
    }

    @Override
    public List<User> findByLastName(String lastName, int limit, int offset) {
        return userDao.findByLastName(lastName, limit, offset).stream()
                .map(dataMapper::toEntity)
                .collect(Collectors.toList());
    }


    @Override
    public int countAll() {
        return userDao.countAll();
    }


    @Override
    public User save(User entity) {
        com.kolos.bookstore.data.dto.UserDto userDto = new com.kolos.bookstore.data.dto.UserDto();
        userDto.setEmail(entity.getEmail());
        userDto.setPassword(entity.getPassword());
        com.kolos.bookstore.data.dto.UserDto saved = userDao.save(userDto);
        return dataMapper.toEntity(saved);
    }

    @Override
    public User update(User entity) {
        com.kolos.bookstore.data.dto.UserDto userDto = dataMapper.toDto(entity);
        com.kolos.bookstore.data.dto.UserDto updated = userDao.update(userDto);
        return dataMapper.toEntity(updated);
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }
}
