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

    @Query(value = "SELECT projectId, projectName, channelId from project p join project_member m join users u WHERE u.id = :userId AND m.userId= :userId AND m.projectId = p.projectId", nativeQuery = true)
    public Iterable<Project> getProjectsForUser(@Param("userId") Long userId);

    @Query(value = "SELECT * from project p where p.projectId = :projectId", nativeQuery = true)
    public Optional<Project> findById(@Param("projectId") Long projectId);

}
