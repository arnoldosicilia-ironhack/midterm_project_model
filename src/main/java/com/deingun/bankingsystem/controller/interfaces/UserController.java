package com.deingun.bankingsystem.controller.interfaces;

import com.deingun.bankingsystem.controller.dto.AccountHolderDTO;
import com.deingun.bankingsystem.controller.dto.ThirdPartyDTO;
import com.deingun.bankingsystem.model.user.AccountHolder;
import com.deingun.bankingsystem.model.user.ThirdParty;
import com.deingun.bankingsystem.model.user.User;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface UserController {

    List<User> findAll();

    AccountHolder findById(Long id);

    List<AccountHolder> findAllAccountHolders();

    AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO);

    ThirdParty createThirdParty(ThirdPartyDTO thirdPartyDTO);

}
