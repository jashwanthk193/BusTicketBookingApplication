package com.redbus.securitydetails.Controler;



import com.redbus.securitydetails.entity.Role;
import com.redbus.securitydetails.entity.user;
import com.redbus.securitydetails.payload.JWTAuthResponse;
import com.redbus.securitydetails.payload.LoginDto;
import com.redbus.securitydetails.payload.signupDto;
import com.redbus.securitydetails.repository.RoleRepository;
import com.redbus.securitydetails.repository.UserRepository;
import com.redbus.securitydetails.secrity.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("api/auth")
public class Authcontroller {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody signupDto signupDto) {
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            return new ResponseEntity<>("Email already exists" + signupDto.getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (userRepository.existsByUsername(signupDto.getUsername())) {
            return new ResponseEntity<>("Username already exists" + signupDto.getUsername(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        user newUser = new user();
        newUser.setName(signupDto.getName());
        newUser.setEmail(signupDto.getEmail());
        newUser.setUsername(signupDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Set<Role> roles = determineRolesBasedOnEmail(signupDto.getEmail());
        newUser.setRoles(roles);

        userRepository.save(newUser);
        return new ResponseEntity<>("User registered", HttpStatus.CREATED);
    }



//        @PostMapping("/signin")
//        public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
//            try {
//                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                        loginDto.getUsernameOrEmail(), loginDto.getPassword()));
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                // Use the roles as needed
//                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//                System.out.println("User roles: " + authorities);
//
//                return new ResponseEntity<>("User signed in successfully", HttpStatus.OK);
//            } catch (AuthenticationException e) {
//                return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
//            }
  //      }
@PostMapping("/signin")
public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto
                                                                loginDto){
    Authentication authentication = authenticationManager.authenticate(new
            UsernamePasswordAuthenticationToken(
            loginDto.getUsernameOrEmail(), loginDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
// get token form tokenProvider
    String token = tokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JWTAuthResponse(token));
}
    private Set<Role> determineRolesBasedOnEmail(String email) {
        Set<Role> roles = new HashSet<>();
        if (email.endsWith("@admin.com")) {
            roles.add(roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role not found")));
        } else {
            roles.add(roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found")));
        }
        return roles;
    }





    }




