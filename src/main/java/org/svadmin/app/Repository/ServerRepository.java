package org.svadmin.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.svadmin.app.Entity.Server;

public interface ServerRepository extends JpaRepository<Server, Long> {
}
