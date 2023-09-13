package tech.api.archref.application.adapters.http.outbound;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tech.api.archref.application.adapters.http.outbound.dto.response.MarvelApiResponse;

@FeignClient(url = "${marvel-api.uri}", name = "MarvelApi")
public interface ICharacterMarvelApi {

    @RequestMapping(method = RequestMethod.GET, value = "/characters/{id}")
    MarvelApiResponse RetrieveCharacterById(@PathVariable Long id,
                                            @RequestParam("ts") String ts,
                                            @RequestParam("apikey") String apikey,
                                            @RequestParam("hash") String hash);
}
