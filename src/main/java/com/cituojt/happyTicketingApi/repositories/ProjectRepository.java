package com.cituojt.happyTicketingApi.repositories;

import java.util.Optional;
import com.cituojt.happyTicketingApi.entities.Project;
import com.cituojt.happyTicketingApi.entities.ProjectMemberId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, ProjectMemberId> {

    @Query(value = "SELECT p.project_id, p.project_name, p.channel_id from project p INNER JOIN project_member m ON m.project_project_id = p.project_id INNER JOIN project_user u ON u.user_id = m.user_user_id", nativeQuery = true)
    public Iterable<Project> getProjectsForUser(@Param("userId") Long userId);

    @Query(value = "SELECT * from project p where p.project_id = :projectId", nativeQuery = true)
    public Optional<Project> findById(@Param("projectId") Long projectId);

}
