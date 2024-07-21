/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.repo;

import java.util.Optional;
import main.models.LocalUser;
import main.models.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hp
 */
@Repository
public interface VerificationTokenRepo extends ListCrudRepository<VerificationToken,Integer> {
    Optional<VerificationToken> findByToken(String token);
    void deleteByUser(LocalUser user);
}
