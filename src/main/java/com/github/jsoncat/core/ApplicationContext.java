package com.github.jsoncat.core;

import com.github.jsoncat.annotation.boot.ComponentScan;
import com.github.jsoncat.common.Banner;
import com.github.jsoncat.core.aop.factory.InterceptorFactory;
import com.github.jsoncat.core.boot.ApplicationRunner;
import com.github.jsoncat.core.config.Configuration;
import com.github.jsoncat.core.config.ConfigurationManager;
import com.github.jsoncat.core.ioc.BeanFactory;
import com.github.jsoncat.core.ioc.DependencyInjection;
import com.github.jsoncat.core.springmvc.factory.RouteMethodMapper;
import com.github.jsoncat.factory.ClassFactory;
import com.github.jsoncat.server.HttpServer;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

/**
 * @Description jsoncat 启动类
 * @Author Goodenough
 * @Date 2021/3/5 16:53
 */
public final class ApplicationContext {

    private static final ApplicationContext APPLICATIONCONTEXT = new ApplicationContext();

    public static ApplicationContext getApplicationContext(){
        return APPLICATIONCONTEXT;
    }

    public void run(Class<?> applicationClass){
        //打印标志
        Banner.print();
        //获得启动类所在包
        String[] packageNames = getPackageNames(applicationClass);
        //扫描组件类并加载
        ClassFactory.loadClass(packageNames);
        //加载路由
        RouteMethodMapper.loadRoutes();
        //创建 bean 并存入 ioc 容器
        BeanFactory.loadBeans();
        //加载配置文件
        loadResources(applicationClass);
        //加载拦截器
        InterceptorFactory.loadInterceptors(packageNames);
        //依赖注入
        DependencyInjection.inject(packageNames);
        //在来自ClassFactory的类上应用bean post处理器。
        //例如，用@Component或@RestController注释的类
        BeanFactory.applyBeanPostProcessors();
        //启动服务监听回调
        callRunners();
   }

    /**
     * 获取应用启动类所在包名
     * @param applicationClass 应用启动类
     * @return 包名
     */
    private static String[] getPackageNames(Class<?> applicationClass){
        ComponentScan annotationScan = applicationClass.getAnnotation(ComponentScan.class);
        return !Objects.isNull(annotationScan) ?
                annotationScan.value() : new String[]{applicationClass.getPackage().getName()};
    }

    /**
     * 加载配置文件
     * @param applicationClass 应用启动类
     */
    private void loadResources(Class<?> applicationClass){
        ClassLoader classLoader = applicationClass.getClassLoader();
        List<Path> filePaths = new ArrayList<>();
        for (String configName : Configuration.DEFAULT_CONFIG_NAMES) {
            URL url = classLoader.getResource(configName);
            if (!Objects.isNull(url)) {
                try {
                    filePaths.add(Paths.get(url.toURI()));
                } catch (URISyntaxException e) {
                }
            }
        }
        ConfigurationManager configurationManager = BeanFactory.getBean(ConfigurationManager.class);
        configurationManager.loadResources(filePaths);
    }

    private void callRunners() {
        List<ApplicationRunner> runners = new ArrayList<>(BeanFactory.getBeansOfType(ApplicationRunner.class).values());
        runners.add(() -> {
            HttpServer httpServer = new HttpServer();
            httpServer.start();
        });
        for (Object runner : new LinkedHashSet<>(runners)) {
            ((ApplicationRunner) runner).run();
        }
    }
}
