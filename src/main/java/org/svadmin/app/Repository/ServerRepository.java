package org.svadmin.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.svadmin.app.Entity.Server;

import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server, Long> {
    @Override
    Optional<Server> findById(Long id);
}
