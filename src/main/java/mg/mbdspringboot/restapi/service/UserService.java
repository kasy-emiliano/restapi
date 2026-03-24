package mg.mbdspringboot.restapi.service;

import lombok.RequiredArgsConstructor;
import mg.mbdspringboot.restapi.model.User;
import mg.mbdspringboot.restapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    public List<User> findAll() { return userRepository.findAll(); }
    
    public Optional<User> findById(Long id) { return userRepository.findById(id); }
    
    public User save(User user) { return userRepository.save(user); }
    
    public void deleteById(Long id) { userRepository.deleteById(id); }
}