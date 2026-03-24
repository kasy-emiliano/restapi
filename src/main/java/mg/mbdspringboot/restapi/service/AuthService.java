package mg.mbdspringboot.restapi.service;

import mg.mbdspringboot.restapi.dto.JwtResponse;
import mg.mbdspringboot.restapi.dto.LoginRequest;
import mg.mbdspringboot.restapi.dto.SignupRequest;
import mg.mbdspringboot.restapi.model.User;
import mg.mbdspringboot.restapi.repository.UserRepository;
import mg.mbdspringboot.restapi.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        User userDetails = userRepository.findByUsername(userPrincipal.getUsername())
                .orElseThrow(() -> new RuntimeException("Error: User not found after authentication"));
        
        return new JwtResponse(jwt, 
                userDetails.getId(), 
                userDetails.getUsername(), 
                userDetails.getEmail(),
                userDetails.getRole().name());
    }

    public void registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Error: Email est deja utilisé!");
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setRole(User.Role.CLIENT);

        userRepository.save(user);
    }
}
