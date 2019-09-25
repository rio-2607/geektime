package com.beautyboss.slogen.easy.client;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import retrofit2.Retrofit;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RetrofitClientFactoryBean implements FactoryBean<Object>, InitializingBean,
        ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(RetrofitClientFactoryBean.class);

    private Class<?> type;

    private String name;

    private String url;

    private double readTimeout;

    private boolean snake;

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(this.name, "Name must be set");
    }

    @Override
    public Object getObject() throws Exception {
        Retrofit.Builder builder = retrofit();
        String serviceIdUrl;
        if (!this.url.startsWith("http")) {
            serviceIdUrl = "http://" + this.url;
        } else {
            serviceIdUrl = this.url;
        }
        return configureClient(builder, serviceIdUrl);
    }

    protected Retrofit.Builder retrofit() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.addConverterFactory(new HttpConvertFactory("utf-8", snake));
        builder.addCallAdapterFactory(CallAdapterFactory.create());
        builder.validateEagerly(true);
        return builder;
    }

    protected Object configureClient(Retrofit.Builder builder,
                                     String serviceIdUrl) {

        builder.baseUrl(serviceIdUrl);

        ConnectionPool pool = new ConnectionPool(180, 5, TimeUnit.MINUTES);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectionPool(pool);

        clientBuilder.dispatcher(new Dispatcher(new ThreadPoolExecutor(5,
                ThreadPoolUtil.MAXIMUMPOOLLSIZE, 3, TimeUnit.MINUTES, new SynchronousQueue<Runnable>())));

        if (log.isDebugEnabled()) {
        }

        double connectTimeout = readTimeout;
        clientBuilder.readTimeout((long) (readTimeout * 1000), TimeUnit.MILLISECONDS);
        clientBuilder.connectTimeout((long) (connectTimeout * 1000), TimeUnit.MILLISECONDS);
        OkHttpClient httpClient = clientBuilder.build();
        httpClient.dispatcher().setMaxRequests(1000);
        httpClient.dispatcher().setMaxRequestsPerHost(300);
        builder.client(httpClient);
        Retrofit retrofit = buildAndSave(builder);
        return retrofit.create(this.type);
    }

    private Retrofit buildAndSave(Retrofit.Builder builder) {
        return builder.build();
    }

    @Override
    public Class<?> getObjectType() {
        return this.type;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(double readTimeout) {
        this.readTimeout = readTimeout;
    }

    public boolean isSnake() {
        return snake;
    }

    public void setSnake(boolean snake) {
        this.snake = snake;
    }

}
