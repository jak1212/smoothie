package com.philosophizer.demeter.service;

import com.philosophizer.demeter.kroger.KrogerClient;
import com.philosophizer.demeter.model.Kroger.KrogerItemDto;
import com.philosophizer.demeter.model.KrogerItem;
import com.philosophizer.demeter.repository.KrogerItemRepository;
import com.philosophizer.demeter.utils.SearchTermNormalizer;
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

    }








}
