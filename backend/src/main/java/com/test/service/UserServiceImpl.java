package com.test.service;

import com.test.dao.UserRepository;
import com.test.dto.auth.LoginResponse;
import com.test.model.Role;
import com.test.model.User;
import com.test.utils.auth.CookieUtil;
import com.test.utils.auth.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

import static com.test.constants.Constants.*;
import static com.test.constants.enums.OperationStatus.SUCCESS;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final JwtUtil jwtUtil;

    @Override
    @Cacheable(value = "loggedInUsers", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository
                .findByUsernameAndActive(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
    }

    @Override
    public String getUserameByRandomStringAndPublicKeyAndHash(String randomStr, String publicKey, String hash) {
        return repository.retrieveUsernameByPublicKeyAndPrivateKey(randomStr, publicKey, hash);
    }

    @Override
    public ResponseEntity<LoginResponse> getLoginResponse(User user) {
        HttpHeaders responseHeaders = new HttpHeaders();
        String accessToken = jwtUtil.createAccessToken(user);
        String refreshToken = jwtUtil.createRefreshToken(user.getUsername());
        CookieUtil.addCookieToHttpHeader(responseHeaders, ACCESS_TOKEN_NAME, accessToken, ACCESS_TOKEN_DURATION_MILLISECONDS);
        CookieUtil.addCookieToHttpHeader(responseHeaders, REFRESH_TOKEN_NAME, refreshToken, REFRESH_TOKEN_DURATION_MILLISECONDS);
        List<Role> roles = user.getRoles();
        List<String> authorities = roles.stream().map(Role::getName).toList();
        List<String> permissions = new LinkedList<>();
        LoginResponse loginResponse = new LoginResponse(
                user.getUsername(),
                authorities,
                permissions,
                accessToken,
                SUCCESS.toString(),
                "Login successful.");
        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }

    @Override
    public ResponseEntity<LoginResponse> refreshAuthTokens(String refreshToken) {
        Claims claimsSet = jwtUtil.verifyToken(refreshToken);
        User user = (User) loadUserByUsername(claimsSet.getSubject());
        return getLoginResponse(user);
    }

}
