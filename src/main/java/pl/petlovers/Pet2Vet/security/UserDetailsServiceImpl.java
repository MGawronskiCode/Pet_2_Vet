package pl.petlovers.Pet2Vet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.appUser.AppUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private AppUserRepository userRepository;

  @Autowired
  public UserDetailsServiceImpl(AppUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
    return userRepository.findByLogin(userLogin);
  }

}
