package com.projeto.kubernetes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("kubernetes")
public class KuberController {


    private final ResourceLoader resourceLoader;

    @Autowired
    private ConfigurableApplicationContext context;

    public KuberController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

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
        String caminhoDoArquivo = "/src/main/resources/arquivo/mensagem.txt";

        try {
            File arquivo = new File(caminhoDoArquivo);

            FileReader leitorArquivo = new FileReader(arquivo);

            BufferedReader leitorBuffer = new BufferedReader(leitorArquivo);

            StringBuilder conteudo = new StringBuilder();
            String linha;

            while ((linha = leitorBuffer.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }

            leitorBuffer.close();
            leitorArquivo.close();

            return ResponseEntity.ok(conteudo.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao ler o arquivo.");
        }
    }

    @PostMapping("/matar-servico")
    public void matarMaquina(){
        context.close();;
    }



}
