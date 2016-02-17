package com.repository;

import com.entity.InvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by aautushk on 9/19/2015.
 */
@Repository
public interface IInvitationRepository extends JpaRepository<InvitationEntity, Long> {
    @Query("select a.invitationGuid, a.email, d.content, a.createdAt, a.createdByUser from InvitationEntity a join a.project b join b.translationMap c join c.translations d where a.project.projectGuid=:projectGuid and a.isInvitationAccepted = false and d.field='description'")
    ArrayList<Object[]> getPendingInvitationsForProject(@Param("projectGuid") String projectGuid);



//    List<InvitationEntity> findByRecipientEmailAndIsInvitationAccepted(String recipientEmail, boolean isInvitationAccepted);
//    List<InvitationEntity> findByCreatedByUser(String createdBy);
}
