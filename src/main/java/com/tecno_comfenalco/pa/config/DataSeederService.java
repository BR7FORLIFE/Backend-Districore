package com.tecno_comfenalco.pa.config;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.tecno_comfenalco.pa.application.auth.ports.IUserRepositoryPort;
import com.tecno_comfenalco.pa.application.delivery.ports.IDeliveryRepositoryPort;
import com.tecno_comfenalco.pa.application.distributor.ports.IDistributorRepositoryPort;
import com.tecno_comfenalco.pa.application.inventory.ports.IInventoryRepositoryPort;
import com.tecno_comfenalco.pa.application.presales.ports.IPresalesRepositoryPort;
import com.tecno_comfenalco.pa.application.product.ports.IProductRepositoryPort;
import com.tecno_comfenalco.pa.application.store.ports.IStoreRepositoryPort;
import com.tecno_comfenalco.pa.application.warehouse.ports.IWareHouseRepositoryPort;
import com.tecno_comfenalco.pa.domain.auth.models.UserModel;
import com.tecno_comfenalco.pa.domain.delivery.model.DeliveryModel;
import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;
import com.tecno_comfenalco.pa.domain.inventory.models.InventoryModel;
import com.tecno_comfenalco.pa.domain.presales.model.PresalesModel;
import com.tecno_comfenalco.pa.domain.product.model.ProductModel;
import com.tecno_comfenalco.pa.domain.store.models.StoreModel;

