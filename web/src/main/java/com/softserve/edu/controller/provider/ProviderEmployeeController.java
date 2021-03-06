package com.softserve.edu.controller.provider;

import com.softserve.edu.dto.PageDTO;
import com.softserve.edu.dto.admin.UsersPageItem;
import com.softserve.edu.entity.Organization;
import com.softserve.edu.entity.user.ProviderEmployee;
import com.softserve.edu.entity.user.User;
import com.softserve.edu.service.SecurityUserDetailsService;
import com.softserve.edu.service.admin.OrganizationsService;
import com.softserve.edu.service.admin.UserService;
import com.softserve.edu.service.provider.ProviderEmployeeService;
import com.softserve.edu.service.verification.VerificationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "provider/admin/users/")
public class ProviderEmployeeController {

    Logger logger = Logger.getLogger(ProviderEmployeeController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationsService organizationsService;

    @Autowired
    private ProviderEmployeeService providerEmployeeService;

    @Autowired
    private VerificationService verificationService;

    /**
     * Check whereas {@code username} is available,
     * i.e. it is possible to create new user with this {@code username}
     *
     * @param username username
     * @return {@literal true} if {@code username} available or else {@literal false}
     */
    @RequestMapping(value = "available/{username}", method = RequestMethod.GET)
    public Boolean isValidUsername(@PathVariable String username) {
        boolean isAvailable = false;
        if (username != null) {
            isAvailable = userService.existsWithUsername(username);
        }
        return isAvailable;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> addEmployee(
            @RequestBody ProviderEmployee providerEmployee,
            @AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {

        Organization employeeOrganization = organizationsService.findById(user.getOrganizationId());
        providerEmployee.setOrganization(employeeOrganization);

        providerEmployeeService.addEmployee(providerEmployee);

        return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
    }


    @RequestMapping(value = "{pageNumber}/{itemsPerPage}/{idOrganization}/{search}", method = RequestMethod.GET)
    public PageDTO<UsersPageItem> pageSearchUsers(
            @PathVariable Integer pageNumber,
            @PathVariable Integer itemsPerPage,
            @PathVariable Long idOrganization,
            @PathVariable String search
    ) {
        Page<UsersPageItem> page = providerEmployeeService
                .getUsersPagination(idOrganization, pageNumber, itemsPerPage, search, "PROVIDER_EMPLOYEE")
                .map(
                        new Converter<User, UsersPageItem>() {
                            @Override
                            public UsersPageItem convert(User user) {
                                UsersPageItem usPage = null;

                                if (user instanceof ProviderEmployee) {
                                    usPage = new UsersPageItem();
                                    usPage.setUsername(user.getUsername());
                                    usPage.setRole(user.getRole());
                                    usPage.setFirstName(((ProviderEmployee) user).getFirstName());
                                    usPage.setLastName(((ProviderEmployee) user).getLastName());
                                    usPage.setOrganization(((ProviderEmployee) user).getOrganization().getName());
                                    usPage.setPhone(((ProviderEmployee) user).getPhone());
                                    usPage.setCountOfVarification(verificationService.countByProviderEmployeeTasks(user.getUsername()));
                                }
                                return usPage;
                            }
                        }
                );
        return new PageDTO<>(page.getTotalElements(), page.getContent(),idOrganization);
    }

    @RequestMapping(value = "{pageNumber}/{itemsPerPage}/{idOrganization}", method = RequestMethod.GET)
    public PageDTO<UsersPageItem> getUsersPage(
            @PathVariable Integer pageNumber,
            @PathVariable Integer itemsPerPage,
            @AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        Long idOrganization = user.getOrganizationId();
        return pageSearchUsers(pageNumber, itemsPerPage,idOrganization,null);
    }


}
