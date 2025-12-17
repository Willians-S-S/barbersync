package br.com.wss.barbersync.repositories;

import br.com.wss.barbersync.entities.Account;
import br.com.wss.barbersync.enums.Role;
import br.com.wss.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account, String> {

    Optional<Account> findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT *"
            + "  FROM  account AS account "
            + "  WHERE (account.deleted = false) "
            + "  AND ((CAST(:uid AS VARCHAR) IS NULL) OR (account.uid = CAST(:uid AS VARCHAR))) "
            + "  AND ((CAST(:createdByName AS VARCHAR) IS NULL) OR (account.created_by_name ILIKE CAST(CONCAT('%', :createdByName, '%') AS VARCHAR)))"
            + "  AND ((CAST(:updatedByName AS VARCHAR) IS NULL) OR (account.updated_by_name ILIKE CAST(CONCAT('%', :updatedByName, '%') AS VARCHAR)))"
            + "  AND ((CAST(:role AS VARCHAR) IS NULL) OR (account.role = CAST(:role AS VARCHAR))) "
            + "  AND ((CAST(:name AS VARCHAR) IS NULL) OR (account.name ILIKE CAST(CONCAT('%', :name, '%') AS VARCHAR)))"
            + "  AND ((CAST(:taxNumber AS VARCHAR) IS NULL) OR (account.tax_number ILIKE CAST(CONCAT('%', :taxNumber, '%') AS VARCHAR)))"
            + "  AND ((CAST(:phone AS VARCHAR) IS NULL) OR (account.phone ILIKE CAST(CONCAT('%', :phone, '%') AS VARCHAR)))"
            + "  AND ((CAST(:email AS VARCHAR) IS NULL) OR (account.email ILIKE CAST(CONCAT('%', :email, '%') AS VARCHAR)))"
            + "  AND ((CAST(CAST(:createdStartAt AS VARCHAR) AS date) IS NULL OR CAST(CAST(:createdEndAt AS VARCHAR) AS date) IS NULL)"
            + "      OR (account.created_at BETWEEN TO_TIMESTAMP(CAST(:createdStartAt AS VARCHAR), 'YYYY-MM-DD HH24:MI:SS') "
            + "          AND TO_TIMESTAMP(CAST(:createdEndAt AS VARCHAR), 'YYYY-MM-DD HH24:MI:SS'))) ")
    Page<Account> findByParams(final String uid, final String name, final String taxNumber,
                                         final String email, final String phone, final String createdByName, final String updatedByName,
                                         final Role role, final Boolean active, final LocalDateTime createdStartAt, final LocalDateTime createdEndAt, final Pageable pageable);

    Optional<Account> findByTaxNumber(String taxNumber);

    Optional<Account> findByPhone(String phone);
}
