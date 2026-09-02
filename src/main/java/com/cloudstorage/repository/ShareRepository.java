package com.cloudstorage.repository;

import com.cloudstorage.model.File;
import com.cloudstorage.model.Share;
import com.cloudstorage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShareRepository extends JpaRepository<Share, UUID> {

    List<Share> findByOwner(User owner);

    List<Share> findBySharedWithUser(User sharedWithUser);

    Optional<Share> findByFileAndSharedWithUser(File file, User sharedWithUser);

    boolean existsByFileAndSharedWithUser(File file, User sharedWithUser);
}
