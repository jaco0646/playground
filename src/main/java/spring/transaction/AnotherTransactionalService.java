package spring.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

@Service
@Transactional
@RequiredArgsConstructor
public class AnotherTransactionalService {
    final MyEntityRepository repo;

    public void persistAnotherNewEntity(Callable<String> msg) throws Exception {
        MyEntity entity = new MyEntity();
        entity.setText(msg.call());
        repo.save(entity);
    }
}