import com.tecno_comfenalco.pa.domain.warehouse.models.WareHouseModel;
import com.tecno_comfenalco.pa.shared.dto.DirectionDto;
import com.tecno_comfenalco.pa.shared.enums.DocumentTypeEnum;
import com.tecno_comfenalco.pa.shared.enums.LicenseTypeEnum;
import com.tecno_comfenalco.pa.shared.enums.Unit;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataSeederService {

    private final Faker faker = new Faker();
    private final Random random = new Random();
    private final PasswordEncoder passwordEncoder;
    private final IUserRepositoryPort iUserRepositoryPort;
    private final IDistributorRepositoryPort iDistributorRepositoryPort;
    private final IStoreRepositoryPort iStoreRepositoryPort;
    private final IProductRepositoryPort iProductRepositoryPort;
    private final IPresalesRepositoryPort iPresalesRepositoryPort;
    private final IDeliveryRepositoryPort iDeliveryRepositoryPort;
    private final IWareHouseRepositoryPort iWareHouseRepositoryPort;
    private final IInventoryRepositoryPort iInventoryRepositoryPort;

    @Transactional
    public void seedDatabase(int totalRecords) {
        log.info("Iniciando generación de {} registros falsos...", totalRecords);

        // 1. Crear distribuidoras
        List<DistributorModel> distributors = createDistributors(totalRecords / 100);
        log.info("Creadas {} distribuidoras con sus usuarios", distributors.size());

        // 2. Crear preventista
        List<PresalesModel> presalesList = createPresales(distributors, totalRecords / 100);
        log.info("Creados {} preventistas con sus usuarios", presalesList.size());

        // 3. Crear tiendas
        List<StoreModel> stores = createStores(totalRecords / 100);
        log.info("Creadas {} tiendas con sus usuarios", stores.size());

        // 4 creamos los deliveries
        List<DeliveryModel> deliveryList = createDelivery(distributors, totalRecords / 100);
        log.info("Creadas {} tiendas con sus usuarios", deliveryList.size());

        // creamos los productos
        List<ProductModel> productList = createProducts(distributors, totalRecords / 100);
        log.info("Creaadas {} productos", productList.size());

        List<WareHouseModel> warehouseList = createWarehouse(distributors, totalRecords / 100);
        log.info("Creadas {} bodegas", warehouseList.size());

        List<InventoryModel> inventoryList = createInventory(distributors, productList, warehouseList);
        log.info("creadas {} inventarios", inventoryList.size());
        
        log.info("Generación de datos completada exitosamente!");
    }

    private List<DistributorModel> createDistributors(int count) {
        List<DistributorModel> distributors = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            
            UserModel user = UserModel.createDraft(
                    null,
                    faker.name().username(),
                    passwordEncoder.encode("password123"),
                    Set.of("DISTRIBUTOR"),
                    faker.internet().emailAddress(),
                    true);
            user = iUserRepositoryPort.save(user);

            DistributorModel distributor = DistributorModel.createDraft(
                    user.getId(),
                    faker.number().digits(9),
                    faker.company().name(),
                    faker.phoneNumber().cellPhone(),
                    faker.internet().emailAddress(),
                    createRandomDirection());

            iDistributorRepositoryPort.save(distributor);

            distributors.add(distributor);
        }
        return distributors;
    }

    private List<PresalesModel> createPresales(List<DistributorModel> distributors, int count) {
        List<PresalesModel> presalesList = new ArrayList<>();

        if (distributors.isEmpty())
            return presalesList;

        for (int i = 0; i < count; i++) {

            DistributorModel selectedDistributor = (i < distributors.size())
                    ? distributors.get(i)
                    : distributors.get(random.nextInt(distributors.size()));

            UserModel user = UserModel.createDraft(
                    selectedDistributor.getId(),
                    faker.name().username(),
                    passwordEncoder.encode("password123"),
                    Set.of("PRESALES"),
                    faker.internet().emailAddress(),
                    true);

            UserModel savedUser = iUserRepositoryPort.save(user);

            PresalesModel presales = PresalesModel.createDraft(
                    selectedDistributor.getId(),
                    savedUser.getId(),
                    faker.name().fullName(),
                    faker.phoneNumber().cellPhone(),
                    faker.internet().emailAddress(),
                    getRandomDocumentType(),
                    faker.number().digits(10));

            presalesList.add(iPresalesRepositoryPort.save(presales));
        }

        return presalesList;
    }

    private List<StoreModel> createStores(int count) {
        List<StoreModel> stores = new ArrayList<>();
        for (int i = 0; i < count; i++) {

            UserModel user = UserModel.createDraft(
                    null,
                    faker.name().username(),
                    passwordEncoder.encode("password123"),
                    Set.of("STORE"),
                    faker.internet().emailAddress(),
                    true);

            UserModel userSaved = iUserRepositoryPort.save(user);

            StoreModel store = StoreModel.createDraft(
                    userSaved.getId(),
                    faker.name().username() + " Store",
                    faker.number().digits(10),
                    faker.phoneNumber().cellPhone(),
                    faker.internet().emailAddress(),
                    createRandomDirection(),
                    Instant.now(),
                    Instant.now());

            stores.add(iStoreRepositoryPort.save(store));
        }
        return stores;
    }

    private List<DeliveryModel> createDelivery(List<DistributorModel> distributors, int count) {
        List<DeliveryModel> deliveryList = new ArrayList<>();

        if (distributors.isEmpty())
            return deliveryList;

        for (int i = 0; i < count; i++) {

            DistributorModel selectedDistributor = (i < distributors.size())
                    ? distributors.get(i)
                    : distributors.get(random.nextInt(distributors.size()));

            UserModel user = UserModel.createDraft(
                    selectedDistributor.getId(),
                    faker.name().username(),
                    passwordEncoder.encode("password123"),
                    Set.of("DELIVERY"),
                    faker.internet().emailAddress(),
                    true);

            UserModel savedUser = iUserRepositoryPort.save(user);

            DeliveryModel delivery = DeliveryModel.createDraft(
                    selectedDistributor.getId(),
                    savedUser.getId(),
                    faker.name().fullName(),
                    faker.internet().emailAddress(),
                    getRandomDocumentType(),
                    faker.number().digits(10),
                    String.valueOf(faker.phoneNumber()),
                    faker.number().digits(10),
                    getRandomLicenseTypeEnum(),
                    null);

            deliveryList.add(iDeliveryRepositoryPort.save(delivery));
        }

        return deliveryList;
    }

    private List<WareHouseModel> createWarehouse(List<DistributorModel> distributors, int count) {
        List<WareHouseModel> wareHouseList = new ArrayList<>();

        if (distributors.isEmpty())
            return wareHouseList;

        for (int i = 0; i < count; i++) {

            DistributorModel selectedDistributor = (i < distributors.size())
                    ? distributors.get(i)
                    : distributors.get(random.nextInt(distributors.size()));

            WareHouseModel warehouseModel = WareHouseModel.createDraft(
                    selectedDistributor.getId(),
                    faker.company().name() + " warehouse",
                    createRandomDirection(),
                    true);

            wareHouseList.add(iWareHouseRepositoryPort.save(warehouseModel));
        }

        return wareHouseList;
    }

    private List<ProductModel> createProducts(List<DistributorModel> distributors, int count) {

        List<ProductModel> productList = new ArrayList<>();

        if (distributors.isEmpty())
            return productList;

        for (int i = 0; i < count; i++) {

            DistributorModel selectedDistributor = (i < distributors.size())
                    ? distributors.get(i)
                    : distributors.get(random.nextInt(distributors.size()));

            ProductModel product = ProductModel.createDraft(
                    selectedDistributor.getId(),
                    faker.commerce().promotionCode(),
                    faker.commerce().productName(),
                    getRandomUnit(),
                    Double.valueOf(faker.commerce().price().replace(',', '.')));

            productList.add(iProductRepositoryPort.save(product));
        }

        return productList;

    }

    private List<InventoryModel> createInventory(List<DistributorModel> distributors, List<ProductModel> products,
            List<WareHouseModel> warehouses) {
        List<InventoryModel> inventoryList = new ArrayList<>();

        if (products.isEmpty() || warehouses.isEmpty()) {
            return inventoryList;
        }

        for (ProductModel product : products) {
            List<WareHouseModel> matchingWarehouses = warehouses.stream()
                    .filter(w -> w.getDistributorId().equals(product.getDistributorId()))
                    .toList();

            if (matchingWarehouses.isEmpty()) {
                WareHouseModel randomWarehouse = warehouses.get(random.nextInt(warehouses.size()));
                inventoryList.add(saveInventory(product, randomWarehouse));
            } else {
                for (WareHouseModel warehouse : matchingWarehouses) {
                    if (random.nextDouble() > 0.3) {
                        inventoryList.add(saveInventory(product, warehouse));
                    }
                }
            }
        }

        return inventoryList;
    }

    // private List<VehicleModel> createVehicle(List<DistributorModel> distributors,
    // int count) {

    // List<VehicleModel> vehicleList = new ArrayList<>();

    // if (distributors.isEmpty())
    // return vehicleList;

    // for (int i = 0; i < count; i++) {

    // DistributorModel selectedDistributor = (i < distributors.size())
    // ? distributors.get(i)
    // : distributors.get(random.nextInt(distributors.size()));

    // VehicleModel vehicle = VehicleModel.createDraft(
    // selectedDistributor.getId(),
    // faker.bothify("???-###").toUpperCase(),
    // faker.company().name(),
    // faker.options().option("Toyota", "Chevrolet", "Renault", "Mazda", "Nissan"));

    // vehicleList.add(null.save(vehicle));
    // }

    // return vehicleList;

    // }

    private InventoryModel saveInventory(ProductModel product, WareHouseModel warehouse) {
        InventoryModel inventory = InventoryModel.createDraft(
                product.getDistributorId(),
                product.getId(),
                warehouse.getId(),
                (long) faker.number().numberBetween(10, 500));

        return iInventoryRepositoryPort.save(inventory);
    }

    private DirectionDto createRandomDirection() {
        DirectionDto directionDto = new DirectionDto(
                faker.address().streetAddress(),
                faker.address().secondaryAddress(),
                faker.address().buildingNumber(),
                faker.address().city(),
                faker.address().state(),
                faker.country().name(),
                faker.address().zipCode(),
                faker.address().fullAddress(),
                faker.idNumber().valid(),
                Double.valueOf(faker.address().latitude().replace(',', '.')),
                Double.valueOf(faker.address().longitude().replace(',', '.')));

        return directionDto;
    }

    private DocumentTypeEnum getRandomDocumentType() {
        DocumentTypeEnum[] types = DocumentTypeEnum.values();
        return types[random.nextInt(types.length)];
    }

    private LicenseTypeEnum getRandomLicenseTypeEnum() {
        LicenseTypeEnum[] types = LicenseTypeEnum.values();
        return types[random.nextInt(types.length)];
    }

    private final Unit getRandomUnit() {
        Unit[] types = Unit.values();
        return types[random.nextInt(types.length)];
    }
}
