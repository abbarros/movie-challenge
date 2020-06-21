package br.com.abbarros.moviechallenge.config.vault;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.vault.config.SecretBackendConfigurer;
import org.springframework.cloud.vault.config.VaultConfigurer;

public class VaultConfig implements VaultConfigurer {

    @Value("${spring.cloud.vault.generic.application-name}")
    private String applicationName;

    @Override
    public void addSecretBackends(SecretBackendConfigurer configurer) {
        configurer.add("secret/" + applicationName);
    }

}
