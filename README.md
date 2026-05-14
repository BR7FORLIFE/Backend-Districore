# 📦 DistriCore Backend

**DistriCore** es un motor de orquestación empresarial de alto rendimiento diseñado para optimizar la cadena de suministro entre distribuidores, personal de preventa y tiendas minoristas. Este sistema gestiona la complejidad de inventarios en tiempo real, validaciones financieras y flujos de pedidos masivos.

---

## 🎯 Filosofía del Proyecto

DistriCore no es solo un CRUD; es un reflejo tecnológico de la logística de mercado real. Se rige por tres pilares:
1. **Pensamiento de Escritorio (Desk-Level Thinking):** Lógica transaccional precisa y eficiente, inspirada en sistemas de alta disponibilidad.
2. **Integridad del Dominio:** El negocio es el centro. La arquitectura protege las reglas de venta e inventario de cualquier cambio tecnológico externo.
3. **Escalabilidad y Rendimiento:** Optimización para rendering de alta frecuencia (120 FPS en cliente) y procesamiento eficiente en servidor.

---

## 🏗️ Arquitectura: Hexagonal (Ports & Adapters)

El proyecto implementa una **Arquitectura Hexagonal** estricta para garantizar un desacoplamiento total:

*   **`domain`**: El núcleo puro. Contiene los modelos (`Order`, `Store`, `Product`), excepciones de negocio y las interfaces de los repositorios.
*   **`application`**: Los servicios orquestadores y casos de uso. Aquí reside la lógica de transición de estados y validación de reglas de negocio.
*   **`infrastructure`**: Los adaptadores. Implementaciones de **MongoDB**, controladores **REST**, seguridad con **JWT** y manejo de eventos.

---

## 🛠️ Stack Tecnológico

*   **Java 17+**
*   **Spring Boot 3.x** (Core framework)
*   **MongoDB** (Persistencia NoSQL flexible)
*   **Spring Security & JWT** (Seguridad basada en roles: `DISTRIBUTOR`, `PRESALES`, `STORE`)
*   **Arquitectura Hexagonal** (Organización de paquetes por responsabilidad)
*   **Jackson Custom Serialization** (Precisión de 4 decimales para cálculos financieros)

---

## 🚀 Características Destacadas

*   **Gestión de Pedidos e Inventario:** Validación proactiva de stock antes de la creación de órdenes.
*   **Máquina de Estados de Órdenes:** Sistema robusto basado en Enums para transiciones de estado seguras (`PENDING` -> `ACCEPT`/`REJECTED`).
*   **Control de Expiración:** Lógica basada en `Instant` para invalidar pedidos automáticamente tras ventanas de tiempo configurables.
*   **Paginación Avanzada:** Consultas optimizadas en MongoDB con filtrado dinámico por roles e identidades.
*   **Seguridad por Métodos:** Uso de `@PreAuthorize` para garantizar que cada usuario acceda solo a sus datos (Binding de Tiendas).

---

## ⚙️ Configuración del Entorno

```bash
  spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/districore
order:
  expiration: 259200 # Segundos (3 días)
```

### DistriCore: Tecnología robusta para la logística del mañana. | BR7FORLIFE

1.  **Clonar:**
    ```bash
    git clone [https://github.com/tu-usuario/districore-backend.git](https://github.com/tu-usuario/districore-backend.git)
