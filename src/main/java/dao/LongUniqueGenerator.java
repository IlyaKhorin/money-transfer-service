package dao;

import com.google.inject.Singleton;

import java.util.concurrent.atomic.AtomicLong;

@Singleton
public class LongUniqueGenerator implements IUniqueGenerator<Long> {

    private AtomicLong counter = new AtomicLong();

    @Override
    public Long getNext() {
        return counter.incrementAndGet();
    }
}
