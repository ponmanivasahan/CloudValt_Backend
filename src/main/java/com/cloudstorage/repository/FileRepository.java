package com.cloudstorage.repository;

import com.cloudstorage.model.File;
import com.cloudstorage.model.Folder;
import com.cloudstorage.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {

    /** Files in root (no folder) for a given owner, not deleted. */
    Page<File> findByOwnerAndFolderIsNullAndDeletedAtIsNull(User owner, Pageable pageable);

    /** Files inside a specific folder, not deleted. */
    Page<File> findByFolderAndDeletedAtIsNull(Folder folder, Pageable pageable);

    /** Non-deleted file by ID. */
    Optional<File> findByIdAndDeletedAtIsNull(UUID id);

    /** Soft-deleted files owned by user (trash). */
    List<File> findByOwnerAndDeletedAtIsNotNull(User owner);

    /** Search by name fragment, owned by user, not deleted. */
    Page<File> findByOwnerAndNameContainingIgnoreCaseAndDeletedAtIsNull(
            User owner, String name, Pageable pageable);
}
