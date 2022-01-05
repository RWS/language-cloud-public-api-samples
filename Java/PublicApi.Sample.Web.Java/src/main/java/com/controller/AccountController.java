package com.controller;

import com.model.User;
import com.rws.lt.lc.publicapi.sdk.api.AccountApi;
import com.rws.lt.lc.publicapi.sdk.model.Account;
import com.rws.lt.lc.publicapi.sdk.model.ListMyAccountsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestController
public class AccountController extends ResponseEntityExceptionHandler {

    @Autowired
    private AccountApi accountApi;

    @GetMapping("/getAccounts/{userId}")
    public List<Account> listAccounts(@PathVariable("userId") int userId) {
        User user = new User(userId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authentication);

        ListMyAccountsResponse listMyAccountsResponse = accountApi.listMyAccounts();

        return listMyAccountsResponse.getItems();
    }
}
