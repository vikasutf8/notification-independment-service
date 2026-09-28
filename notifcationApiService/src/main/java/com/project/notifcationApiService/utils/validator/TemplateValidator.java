package com.project.notifcationApiService.utils.validator;

import com.project.notifcationApiService.constant.ErrorMessages;
import com.project.notifcationApiService.exception.ValidationException;
import com.project.notifcationApiService.models.request.TemplateRequest;
import com.project.notifcationApiService.utils.commonHelper.UtilsMehtods;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cross-field validator for template create/update payloads.
 * Bean Validation on the DTO covers presence and sizes;
 * this component covers variable key/value rules and
 * placeholder-vs-declared-variable consistency.
 * Shared by create and update flows.
 */
@Component
public class TemplateValidator {

    public static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(.+?)}}");
    private static final Pattern VARIABLE_KEY_PATTERN = Pattern.compile("^[A-Za-z0-9_]{1,100}$");
    private static final int MAX_VARIABLE_VALUE_LENGTH = 5120;

    /**
     * Validate a template request end to end.
     *
     * @param request the template request DTO
     * @throws ValidationException on any rule violation
     */
    public void validate(TemplateRequest request) {
        if (UtilsMehtods.isEmpty(request.getName()) ||
            UtilsMehtods.isEmpty(request.getTempVariables()) ||
            UtilsMehtods.isEmpty(request.getMessageTemplate())) {
            throw new ValidationException(ErrorMessages.TEMPLATE_EMPTY_FIELDS);
        }

        validateVariables(request.getTempVariables());
        validatePlaceholders(request.getMessageTemplate(), request.getTempVariables().keySet());
    }

    /**
     * Extract {{placeholder}} names from a message template.
     *
     * @param messageTemplate the raw message template
     * @return placeholder names in order of appearance
     */
    public static Set<String> extractPlaceholders(String messageTemplate) {
        Set<String> placeholders = new HashSet<>();
        if (UtilsMehtods.isEmpty(messageTemplate)) {
            return placeholders;
        }
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(messageTemplate);
        while (matcher.find()) {
            placeholders.add(matcher.group(1).trim());
        }
        return placeholders;
    }

    private void validateVariables(Map<String, String> variables) {
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            if (UtilsMehtods.isEmpty(entry.getKey()) ||
                !VARIABLE_KEY_PATTERN.matcher(entry.getKey()).matches()) {
                throw new ValidationException(
                        String.format(ErrorMessages.TEMPLATE_VARIABLE_KEY_INVALID, entry.getKey()));
            }
            if (UtilsMehtods.isEmpty(entry.getValue())) {
                throw new ValidationException(
                        String.format(ErrorMessages.TEMPLATE_VARIABLE_VALUE_BLANK, entry.getKey()));
            }
            if (entry.getValue().length() > MAX_VARIABLE_VALUE_LENGTH) {
                throw new ValidationException(
                        String.format(ErrorMessages.TEMPLATE_VARIABLE_VALUE_TOO_LONG, entry.getKey()));
            }
        }
    }

    private void validatePlaceholders(String messageTemplate, Set<String> declared) {
        Set<String> used = extractPlaceholders(messageTemplate);
        for (String placeholder : used) {
            if (!declared.contains(placeholder)) {
                throw new ValidationException(
                        String.format(ErrorMessages.TEMPLATE_UNDECLARED_VARIABLE, placeholder));
            }
        }
        for (String variable : declared) {
            if (!used.contains(variable)) {
                throw new ValidationException(
                        String.format(ErrorMessages.TEMPLATE_UNUSED_VARIABLE, variable));
            }
        }
    }
}
