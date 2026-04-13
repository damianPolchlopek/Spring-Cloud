package org.example.photoappapiusers.data;


import io.github.resilience4j.retry.annotation.Retry;
import org.example.photoappapiusers.ui.response.AlbumResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "albums-ws")
public interface AlbumsServiceClient {

    @GetMapping("/users/{id}/albums")
    @Retry(name = "albums-ws")
    @CircuitBreaker(name="albums-ws", fallbackMethod = "getAlbumsFallback")
    public List<AlbumResponseModel> getAlbums(@PathVariable("id") String id);

    default List<AlbumResponseModel> getAlbumsFallback(@PathVariable("id") String id, Throwable throwable) {
        System.out.println("Param: " + id);
        System.out.println("Fallback: " + throwable.getMessage());
        return new ArrayList<AlbumResponseModel>();
    }
}
