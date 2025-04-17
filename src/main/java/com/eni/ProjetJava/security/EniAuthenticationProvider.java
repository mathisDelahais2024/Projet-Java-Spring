package com.eni.ProjetJava.security;

import com.eni.ProjetJava.bo.Utilisateur;
import com.eni.ProjetJava.service.ConstanteService;
import com.eni.ProjetJava.service.ReponseService;
import com.eni.ProjetJava.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.eni.ProjetJava.service.ConstanteService.CD_CREATED;

@Component
public class EniAuthenticationProvider implements AuthenticationProvider {

    private final UtilisateurService utilisateurService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EniAuthenticationProvider(UtilisateurService utilisateurService, PasswordEncoder passwordEncoder) {
        this.utilisateurService = utilisateurService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String motDePasse = authentication.getCredentials().toString();

        Utilisateur utilisateur = utilisateurService.findByEmail(email);

        if (utilisateur == null || !passwordEncoder.matches(motDePasse, utilisateur.getMotDePasse())) {
            ReponseService.construireReponse(ConstanteService.CD_ERR_UNAUTHORIZED, "Email ou mot de passe invalide.", null);
        }

        EniUserDetails userDetails = new EniUserDetails(utilisateur);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                motDePasse,
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

