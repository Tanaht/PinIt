package fr.istic.m2.taa.pinit.repository;

import fr.istic.m2.taa.pinit.domain.InscriptionActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface InscriptionActivityRepository extends JpaRepository<InscriptionActivity, String> {

    List<InscriptionActivity> findAllByUser_Login(String login);


    List<InscriptionActivity> findAllByUser_Id(long userId);

    void deleteById(long inscriptionId);
}
