package ru.hogwarts.school.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!production")
public class PortServiceTest implements PortService{

    @Value("${server.port}")
    private Integer port;

    @Override
    public Integer getPort() {
        return port;
    }
}
