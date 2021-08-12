package com.cituojt.happyTicketingApi.repositories;

import java.util.Optional;
import java.util.UUID;

import com.cituojt.happyTicketingApi.entities.ProjectEntity;
import com.cituojt.happyTicketingApi.entities.ProjectMemberId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, ProjectMemberId> {

    @Query(value = "SELECT p.project_id, p.project_name, p.channel_id from project p INNER JOIN project_member m ON m.project_project_id = p.project_id INNER JOIN project_user u ON u.user_id = m.user_user_id", nativeQuery = true)
    public Iterable<ProjectEntity> getProjectsForUser(@Param("userId") UUID userId);

    @Query(value = "SELECT * from project p where p.project_id = :projectId", nativeQuery = true)
    public Optional<ProjectEntity> findById(@Param("projectId") UUID projectId);

}
