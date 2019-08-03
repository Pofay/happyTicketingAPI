package com.cituojt.happyTicketingApi.repositories;

import com.cituojt.happyTicketingApi.entities.Project;
import com.cituojt.happyTicketingApi.entities.ProjectMemberId;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, ProjectMemberId> {

}
