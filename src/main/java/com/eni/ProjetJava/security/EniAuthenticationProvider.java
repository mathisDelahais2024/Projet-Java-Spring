package com.eni.ProjetJava.security;

import com.eni.ProjetJava.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class EniAuthenticationProvider implements AuthenticationProvider {

    private final UtilisateurService utilisateurService;

    @Autowired
    public EniAuthenticationProvider(@Lazy UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        var utilisateur = utilisateurService.findByEmail(email);

        System.out.println("Tentative de connexion avec : " + email + " / " + password);

        if (!utilisateur.getEmail().equals(email) && !utilisateur.getMotDePasse().equals(password)) {
            throw new UsernameNotFoundException("Email ou mot de passe invalide");
        }

        var userDetails = new EniUserDetails(utilisateur);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                password,
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
