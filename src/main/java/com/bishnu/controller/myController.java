package com.bishnu.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bishnu.Entity.AppUser;
import com.bishnu.Entity.RegisterDto;
import com.bishnu.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class myController {
	
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/")
	public String openHome(){
		return "index";
		
	}
	

@GetMapping("/myProfile")
public String myprofile(){
	
	return "profile";
}

@GetMapping("/login")
public String login(Model model) {
	model.addAttribute("appUser", new AppUser());
	       return "login";
}
@GetMapping("/editview")

public String editview(){
	
	return "editpage";
}

@GetMapping("/logout")
public String logoutPage(HttpServletRequest request) {
    HttpSession session=request.getSession();
           session.removeAttribute("valuser");
           session.setAttribute("error","User loged out Successfully");
    return "redirect:/loginPage"; // this resolves to /WEB-INF/views/login.jsp
}
    

@PostMapping("/loginPage")
public String logmein(@ModelAttribute AppUser user,BindingResult result, Model model, HttpSession session) {
    
AppUser validatedUser= userService.validate(user.getEmail());
    if (validatedUser!=null && validatedUser.getPassword().equals(user.getPassword())) {
          	
       Iterable<AppUser> user1= userService.searchAll();
       session.setAttribute("alluser",user1);
       session.setAttribute("valuser", validatedUser);
      
       
       return "redirect:/myProfile";
    }
    else {
			result.addError(new FieldError("appUser","email","Username or password Incorrect"));
    	}
	    	if(result.hasErrors())
			{
				return "login";
			}
       return "redirect:/login"; 
    }

@GetMapping("/registerPage")
public String showRegisterPage(Model model) {
    model.addAttribute("RegisterDto", new RegisterDto());
    return "register"; 
}

@PostMapping("/registerForm")
public String register(@Valid @ModelAttribute RegisterDto registerDto, Model Model,BindingResult result) throws Exception {

AppUser checkExistingUser= userService.validate(registerDto.getEmail());

	if(checkExistingUser!=null )
{
		result.addError(new FieldError("registerDto","email","This email address is already used"));
		
}
	if(result.hasErrors())
	{
		return "redirect:/registerPage";
	}
{ 
	AppUser newUser=new AppUser();
	newUser.setFname(registerDto.getFname());
	newUser.setLname(registerDto.getLname());
	newUser.setEmail(registerDto.getEmail());
	newUser.setMobile(registerDto.getMobile());
	newUser.setPassword(registerDto.getPassword()+"123");
	newUser.setRole("client");
	userService.registerNewUser(newUser);			
   return "redirect:/login";
   }
}


@RequestMapping(path="/deleteUser/{delUserId}")
public String deleteUser(@PathVariable int delUserId, HttpSession session) 
{
	AppUser user=(AppUser)session.getAttribute("valuser");
	int check=user.getId();
	if(check==delUserId)
	{
		session.setAttribute("delmsg","Cannot delete Same User");
			
	}
	else
	{
	userService.deleteUser(delUserId);
	session.setAttribute("delmsg","User Deleted Successfully");
	
	}
	Iterable< AppUser> user1= userService.searchAll();
    session.setAttribute("alluser",user1);
		return "redirect:/myProfile";
}

@RequestMapping(value="/editUser/{editUserId}")
public String editUserDetail(@PathVariable int editUserId, Model model) throws Exception
{	
	Optional<AppUser> editUserDetail=userService.findUserById(editUserId);
	model.addAttribute("registerDto",editUserDetail.get());
	return "editpage";
	}

@GetMapping("/updateUser")
public String UpdateUser(@ModelAttribute AppUser user,HttpSession session)
{
	userService.registerNewUser(user);
	session.setAttribute("delmsg","User: "+user.getEmail()+" Edited Successfully");
	Iterable< AppUser> user1= userService.searchAll();
    session.setAttribute("alluser",user1);
	return "redirect:/myProfile";
	}


}
