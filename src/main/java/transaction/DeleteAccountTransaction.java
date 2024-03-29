package transaction;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dao.IRepository;
import dao.dto.AccountDto;

import java.util.Map;

public class DeleteAccountTransaction implements ITransaction<Void> {
    private final IRepository<Long, AccountDto> repository;

    @Inject
    public DeleteAccountTransaction(@Named("Account")IRepository<Long, AccountDto> repository) {
        this.repository = repository;
    }

    @Override
    public Void Run(Map<String, Object> context) {
        Long id = (Long)context.get("id");
        if(id == null) throw new IllegalArgumentException("Context should have account id to delete");

        repository.delete(id);

        return null;
    }
}
