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
    public SendNotificationResponse sendNotification(SendNotificationRequest request) {
        // Extract tenant ID from context
        var tenantId = UtilsMehtods.getCurrentTenantId();

        // 1. Check template exists for this tenant via id + tenantId
        Template template = templateDao.findByIdAndTenantId(request.getTemplateId(), tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.TEMPLATE_NOT_FOUND, request.getTemplateId())));
    // in or else throw we can also pushing/publising in kafka topic "aduit" and throw expections and return it


//        Map<String, Object> variables = request.getDynamicVariables() == null
//                ? Map.of()
//                : request.getDynamicVariables();

//        // 2. Every variable declared on the template must be supplied
//        List<String> missing = new ArrayList<>();
//        if (UtilsMehtods.isNotEmpty(template.getTemplateVariables())) {
//            for (String key : template.getTemplateVariables().keySet()) {
//                if (!variables.containsKey(key) || variables.get(key) == null) {
//                    missing.add(key);
//                }
//            }
//        }
//        if (!missing.isEmpty()) {
//            throw new ValidationException(
//                    String.format(ErrorMessages.NOTIFICATION_MISSING_VARIABLES,
//                            String.join(", ", missing)),
//                    tenantId
//            );
//        }


        //2. after its existing ...push in ingestdto as we InjestTopicDto vai builder design patter
        InjestTopicDto injestTopicDto = InjestTopicDto.builder()
//                .requestId(request) MDC --distributed tracing
                .requestId(getRequestIDContext())
                .tenantId(tenantId.toString())
                .recivedAt(getCurrentTimeInMillis())
                .templateId(request.getTemplateId())
                .notificationType(request.getNotificationType())
                .dynamicVariables(request.getDynamicVariables())
                .build();
    // pusblish in kafka topic "ingest" for processor service to process and send notification
        boolean published = genericPublisher.sendNotification(ingestTopic, injestTopicDto);
        if (!published) {
            log.error("Failed to publish ingest event for template '{}' (requestId='{}')",
                    request.getTemplateId(), injestTopicDto.getRequestId());
        }

        return SendNotificationResponse.builder()
                .templateId(request.getTemplateId())
                .templateName(template.getMessageTemplate())
                .notificationType(request.getNotificationType())
                .message("ingest topic set")
                .build();
    }


}
