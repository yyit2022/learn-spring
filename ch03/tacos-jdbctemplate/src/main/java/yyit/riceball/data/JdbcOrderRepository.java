package yyit.riceball.data;

import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yyit.riceball.domain.IngredientRef;
import yyit.riceball.domain.Riceball;
import yyit.riceball.domain.RiceballOrder;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public RiceballOrder save(RiceballOrder order) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Riceball_Order "
                        + "(delivery_name, delivery_street, delivery_city, "
                        + "delivery_state, delivery_zip, cc_number, "
                        + "cc_expiration,  placed_at) "
                        + "values (?,?,?,?,?,?,?,?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR,  Types.TIMESTAMP
        );

        pscf.setReturnGeneratedKeys(true);
        order.setPlacedAt(new Date());
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                        order.getDeliveryName(),
                        order.getDeliveryStreet(),
                        order.getDeliveryCity(),
                        order.getDeliveryState(),
                        order.getDeliveryZip(),
                        order.getCcNumber(),
                        order.getCcExpiration(),
                        order.getPlacedAt()));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);
        List<Riceball> riceballs = order.getRiceballs();
        int i=0;
        for (Riceball ball : riceballs) {
            saveRiceball(orderId, i++, ball);
        }
        return order;
    }

    private long saveRiceball(Long orderId, int orderKey, Riceball ball) {
        ball.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Riceball "
                        + "(name, created_at, riceball_order, riceball_order_key) "
                        + "values (?, ?, ?, ?)",
                Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
        );

        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                        ball.getName(),
                        ball.getCreatedAt(),
                        orderId,
                        orderKey
                ));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcOperations.update(psc, keyHolder);

        long ballId = keyHolder.getKey().longValue();

        ball.setId(ballId);

        saveIngredientRefs(ballId, ball.getIngredients());
        return ballId;
    }

    private void saveIngredientRefs(long ballId, List<IngredientRef> ingredientRefs) {
        int key = 0;
        for (IngredientRef ingredientRef : ingredientRefs) {
            jdbcOperations.update(
                    "insert into Ingredient_Ref (ingredient, riceball, riceball_key) "
                            + "values (?, ?, ?)",
                    ingredientRef.getIngredient(), ballId, key++);
        }
    }
}
