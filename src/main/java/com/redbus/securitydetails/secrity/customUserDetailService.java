package com.redbus.securitydetails.secrity;

import com.redbus.securitydetails.entity.Role;


import com.redbus.securitydetails.entity.user;
import com.redbus.securitydetails.repository.RoleRepository;
import com.redbus.securitydetails.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class customUserDetailService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public customUserDetailService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        user user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));

        // Check the email format to determine the role
        Set<Role> roles = determineRolesBasedOnEmail(user.getEmail());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), mapRolesToAuthorities(roles));
    }

    // Method to determine roles based on email format
    private Set<Role> determineRolesBasedOnEmail(String email) {
        Set<Role> roles = new HashSet<>();
        if (email.endsWith("@admin.com")) {
            roles.add(roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role not found")));
        } else {
            roles.add(roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found")));
        }
        return roles;
    }

    // Method to map roles to authorities
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
