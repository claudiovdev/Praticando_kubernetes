package com.projeto.kubernetes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@RestController
@RequestMapping("kubernet")
public class KuberController {

    @GetMapping("/meu-ip")
    public String retornandoIp() {
        try {
            return obterEnderecoIPInstancia();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Erro ao obter o endereço IP da instância", e);
        }
    }

    private static String obterEnderecoIPInstancia() throws UnknownHostException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        return "O endereço IP da instância é: " + ip;
    }

    @GetMapping("/ler")
    public ResponseEntity<String> lerArquivo() {
        try {
            Resource resource = new ClassPathResource("arquivo/mensagem.txt");
            InputStream inputStream = resource.getInputStream();
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A");

            String conteudo = scanner.hasNext() ? scanner.next() : "";

            return ResponseEntity.ok(conteudo);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao ler o arquivo.");
        }
    }
    }



}
