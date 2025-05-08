package com.controller;

import com.model.User;
import com.rws.lt.lc.publicapi.sdk.api.GroupApi;
import com.rws.lt.lc.publicapi.sdk.model.Group;
import com.rws.lt.lc.publicapi.sdk.model.ListGroupsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupController {

    @Autowired
    private GroupApi groupApi;


    @GetMapping("/getGroups/{userId}")
    public List<Group> listGroups(@PathVariable("userId") int userId) {
        User user = new User(userId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authentication);

        ListGroupsResponse groupsResponse = groupApi.listGroups(new GroupApi.ListGroupsQueryParams());
        return groupsResponse.getItems();
    }
}
