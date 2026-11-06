package com.example.bank_mcp_server.tools;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.beans.factory.annotation.Value;

@Component
public class BankTools {

    private final RestClient restClient;

    public BankTools(@Value("${bank-app.url}") String bankAppUrl) {
        this.restClient = RestClient.create(bankAppUrl);
    }

    @McpTool(name = "getClients", description = "Retourne la liste des clients de la banque")
    public String getClients() {
        try {
            //
            return restClient.get().uri("clients").retrieve().body(String.class);
        } catch (HttpClientErrorException e) {
            return "Erreur client HTTP : " + e.getStatusCode();
        } catch (RestClientException e) {
            return "Impossible de récupérer la liste des clients.";
        }
    }

    @McpTool(name = "getAccountBalance", description = "Retourne le solde d'un compte bancaire")
    public String getAccountBalance(@McpToolParam(description = "Numéro du compte", required = true) String numero) {
        try {
            //
            BigDecimal montant = restClient.get().uri("comptes/{numero}/solde", numero).retrieve().body(BigDecimal.class);
            return String.format("Le compte %s possède un solde de %.2f CHF.", numero, montant);
        } catch (HttpClientErrorException.NotFound e) {
            return "Compte introuvable : " + numero;
        } catch (RestClientException e) {
            return "Impossible de récupérer le solde du compte : " + numero;
        }
    }


    @McpTool(name = "getAccountTransactions", description = "Retourne l'historique des transactions d'un compte bancaire")
    public String getAccountTransactions(@McpToolParam(description = "Numéro du compte", required = true) String numero) {
        return restClient.get().uri("comptes/{numero}/transactions", numero).retrieve().body(String.class);
    }
}