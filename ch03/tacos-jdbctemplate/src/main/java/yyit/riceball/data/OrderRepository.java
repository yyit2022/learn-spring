package yyit.riceball.data;

import yyit.riceball.domain.RiceballOrder;

public interface OrderRepository {

    RiceballOrder save(RiceballOrder order);

}
