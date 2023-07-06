package subscription;

import notification.WarReport;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

public class Subscription implements Flow.Subscription, Serializable {
    private final Flow.Subscriber<? super WarReport> subscriber;
    private final ExecutorService executor;
    private Future<?> future;
    private WarReport item;

    public Subscription(Flow.Subscriber<? super WarReport> subscriber,
                        ExecutorService executor) {
        this.subscriber = subscriber;
        this.executor = executor;
    }
    public void request(long n) {
        future = executor.submit(() -> {
            this.subscriber.onNext(item);
        });
    }
    public synchronized void cancel() {
        if (future != null && !future.isCancelled()) {
            this.future.cancel(true);
        }
    }
}
