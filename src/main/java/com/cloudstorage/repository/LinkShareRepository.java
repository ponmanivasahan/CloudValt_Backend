package com.cloudstorage.repository;

import com.cloudstorage.model.File;
import com.cloudstorage.model.LinkShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkShareRepository extends JpaRepository<LinkShare, UUID> {

    Optional<LinkShare> findByToken(String token);

    List<LinkShare> findByFile(File file);
}
