package org.starcoin.utils;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class FlowableTests {

    @Test
    void testFlowable() {
        List<String> list = Arrays.asList(
                "blue", "red", "green", "yellow", "orange", "cyan", "purple"
        );
        Flowable.fromIterable(list).skip(2).subscribe(System.out::println);
        Flowable.fromArray(list.toArray()).subscribe(System.out::println);
        Flowable.just("blue").subscribe(System.out::println);

        System.out.println("===========================");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        System.out.println("MAIN: " + Thread.currentThread().getId());
        Callable<String> callable = () -> {
            System.out.println("callable [" + Thread.currentThread().getId() + "]: ");
//            Path filePath = Paths.get("build.gradle");
//            return Files.readAllLines(filePath).stream().flatMap(s -> Arrays.stream(s.split
//                    (""))).count() + "";
            return list.stream().count() + "";
        };

        Consumer<String> onNext = v -> System.out.println("consumer[" + Thread.currentThread().getId() + "]:" + v);

        Flowable.fromCallable(callable).subscribe(onNext);
        System.out.println("============");
        Future<String> future = executor.submit(callable);
        Flowable.fromFuture(future).subscribe(onNext);

        System.out.println("END");

        System.out.println("===========================");
        System.out.println("MAIN: " + Thread.currentThread().getId());
        List<String> list2 = Arrays.asList("tulip", "apple", "pear", "banana");

        ExecutorService executorService = Executors.newScheduledThreadPool(1);
        //
        Flowable<String> helloFlowable = Flowable.fromCallable(() -> {
            Thread.sleep(100);
            return "hello";
        });
        helloFlowable = helloFlowable.subscribeOn(Schedulers.newThread());
        //
        Flowable<String> f1 = Flowable.fromIterable(list).subscribeOn(Schedulers.from(executorService));//Schedulers.io());
        Flowable<String> f2 = Flowable.fromIterable(list2).subscribeOn(Schedulers.from(executorService));//Schedulers.newThread());
        //f1.take(1).subscribe(onNext);
        Flowable<String> mergedFlowable = Flowable.just("world").subscribeOn(Schedulers.from(executorService))//Schedulers.newThread())
                .mergeWith(helloFlowable)
                .mergeWith(f1)
                .mergeWith(f2);

        mergedFlowable
                //.observeOn(Schedulers.newThread())
                .subscribe(onNext);

//        mergedFlowable.blockingIterable().forEach(s -> {
//            try {
//                onNext.accept(s);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        Completable.create(new CompletableOnSubscribe() {
//            @Override
//            public void subscribe(CompletableEmitter e) throws Exception {
//                Path filePath = Paths.get("build.gradle");
//                Files.readAllLines(filePath);
//                e.onComplete();
//            }
//        }).subscribe(() -> System.out.println("OK!"),
//                Throwable::printStackTrace);
    }
}
