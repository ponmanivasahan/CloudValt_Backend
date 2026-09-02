package com.cloudstorage.repository;

import com.cloudstorage.model.File;
import com.cloudstorage.model.FileVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileVersionRepository extends JpaRepository<FileVersion, UUID> {

    List<FileVersion> findByFileOrderByVersionNumberDesc(File file);

    int countByFile(File file);
}
