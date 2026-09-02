package com.cloudstorage.repository;

import com.cloudstorage.model.Folder;
import com.cloudstorage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FolderRepository extends JpaRepository<Folder, UUID> {

    /** Root folders owned by a user (no parent, not deleted). */
    List<Folder> findByOwnerAndParentFolderIsNullAndDeletedAtIsNull(User owner);

    /** Child folders of a specific parent folder (not deleted). */
    List<Folder> findByParentFolderAndDeletedAtIsNull(Folder parentFolder);

    /** Find a non-deleted folder by ID. */
    Optional<Folder> findByIdAndDeletedAtIsNull(UUID id);

    /** Soft-deleted folders owned by user (for trash view). */
    List<Folder> findByOwnerAndDeletedAtIsNotNull(User owner);
}
