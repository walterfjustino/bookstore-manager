package com.api.bookstoremanager.users.service;

import com.api.bookstoremanager.users.dto.AuthenticatedUser;
import com.api.bookstoremanager.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationService implements UserDetailsService {

  @Autowired
  private UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = repository.findByUsername(username)
            .orElseThrow(()-> new UsernameNotFoundException(String.format("User not found with username %s", username)));
    return new AuthenticatedUser(
            user.getUsername(),
            user.getPassword(),
            user.getRole().getDescription());
  }
}
