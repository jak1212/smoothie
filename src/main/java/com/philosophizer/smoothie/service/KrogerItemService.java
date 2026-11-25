package com.philosophizer.smoothie.service;

import com.philosophizer.smoothie.connectivity.kroger.KrogerClient;
import com.philosophizer.smoothie.model.KrogerItem;
import com.philosophizer.smoothie.repository.KrogerItemRepository;
import com.philosophizer.smoothie.utils.SearchTermNormalizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class KrogerItemService {


    @Autowired
    private KrogerClient krogerClient;

    @Autowired
    private SearchTermNormalizer searchTermNormalizer;

    @Autowired
    private KrogerItemRepository krogerItemRepository;

    private final RestClient restClient = RestClient.create();


    public KrogerItemService(){}

    public void saveKrogerItem(KrogerItem krogerItem)
    {
        krogerItemRepository.save(krogerItem);
    }


}
