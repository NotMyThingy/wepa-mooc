package wad.simplebanking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BankingController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @Transactional
    public String add(@RequestParam String name, @RequestParam String iban) {
        if (name.trim().isEmpty() || iban.trim().isEmpty()) {
            return "redirect:/";
        }

        Client client = clientRepository.findByName(name);
        if (client == null) {
            client = new Client();
            client.setName(name);
            client = clientRepository.save(client);
        }

        Account account = accountRepository.findByIban(iban);
        if (account == null) {
            account = new Account();
            account.setIban(iban);
            account.setBalance(100);
            account = accountRepository.save(account);
        }

        // DO SOMETHING HERE
        client.getAccounts().add(account);

        return "redirect:/";
    }
}
