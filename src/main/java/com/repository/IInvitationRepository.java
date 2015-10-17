package com.repository;

import com.entity.InvitationEntity;
import com.entity.TranslationEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aautushk on 9/19/2015.
 */
@Repository
public interface IInvitationRepository extends JpaRepository<InvitationEntity, Long> {
    List<InvitationEntity> findByRecipientEmailAndIsInvitationAccepted(String recipientEmail, boolean isInvitationAccepted);
    List<InvitationEntity> findByCreatedByUser(String createdBy);
}
