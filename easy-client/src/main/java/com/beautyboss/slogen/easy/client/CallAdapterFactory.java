package com.beautyboss.slogen.easy.client;


import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public final class CallAdapterFactory extends CallAdapter.Factory {
    private final static Logger logger = LoggerFactory.getLogger(CallAdapterFactory.class);


    public static CallAdapterFactory create() {
        return new CallAdapterFactory();
    }

    private CallAdapterFactory() {
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new BodyCallAdapter<>(returnType);
    }

    private static final class BodyCallAdapter<R> implements CallAdapter<R, R> {
        private final Type responseType;

        BodyCallAdapter(Type responseType) {
            this.responseType = responseType;
        }

        @Override
        public Type responseType() {
            return responseType;
        }

        @Override
        public R adapt(final Call<R> call) {
            try {
                Response<R> resp = call.execute();
                if (resp.isSuccessful()) {
                    return resp.body();
                } else {
                    ResponseBody errorBody = resp.errorBody();
                    if (errorBody != null && errorBody.contentLength() > 0) {
                        throw new RpcException(errorBody.string());
                    } else {
                        throw new RpcException(resp.raw().toString());
                    }
                }

            } catch (IOException e) {
                throw new RpcException(e);
            }
        }
    }

}