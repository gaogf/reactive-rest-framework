package com.client.fluxclient.api;


import com.client.fluxclient.annotation.ApiServer;
import com.client.fluxclient.bean.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @ClassName IUserApi
 * @Description TODO
 * @Author gaogf
 * @Date 2018/5/19 16:01
 * @Version 1.0
 */
@ApiServer("http://localhost:8080/user")
public interface IUserApi {
    @GetMapping("/")
    Flux<User> getAllUser();

    @GetMapping("/{id}")
    Mono<User> getUserById(@PathVariable("id") String id);

    @DeleteMapping("/{id}")
    Mono<Void> deleteUserById(@PathVariable("id") String id);

    @PostMapping("/")
    Mono<Void> createUser(@RequestBody Mono<User> user);
}
