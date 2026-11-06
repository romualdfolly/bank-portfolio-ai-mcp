package com.example.bank_portfolio_web.controllers.api;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.bank_portfolio_web.models.Client;
import com.example.bank_portfolio_web.models.Compte;
import com.example.bank_portfolio_web.models.Transaction;
import com.example.bank_portfolio_web.services.BanqueService;

@RestController
@RequestMapping("/api")
public class BankApiController {
    private final BanqueService banqueService;

    public BankApiController(BanqueService banqueService) {
        this.banqueService = banqueService;
    }

    //
    @GetMapping("/clients")
    public List<Client> getClients() throws SQLException {
        return banqueService.listClients();
    }


    //
    @GetMapping("/comptes/{numero}/solde")
    public BigDecimal getSolde(@PathVariable String numero) throws SQLException {
        //
        Compte compte = banqueService.findCompte(numero);

        if (compte == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Compte introuvable"
            );
        }

        return compte.getSolde();
    }

    //
    @GetMapping("/comptes/{numero}/transactions")
    public List<Transaction> getTransactions(@PathVariable String numero) throws SQLException {
        return banqueService.historique(numero);
    }
}
