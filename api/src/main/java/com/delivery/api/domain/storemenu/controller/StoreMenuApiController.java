package com.delivery.api.domain.storemenu.controller;

import com.delivery.api.common.api.Api;
import com.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store-menus")
public class StoreMenuApiController {
    private final StoreMenuBusiness storeMenuBusiness;

    @GetMapping("/stores/{storeId}")
    public Api<List<StoreMenuResponse>> search(@PathVariable Long storeId) {
        var response = storeMenuBusiness.search(storeId);
        return Api.OK(response);
    }
}
