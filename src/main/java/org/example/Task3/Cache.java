package org.example.Task3;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.function.Function;

public class Cache<K, V> {

    public Cache(Function<K, V> _producer){
        this.producer = _producer;
    }

    private HashMap<K, CacheReference> cache = new HashMap<>();
    private ReferenceQueue<V> referenceQueue = new ReferenceQueue<>();
    private Function<K, V> producer;

    private class CacheReference extends WeakReference<V> {
        public final K key;
        public CacheReference(K key, V referent) {
            super(referent, referenceQueue);
            this.key = key;
        }
    }
    private final Runnable cleanRunnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                try {
                    CacheReference reference = (CacheReference) referenceQueue.remove();
                    cache.remove(reference.key);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };

    public V get(K key){
        CacheReference reference = cache.get(key);
        if(reference != null){
            V value = reference.get();
            if(value != null)
                return value;
        }
        V value = producer.apply(key);
        reference = new CacheReference(key, value);
        cache.put(key, reference);
        return value;
    }

}
