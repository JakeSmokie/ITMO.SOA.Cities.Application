package ru.itmo.jakesmokie.soa.lab01.servlets;

import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.itmo.jakesmokie.soa.lab01.helpers.CitySpecificationParser;
import ru.itmo.jakesmokie.soa.lab01.helpers.ServletHelper;
import ru.itmo.jakesmokie.soa.lab01.models.input.CityInputDto;
import ru.itmo.jakesmokie.soa.lab01.models.input.GovernmentDto;
import ru.itmo.jakesmokie.soa.lab01.services.ICityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/api/cities/*")
public class CityServlet extends HttpServlet {
    @Autowired ICityService cityService;
    @Autowired ServletHelper servletHelper;

    protected AutowireCapableBeanFactory ctx;

    public CityServlet() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        val context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ctx = context.getAutowireCapableBeanFactory();
        ctx.autowireBean(this);
    }

    @Override
    @SneakyThrows
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (servletHelper.isRootPath()) {
            CitySpecificationParser.parse(servletHelper.getCriteria()).apply(
                servletHelper::return400,
                spec -> servletHelper.returnOK(cityService.list(servletHelper.getPageRequest(), spec))
            );

            return;
        }

        val carCode = servletHelper.getLongPathVariable("/car-code/{carCode}/count").orElse(null);
        if (carCode != null) {
            servletHelper.returnOK(cityService.countByCarCode(carCode));
            return;
        }

        val name = servletHelper.getStringPathVariable("/name/starts-with/{name}").orElse(null);
        if (name != null) {
            servletHelper.returnOK(cityService.findAllByNameStartsWith(name, servletHelper.getPageRequest()));
            return;
        }

        val government = servletHelper.getEnumPathVariable("/government/greater-than/{government}", GovernmentDto.class).orElse(null);
        if (government != null) {
            servletHelper.returnOK(cityService.findAllByGovernmentGreaterThan(government, servletHelper.getPageRequest()));
            return;
        }

        val id = servletHelper.getLongPathVariable("/{id}").orElse(null);
        if (id != null) {
            val cityDto = cityService.get(id);
            if (cityDto.isPresent()) {
                servletHelper.returnOK(cityDto.get());
                return;
            }
        }

        servletHelper.return404();
    }

    @Override
    @SneakyThrows
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        servletHelper.deserializeBody(CityInputDto.class)
            .flatMap(servletHelper::validateEntity)
            .flatMap(cityService::create)
            .apply(servletHelper::return400, servletHelper::returnOK);
    }

    @Override
    @SneakyThrows
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        servletHelper.deserializeBody(CityInputDto.class)
            .flatMap(servletHelper::validateEntity)
            .flatMap(cityService::update)
            .apply(servletHelper::return400, servletHelper::returnOK);
    }

    @Override
    @SneakyThrows
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        val id = servletHelper.getLongPathVariable("/{id}").orElse(null);

        if (id == null) {
            servletHelper.return400("Invalid format of id path param");
            return;
        }

        cityService.delete(id).apply(servletHelper::return400, servletHelper::returnOK);
    }
}
