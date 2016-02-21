package com.repository;

import com.entity.InvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aautushk on 9/19/2015.
 */
@Repository
public interface IInvitationRepository extends JpaRepository<InvitationEntity, Long> {
    List<InvitationEntity> findByEmailAndIsAcceptedAndIsDeclined(String invitationGuid, Boolean isAccepted, Boolean isDenied);

    @Query("select a.invitationGuid, a.email, d.content, a.createdAt, a.createdByUser from InvitationEntity a join a.project b join b.translationMap c join c.translations d where a.project.projectGuid=:projectGuid and a.isAccepted = false and a.isDeclined = false and d.field='description' and d.language=:language")
    ArrayList<Object[]> getPendingInvitationsForProject(@Param("projectGuid") String projectGuid, @Param("language") String language);

    @Query("select a.invitationGuid, a.email, d.content, a.createdAt, a.createdByUser from InvitationEntity a join a.project b join b.translationMap c join c.translations d where a.email=:username and a.isAccepted = false and  a.isDeclined = false and d.field='description' and d.language=:language")
    ArrayList<Object[]> getReceivedInvitations(@Param("username") String username, @Param("language") String language);

    InvitationEntity findByInvitationGuid(String guid);
//    List<InvitationEntity> findByRecipientEmailAndIsInvitationAccepted(String recipientEmail, boolean isInvitationAccepted);
//    List<InvitationEntity> findByCreatedByUser(String createdBy);
}
