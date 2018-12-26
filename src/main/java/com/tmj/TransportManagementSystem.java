package com.tmj;

import com.cosium.spring.data.jpa.entity.graph.repository.support.JpaEntityGraphRepositoryFactoryBean;
import com.tmj.tms.fleet.integration.EventsManagementExample;
import com.wialon.core.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = JpaEntityGraphRepositoryFactoryBean.class)
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider", auditorAwareRef = "auditorProvider")
@EnableTransactionManagement
//@EnableCaching // Cache disabled due to issues in transactions by thilanga
@EnableAsync
public class TransportManagementSystem extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TransportManagementSystem.class, args);
    }

    @Override
    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(TransportManagementSystem.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
    }

    @Bean(name = "specificTaskExecutor")
    public TaskExecutor specificTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        return executor;
    }
}
