package com.talent.graph.notification_service.service.impl;

import com.talent.graph.notification_service.model.WebhookRequest;
import com.talent.graph.notification_service.service.WebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebhookServiceImpl implements WebhookService {


    public void processWebhookAsync(WebhookRequest request) {

    }

}
