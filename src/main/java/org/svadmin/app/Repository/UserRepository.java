package org.svadmin.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.svadmin.app.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsername(String username);
}
