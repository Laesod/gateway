package com.repository;

import com.entity.InvitationEntity;
import com.entity.InvitationGroupEntity;
import com.entity.TranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aautushk on 9/19/2015.
 */
@Repository
public interface IInvitationGroupRepository extends JpaRepository<InvitationGroupEntity, Long> {
    List<InvitationGroupEntity> findByCreatedByUser(String createdBy);
}
