package com.project.notifcationApiService.controller.notifications;

import com.project.notifcationApiService.models.request.SendNotificationRequest;
import com.project.notifcationApiService.models.response.SendNotificationResponse;
import com.project.notifcationApiService.services.interfaces.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for notification operations.
 */
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class notificationController {

    private final NotificationService notificationService;

    /**
     * Send a notification using a stored template.
     * Tenant ID comes from request context; template is looked up
     * scoped to that tenant.
     *
     * @param request the send notification request with templateId, variables, type
     * @return ResponseEntity containing the resolved notification
     */
    @PostMapping("/send")
    public ResponseEntity<SendNotificationResponse> sendNotification(
            @Valid @RequestBody SendNotificationRequest request) {

        SendNotificationResponse response = notificationService.sendNotification(request);
        return ResponseEntity.ok(response);
    }

}
