package com.softserve.edu.service.provider;

import com.softserve.edu.entity.user.ProviderEmployee;
import com.softserve.edu.entity.user.User;
import com.softserve.edu.repository.ProviderEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.softserve.edu.entity.user.ProviderEmployee.ProviderEmployeeRole.PROVIDER_EMPLOYEE;

@Service
public class ProviderEmployeeService {
    @Autowired
    private ProviderEmployeeRepository providerEmployeeRepository;

    @Transactional
    public void addEmployee(ProviderEmployee providerEmployee) {

        String passwordEncoded = new BCryptPasswordEncoder().encode(providerEmployee.getPassword());
        providerEmployee.setPassword(passwordEncoded);
        providerEmployee.setRole(PROVIDER_EMPLOYEE);
        providerEmployeeRepository.save(providerEmployee);
    }

    @Transactional
    public Page<? extends User> getUsersPagination(Long idOrganization, int pageNumber, int itemsPerPage, String search, String role) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, itemsPerPage);

        if (search==null) {
            return providerEmployeeRepository.findByRoleAndOrganizationId(role, idOrganization, pageRequest);
        } else {
            return providerEmployeeRepository.findByOrganizationIdAndRoleAndLastNameLikeIgnoreCase(idOrganization,role,"%" + search + "%", pageRequest);
          }

    }

    @Transactional
    public ProviderEmployee oneProviderEmployee(String username) {
        return providerEmployeeRepository.getUserByUserName(username);
    }

    @Transactional
    public List<ProviderEmployee> getAllProviders(String role, Long id) {
        return providerEmployeeRepository.getAllProviderUsers(role,id);
    }


}
