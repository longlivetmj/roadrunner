package com.tmj;

import com.tmj.tms.config.datalayer.modal.Authority;
import com.tmj.tms.config.datalayer.service.AuthorityService;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.datalayer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Thilangaj on 9/14/2016 6:11 AM).
 */
@Controller
public class CompanySelectionController {

    private final UserService userService;
    private final CompanyProfileService companyProfileService;
    private final AuthorityService authorityService;

    @Autowired
    public CompanySelectionController(UserService userService,
                                      CompanyProfileService companyProfileService,
                                      AuthorityService authorityService) {
        this.userService = userService;
        this.companyProfileService = companyProfileService;
        this.authorityService = authorityService;
    }

    @RequestMapping(value = "/companySelection", method = RequestMethod.GET)
    public String viewPage(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("companyProfileList", this.companyProfileService.getActiveCompanyListByUsername(principal.getName()));
        model.addAttribute("user", this.userService.findByUsername(principal.getName()));
        return "companySelection";
    }

    @RequestMapping(value = "/companySelection", method = RequestMethod.POST)
    public void loadByCompany(@RequestParam("companyProfileSeq") Integer companyProfileSeq,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              Principal principal) {
        try {
            Set<GrantedAuthority> roles = new HashSet<>();
            List<Authority> authorityList = this.authorityService.getAuthorityListByUsernameAndCompanyProfileSeq(principal.getName(), companyProfileSeq);
            for (Authority authority : authorityList) {
                roles.add(new SimpleGrantedAuthority(authority.getAuthority()));
            }

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                            SecurityContextHolder.getContext().getAuthentication().getCredentials(),
                            roles)
            );
            request.getSession().setAttribute("companyProfileSeq", companyProfileSeq);
            response.sendRedirect("/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
