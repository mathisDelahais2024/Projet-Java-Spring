package com.eni.ProjetJava.dao;

import com.eni.ProjetJava.bo.Utilisateur;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DAOUtilisateur implements IDAOUtilisateur {

    private final List<Utilisateur> utilisateurs = new ArrayList<>();

    public DAOUtilisateur(PasswordEncoder motDePasseEncoder) {
        Utilisateur NouveauUtilisateur = new Utilisateur();
        NouveauUtilisateur.setNoUtilisateur("1");
        NouveauUtilisateur.setPseudo("admin");
        NouveauUtilisateur.setNom("Admin");
        NouveauUtilisateur.setPrenom("Super");
        NouveauUtilisateur.setEmail("admin@eni.fr");
        NouveauUtilisateur.setMotDePasse(motDePasseEncoder.encode("admin123"));
        NouveauUtilisateur.setAdministrateur(true);
        utilisateurs.add(NouveauUtilisateur);
    }

    @Override
    public List<Utilisateur> selectAll() {
        return utilisateurs;
    }

    @Override
    public Utilisateur findByEmail(String email) {
        return utilisateurs.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
    }

    @Override
    public void deleteByEmail(String email){utilisateurs.removeIf(u -> u.getEmail().equalsIgnoreCase(email));}

    @Override
    public void update(Utilisateur utilisateur) {
        for (int i = 0; i < utilisateurs.size(); i++) {
            if (utilisateurs.get(i).getNoUtilisateur().equals(utilisateur.getNoUtilisateur())) {
                utilisateurs.set(i, utilisateur);
                return;
            }
        }
        System.out.println("Utilisateur non trouvé pour mise à jour : " + utilisateur.getNoUtilisateur());
    }

}
