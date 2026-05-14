package com.tecno_comfenalco.pa.infrastructure.catalog.events.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.tecno_comfenalco.pa.application.product.events.ProductDeletedEvent;
import com.tecno_comfenalco.pa.application.product.events.ProductUpdatedEvent;
import com.tecno_comfenalco.pa.infrastructure.catalog.entity.CatalogDocument;

@Component
public class CatalogSyncListener {
    private final MongoTemplate mongoTemplate;

    public CatalogSyncListener(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    // mandamos el metodo a otro hilo para ejecutar la actualizacion de los
    // registros del catalogo

    /**
     * cuando el producto es actualizado tmb se actualiza en el catalogo
     */
    @Async
    @EventListener
    public void handleProductUpdate(ProductUpdatedEvent productEvent) {
        // creamos primero la query
        Query query = new Query(Criteria.where("distributorId").is(productEvent.distributorId())
                .and("categories.products._id").is(productEvent.id()));

        // creamos el update -> una forma de actualizar cuando recupera los productos
        // coincidentes
        Update update = new Update()
                .set("categories.$[].products.$[prod].sku", productEvent.sku())
                .set("categories.$[].products.$[prod].name", productEvent.name())
                .set("categories.$[].products.$[prod].unit", productEvent.unit())
                .set("categories.$[].products.$[prod].price", productEvent.price());

        update.filterArray(Criteria.where("prod._id").is(productEvent.id()));

        mongoTemplate.updateMulti(query, update, CatalogDocument.class);
    }

    @Async
    @EventListener
    public void handleProductDelete(ProductDeletedEvent productEvent) {
        Query query = new Query(Criteria.where("distributorId").is(productEvent.distributorId())
                .and("categories.products._id").is(productEvent.id()));

        Update update = new Update().pull("categories.$[].products",
                new Query(Criteria.where("_id").is(productEvent.id())));

        mongoTemplate.updateMulti(query, update, CatalogDocument.class);
    }
}
