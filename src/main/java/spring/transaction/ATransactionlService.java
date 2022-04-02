package spring.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

@Service
@Transactional
@RequiredArgsConstructor
public class ATransactionlService {
    final AnotherTransactionalService service2;
    final MyEntityRepository repo;

    public void persistTwoEntities(String msg1, Callable<String> msg2) throws Exception {
        persistANewEntity(msg1);
        service2.persistAnotherNewEntity(msg2);
    }

    public void persistANewEntity(String msg) {
        MyEntity entity = new MyEntity();
        entity.setText(msg);
        repo.save(entity);
    }

}
