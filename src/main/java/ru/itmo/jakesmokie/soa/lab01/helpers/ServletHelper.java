package ru.itmo.jakesmokie.soa.lab01.helpers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequestScope
public class ServletHelper {
    private final ObjectMapper objectMapper;

    public ServletHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<List<String>> getCriteria() {
        val req = request();
        val values = req.getParameterValues("criteria");

        if (values == null) {
            return new ArrayList<>();
        }

        return Arrays.stream(values)
            .map(x -> Arrays.stream(x.split(",")).collect(Collectors.toList()))
            .collect(Collectors.toList());
    }

    public PageRequest getPageRequest() {
        val req = request();

        val page = Parser.parseInt(req.getParameter("page")).orElse(0);
        val size = Parser.parseInt(req.getParameter("size")).orElse(20);
        val sort = parseSort(req.getParameterValues("sort"));

        return PageRequest.of(page, size, sort);
    }

    @SneakyThrows
    public void return400(Object error) {
        val resp = response();

        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setContentType("application/json");
        resp.getWriter().print(objectMapper.writeValueAsString(error));
    }

    @SneakyThrows
    public void return404() {
        val resp = response();
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @SneakyThrows
    public void returnOK(Object entity) {
        val resp = response();

        if (entity == null) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.getWriter().print(objectMapper.writeValueAsString(entity));
    }

    @SneakyThrows
    public boolean isRootPath() {
        val request = request();
        return request.getPathInfo() == null || request.getPathInfo().equals("/");
    }

    @SneakyThrows
    public Optional<String> getStringPathVariable(String template) {
        val request = request();
        val regex = Pattern.compile("\\{.*}").matcher(template).replaceFirst("(.*)");
        val matcher = Pattern.compile(regex).matcher(request.getPathInfo());

        if (matcher.find()) {
            return Optional.of(matcher.group(1));
        } else {
            return Optional.empty();
        }
    }

    @SneakyThrows
    public Optional<Long> getLongPathVariable(String template) {
        return getStringPathVariable(template).flatMap(Parser::parseLong);
    }

    @SneakyThrows
    public <E extends Enum<E>> Optional<E> getEnumPathVariable(String template, Class<E> clazz) {
        return getStringPathVariable(template).flatMap(x -> Parser.parseEnum(x, clazz));
    }

    @SneakyThrows
    public <T> Either<Object, T> deserializeBody(Class<T> clazz) {
        try {
            return Either.right(objectMapper.readValue(request().getReader(), clazz));
        } catch (JsonParseException | JsonMappingException e) {
            return Either.left(e.getMessage());
        }
    }

    @SneakyThrows
    public <T> Either<Object, T> validateEntity(T entity) {
        val factory = Validation.buildDefaultValidatorFactory();
        val validator = factory.getValidator();
        val violations = validator.validate(entity);

        if (violations.size() > 0) {
            return Either.left(violations.stream()
                .map(x -> x.getPropertyPath().toString() + ": " + x.getMessage())
                .collect(Collectors.toList()));
        } else {
            return Either.right(entity);
        }
    }

    private static HttpServletRequest request() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    private static HttpServletResponse response() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }

    private static Sort parseSort(String[] values) {
        if (values == null) {
            return Sort.unsorted();
        }

        val orders = Arrays.stream(values)
            .map(x -> x.split(","))
            .map(x -> new Sort.Order(Sort.Direction.fromString(x[1]), x[0]))
            .collect(Collectors.toList());

        return Sort.by(orders);
    }
}
