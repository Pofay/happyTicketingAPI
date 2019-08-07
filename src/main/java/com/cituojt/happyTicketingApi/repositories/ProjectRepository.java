package com.cituojt.happyTicketingApi.repositories;

import java.util.Optional;
import com.cituojt.happyTicketingApi.entities.Project;
import com.cituojt.happyTicketingApi.entities.ProjectMemberId;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends CrudRepository<Project, ProjectMemberId> {

    @Query(value = "SELECT project_id, name from project p join project_member m join user u WHERE u.id = :userId AND m.user_id= :userId AND m.project_project_id= p.project_id", nativeQuery = true)
    public Iterable<Project> getProjectsForUser(@Param("userId") Long userId);

    @Query(value = "SELECT * from project p where p.project_id = :projectId", nativeQuery = true)
    public Optional<Project> findById(@Param("projectId") Long projectId);

}
