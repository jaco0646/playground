package spring.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

@Service
@RequiredArgsConstructor
public class TransactionalService {
    final MyEntityRepository repo;

    @Transactional
    public void persistTransactional(Callable<String> msg) throws Exception {
        persistNewEntity(msg);
    }

    public void persistNonTransactional(Callable<String> msg) throws Exception {
        persistNewEntity(msg);
    }

    private void persistNewEntity(Callable<String> msg) throws Exception {
        MyEntity entity = new MyEntity();
        try {
            entity.setText(msg.call());
        } catch (Exception e) {
            entity.setText(e.getMessage());
            throw e;
        } finally {
            repo.save(entity);  // In a Transaction, RuntimeException will roll this back, and it will not be saved.
        }
    }
}
