package business.businesswork.service;

import business.businesswork.domain.Project;

import java.util.Optional;

public interface BusinessService {

    void register() throws Exception;
    void update() throws Exception;
    void delete() throws Exception;

    Optional<Project> findByID() throws Exception;

}
