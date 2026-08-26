package com.insurance.insuranceportal.service;

import com.insurance.insuranceportal.dto.LoginRequest;
import com.insurance.insuranceportal.dto.LoginResponse;
import com.insurance.insuranceportal.dto.RegisterRequest;
import com.insurance.insuranceportal.dto.RegisterResponse;
import com.insurance.insuranceportal.entity.Role;
import com.insurance.insuranceportal.entity.User;
import com.insurance.insuranceportal.exception.InvalidCredentialsException;
import com.insurance.insuranceportal.exception.RoleNotFoundException;
import com.insurance.insuranceportal.exception.UserAlreadyExistsException;
import com.insurance.insuranceportal.repository.RoleRepository;
import com.insurance.insuranceportal.repository.UserRepository;
import com.insurance.insuranceportal.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log =
            LoggerFactory.getLogger(UserService.class);

    private static final long TOKEN_EXPIRATION_SECONDS = 86400;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public RegisterResponse registerUser(
            RegisterRequest request
    ) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        log.info(
                "Registration request received for email: {}",
                email
        );

        if (userRepository.existsByEmail(email)) {

            log.warn(
                    "Registration rejected because email already exists: {}",
                    email
            );

            throw new UserAlreadyExistsException(
                    "Email is already registered"
            );
        }

        if (userRepository.existsByPhoneNumber(
                request.getPhoneNumber()
        )) {

            log.warn(
                    "Registration rejected because phone number already exists"
            );

            throw new UserAlreadyExistsException(
                    "Phone number is already registered"
            );
        }

        Role customerRole = roleRepository
                .findByRoleName("CUSTOMER")
                .orElseThrow(() -> {

                    log.error(
                            "Registration failed because CUSTOMER role is missing"
                    );

                    return new RoleNotFoundException(
                            "CUSTOMER role is not configured"
                    );
                });

        String encodedPassword =
                passwordEncoder.encode(
                        request.getPassword()
                );

        User user = new User(
                request.getFirstName().trim(),
                request.getLastName().trim(),
                email,
                encodedPassword,
                request.getPhoneNumber().trim(),
                "ACTIVE",
                customerRole
        );

        User savedUser =
                userRepository.save(user);

        log.info(
                "User registered successfully with userId: {}",
                savedUser.getUserId()
        );

        return new RegisterResponse(
                savedUser.getUserId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getRole().getRoleName(),
                "User registered successfully"
        );
    }

    public LoginResponse loginUser(
            LoginRequest request
    ) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        log.info(
                "Login request received for email: {}",
                email
        );

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> {

                    log.warn(
                            "Login failed for an unregistered email"
                    );

                    return new InvalidCredentialsException(
                            "Invalid email or password"
                    );
                });

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!passwordMatches) {

            log.warn(
                    "Login failed because password did not match for userId: {}",
                    user.getUserId()
            );

            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        if (!"ACTIVE".equalsIgnoreCase(
                user.getStatus()
        )) {

            log.warn(
                    "Login rejected because account is not active. "
                            + "userId: {}, status: {}",
                    user.getUserId(),
                    user.getStatus()
            );

            throw new InvalidCredentialsException(
                    "User account is not active"
            );
        }

        String token =
                jwtService.generateToken(
                        user.getEmail()
                );

        log.info(
                "User logged in successfully. userId: {}, role: {}",
                user.getUserId(),
                user.getRole().getRoleName()
        );

        return new LoginResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().getRoleName(),
                user.getStatus(),
                token,
                "Bearer",
                TOKEN_EXPIRATION_SECONDS,
                "Login successful"
        );
    }
}