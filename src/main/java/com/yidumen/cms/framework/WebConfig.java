package com.yidumen.cms.framework;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.yidumen.cms.view.model.ItemGroup;
import com.yidumen.cms.view.model.ItemModel;
import com.yidumen.cms.view.model.MenuModel;
import com.yidumen.cms.webservice.VideoWebService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = {VideoWebService.class})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        Hibernate4Module module = new Hibernate4Module();
        module.configure(Hibernate4Module.Feature.FORCE_LAZY_LOADING, false);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        converters.add(converter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public ViewResolver initViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
        resolver.setPrefix("/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean(name = "menus")
    public List<MenuModel> getMenuModel() {
        final List<MenuModel> result = new ArrayList<>();
        {
            MenuModel menu = new MenuModel();
            menu.setName("视频管理");
            {
                ItemGroup group = new ItemGroup();
                group.setName("查询与编辑");
                group.addItem(new ItemModel("全部视频列表", "/video/manager"));
                group.addItem(new ItemModel("高级条件查询", "/video/query"));
                group.setPermission(1);
                menu.addItem(group);
            }
            {
                ItemGroup group = new ItemGroup();
                group.setName("发布与维护");
                group.addItem(new ItemModel("添加新视频信息", "/video/create"));
                group.addItem(new ItemModel("发布视频", "/video/publish"));
                group.setPermission(1);
                menu.addItem(group);
            }
            result.add(menu);
        }
        return result;
    }
}
