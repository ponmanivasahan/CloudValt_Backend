package com.cloudstorage.repository;

import com.cloudstorage.model.File;
import com.cloudstorage.model.Star;
import com.cloudstorage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StarRepository extends JpaRepository<Star, UUID> {

    List<Star> findByUser(User user);

    Optional<Star> findByUserAndFile(User user, File file);

    boolean existsByUserAndFile(User user, File file);

    void deleteByUserAndFile(User user, File file);
}
