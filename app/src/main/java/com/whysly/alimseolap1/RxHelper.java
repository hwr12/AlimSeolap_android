package com.whysly.alimseolap1;

import androidx.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RxHelper {
    private static final String TAG = RxHelper.class.getName();

    @NonNull
    public static <T> Observable<T> getObserbable(@NonNull final Call<T> response){

        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(final ObservableEmitter<T> emitter) throws Exception {

                response.enqueue(new Callback<T>() {
                    @Override
                    public void onResponse(Call<T> call, Response<T> response) {

                        if (!emitter.isDisposed()) {
                            emitter.onNext(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<T> call, Throwable t) {
                        if (!emitter.isDisposed()) {
                            emitter.onError(t);
                        }
                    }
                });

            }
        });

    }
}