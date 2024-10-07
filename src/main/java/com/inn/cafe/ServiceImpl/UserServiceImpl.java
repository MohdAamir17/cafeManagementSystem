package com.inn.cafe.ServiceImpl;

import com.inn.cafe.Constents.CafeConstants;
import com.inn.cafe.Dao.UserDao;
import com.inn.cafe.Jwt.JwtHelper;
import com.inn.cafe.Model.User;
import com.inn.cafe.Service.UserService;
import com.inn.cafe.Utils.CafeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    private UserDetailsService userDetailsService;

    private JwtHelper helper;

    @Override
    public ResponseEntity<String> singUp(Map<String, String> requestBody) {
        logger.info("Start: Inside UserControllerImpl singUp() method");
        try {
            logger.info("Processing sign-up request with data: {}", requestBody);
            if (validateSingUpMap(requestBody)) {
                List<User> all = userDao.findAll();
                Optional<User> optionalUser = userDao.findByEmailIgnoreCase(requestBody.get("email"));
                if (optionalUser.isPresent()) {
                    return CafeUtils.getResponseEntity("Email already exits", HttpStatus.BAD_REQUEST);
                } else {
                    userDao.save(getUserFromMap(requestBody));
                    logger.info("End: Successfully executed singUp() method");
                    return CafeUtils.getResponseEntity("Successfully registered", HttpStatus.OK);
                }
            } else {
                logger.info("End: Successfully executed singUp() method");
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            logger.error("Exception occurred in singUp() method: {}", ex.getMessage(), ex);
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestBody) {
        logger.info("Start: Inside UserControllerImpl login() method");
        try {
            logger.info("Processing login request with data: {}", requestBody);

            // Authenticate the user
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestBody.get("name"), requestBody.get("password"))
            );

            /// If authentication is successful
            if (authenticate.isAuthenticated()) {
                // Load user details
                UserDetails userDetails = userDetailsService.loadUserByUsername(requestBody.get("name"));

                // Generate JWT token
                String token = this.helper.generateToken(userDetails);

                // Retrieve the user object and modify the necessary fields
                User userFromMap = getUserFromMap(requestBody);// Set username
                // Return success response
                return new ResponseEntity<>(userFromMap.toString(), HttpStatus.OK);
            }
            // Return unauthorized status if not authenticated
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

        } catch (Exception ex) {
            logger.error("Exception occurred in login() method: {}", ex.getMessage(), ex);
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public boolean validateSingUpMap(Map<String, String> requestMap) {
        return requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password");
    }

    public User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus(requestMap.get("status"));
        user.setRole(requestMap.get("role"));
        return user;
    }
}
