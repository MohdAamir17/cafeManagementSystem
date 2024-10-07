package com.inn.cafe.ControllerImpl;

import com.inn.cafe.Constents.CafeConstants;
import com.inn.cafe.Controller.UserController;
import com.inn.cafe.Model.User;
import com.inn.cafe.Service.UserService;
import com.inn.cafe.Utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserControllerImpl implements UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<String> singUp(Map<String, String> requestBody) {
        logger.info("Start: Inside UserControllerImpl singUp() method");
        try {
            ResponseEntity<String> response = userService.singUp(requestBody);
            logger.info("End: Successfully executed singUp() method");
            return response;
        } catch (Exception ex) {
            logger.error("Exception occurred in singUp() method: {}", ex.getMessage(), ex);
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestBody) {
        logger.info("Start: Inside UserControllerImpl login() method");
        try {
            ResponseEntity<String> response = userService.login(requestBody);
            logger.info("End: Successfully executed login() method");
            return response;
        } catch (Exception ex) {
            logger.error("Exception occurred in login() method : {}", ex.getMessage(), ex);
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
