package com.demo.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;


import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.dto.UserDto;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.repository.UserRepository;
import com.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;




@Controller
public class AuthController {
	
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        List<String> roles = new ArrayList<>();
        for (UserDto userDto : users) {
            User user = userService.findByEmail(userDto.getEmail());
            List<Role> userRoles = user.getRoles();
            if (userRoles != null && !userRoles.isEmpty()) {
                roles.add(userRoles.get(0).getName());
            } else {
                roles.add("N/A");
            }
        }
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        
        return "users";
    }
    
    @GetMapping("/user")
    public String getUserInfo(Model model) {
        
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            
            // Get the first authority in the collection
            GrantedAuthority authority = authorities.iterator().next();
            
            String username = userDetails.getUsername();

            // Load user information using the username or any other required field
            UserDto userDto = userService.findByUsername(username);

            // Add user information to the model
            model.addAttribute("user", userDto);
            model.addAttribute("auth", authority.getAuthority());

            return "user";
        }

        // Handle cases where the user is not logged in, or the authentication information is missing
        return "error";
       
    }
    
    @DeleteMapping("/user/delete")
    public String DeleteUser(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                 
            String email = userDetails.getUsername();

            // Load user information using the username or any other required field
            User user = userService.findByEmail(email);
            
            // Remove the association between the user and their roles
            userService.removeUserRoles(user);

            // Delete user info
            userRepository.delete(user);
            
            SecurityContextHolder.getContext().setAuthentication(null);

            return "redirect:/login?logout";
        }

        // Handle cases where the user is not logged in, or the authentication information is missing
        return "error";
    }
    
    @DeleteMapping("/users/delete")
    String DeleteUsers(@RequestParam("email") String email, Model model) {
    	User user = userService.findByEmail(email);
        
        // Remove the association between the user and their roles
        userService.removeUserRoles(user);

        // Delete user info
        userRepository.delete(user);
    	
    	return "redirect:/users?deleted";
    }
    
    @GetMapping("user/edit")
    public String EditUser(Model model) {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            String username = userDetails.getUsername();

            // Load user information using the username or any other required field
            UserDto userDto = userService.findByUsername(username);

            // Add user information to the model
            model.addAttribute("user", userDto);

            return "edit";
        }

        // Handle cases where the user is not logged in, or the authentication information is missing
        return "error";
    }
    
    @GetMapping("/users/edit")
    String EditUsers(@RequestParam("email") String email, Model model) {
    	UserDto user = userService.findByUsername(email);
    	
    	model.addAttribute("user", user);
    	
    	return "usersEdit";
    }
    
    @PutMapping("/user/edit/save")
    public String SaveUser(@Valid @ModelAttribute("user") UserDto user,
					            BindingResult result,
					            Model model){
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "edit";
        }
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            String email = userDetails.getUsername();
            
            userService.updateUser(user, email);

            // Add user information to the model
            //model.addAttribute("user", user);

            return "redirect:/user/edit?success";
        }

        // Handle cases where the user is not logged in, or the authentication information is missing
        return "error";
        
    }
    
    @PutMapping("/users/edit/save")
    public String SaveUsers(@Valid @ModelAttribute("user") UserDto user,
					            BindingResult result,
					            Model model){
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "usersEdit";
        }
        
        String email = user.getEmail();
        
        userService.updateUser(user, email);
        
        return "redirect:/users/edit?email=" + user.getEmail() + "&success=true";
    }

}
