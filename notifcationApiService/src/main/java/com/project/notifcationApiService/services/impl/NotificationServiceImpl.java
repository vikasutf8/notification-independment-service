package com.project.notifcationApiService.services.impl;

import com.project.notifcationApiService.constant.ErrorMessages;
import com.project.notifcationApiService.dao.interfaces.TemplateDao;
import com.project.notifcationApiService.exception.ResourceNotFoundException;
import com.project.notifcationApiService.exception.ValidationException;
import com.project.notifcationApiService.models.entity.Template;
import com.project.notifcationApiService.models.request.SendNotificationRequest;
import com.project.notifcationApiService.models.request.kafka.InjestTopicDto;
import com.project.notifcationApiService.models.response.SendNotificationResponse;
import com.project.notifcationApiService.pubsub.publisher.GenericPublisher;
import com.project.notifcationApiService.services.interfaces.NotificationService;
import com.project.notifcationApiService.utils.commonHelper.UtilsMehtods;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.project.notifcationApiService.utils.commonHelper.UtilsMehtods.getCurrentTimeInMillis;
import static com.project.notifcationApiService.utils.commonHelper.UtilsMehtods.getRequestIDContext;

/**
 * Service implementation for notification operations.
 * Handles template lookup (tenant-scoped) and variable resolution.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(.+?)}}");

    private final TemplateDao templateDao;
    private final GenericPublisher genericPublisher;

    @Value("${app.pubsub.kafka.topic:ingest}")
    private String ingestTopic;

    /**
     * Send a notification using a stored template.
     * 1. Lookup template by id + tenantId via DAO — 404 if not found.
     * 2. Validate all template variables are supplied in dynamicVariables.
     * 3. Resolve {{placeholders}} in messageTemplate and return.
     *
     * @param request the send notification request DTO
     * @return the resolved notification response
     * @throws ResourceNotFoundException if template with id not found for tenant
     * @throws ValidationException if required template variables are missing
     */
    @Override
    public SendNotificationResponse sendNotification(
            SendNotificationRequest request) {

        // 1. Extract tenant ID
        var tenantId = UtilsMehtods.getCurrentTenantId();

        // 2. Find template for this tenant
        Template template = templateDao
                .findByIdAndTenantId(
                        request.getTemplateId(),
                        tenantId
                )
                .orElseThrow(() -> {

                    // Template not found -> publish audit event
                    genericPublisher.sendDataToAudit(request);

                    // Then throw exception
                    return new ResourceNotFoundException(
                            String.format(
                                    ErrorMessages.TEMPLATE_NOT_FOUND,
                                    request.getTemplateId()
                            )
                    );
                });

        // 3. Template exists -> build ingest event
        InjestTopicDto injestTopicDto = InjestTopicDto.builder()
                .requestId(getRequestIDContext())
                .tenantId(tenantId.toString())
                .recivedAt(getCurrentTimeInMillis())
                .templateId(request.getTemplateId())
                .notificationType(request.getNotificationType())
                .dynamicVariables(request.getDynamicVariables())
                .build();

        // 4. Publish to ingest topic
        // Processor service will consume this event
        genericPublisher.sendDataToInjest(injestTopicDto);

        // 5. Return response
        return SendNotificationResponse.builder()
                .templateId(request.getTemplateId())
                .templateName(template.getMessageTemplate())
                .notificationType(request.getNotificationType())
                .message("ingest topic set")
                .build();
    }

}
