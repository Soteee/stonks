# stonks

_A trading community experience_

Proyecto para la asignatura de Ingeniería Web (optativa general para distintos grados de la [Facultad de Informática de la UCM](https://informatica.ucm.es/)).

## Autores

Óscar Fernández Romano, Víctor Fresco Perales, Alberto Redondo Gil, Javier Ignacio Sotelino Barriga, Juan Tecedor Roa

## Introducción

¿Te gustaría invertir en bolsa pero no tienes ni para un tercio? ¿Quieres demostrarle a tus amigos quién es el verdadero Lobo de Wall Street? En Stonks te ofrecemos una oportunidad de simular la verdadera experiencia Wall Street con tus amigos a través de un divertido juego social.

_Buy, sell and have fun with your friends to get STONKS!_

## Entrega vistas

### Vistas

*   [index.html](localhost:8080)
*   [admin.html](localhost:8080/admin)
*   [r.html](localhost:8080/r)
*   [rooms.html](localhost:8080/rooms)
*   [social.html](localhost:8080/social)

### Licencias

*   Logo: [https://www.freelogodesign.org/preview?lang=es&name=Stonks%20EST%202012&logo=e6fe77e5-b974-4cd8-87eb-8983d0e6de16](https://www.freelogodesign.org/preview?lang=es&name=Stonks%20EST%202012&logo=e6fe77e5-b974-4cd8-87eb-8983d0e6de16)  
    Licencia descrita aquí: [https://es.freelogodesign.org/terms-of-use](https://es.freelogodesign.org/terms-of-use)

*   Imagen de muestra de bolsa: [https://media.warriortrading.com/2019/08/shutterstock_775889491.jpg](https://media.warriortrading.com/2019/08/shutterstock_775889491.jpg)  
    Licencia Creative Commons

## Entrega modelado

### Esquema entidad/relación de la BD

![Esquema entidad/relación de la BD](/stonks/img/esquema_bd.png)

Todas las entidades estan etiquetadas con @Entity y sus ids son únicas generadas secuencialmente

### User

Representa un usuario.

*   **ID**
*   **userName:** Nombre de usuario (no puede ser nulo).
*   **realName:** Nombre real del usuario.
*   **memberList:** Indica las salas en las que el usuario participa. Es una lista de relaciones usuario-sala (clase Member).

### Rooms

Representa una sala en la que los usuarios pueden jugar una partida.

*   **ID**
*   **name:** Nombre de la sala (no puede ser nulo).
*   **isPublic:** Indica si la sala es pública o no.
*   **weeklyCash:** Indica la cantidad de dinero que se entrega a los usuarios de la sala al final de la semana. Esto les permite seguir jugando aunque pierdan todo su dinero. Puede ser 0.
*   **maxUsers:** Máximo número de participantes en la sala.
*   **startBalance:** Cantidad inicial de dinero en la sala.
*   **cash3win:** Cantidad de dinero necesaria para que un usuario gane la partida de la sala. Puede ser 0, lo que indicaría que la partida se terminará cuando expire (en ese caso, expirationDate no podría ser NULL)
*   **createDate:** Fecha de creación de la sala.
*   **expirationDate:** Fecha de expiracion de la sala. Cuando llega esa fecha, el usuario con más dinero gana la partida (si nadie ha alcanzado ya la canitdad cash3win). Puede ser NULL, lo que indicaría que la partida terminará cuando alguien alcance la cantidad cash3win (en ese caso, cash3win no puede ser 0).
*   **admin:** Usuario con el rol de administrador en la sala (clase User).
*   **memberList:** Indica los usuarios que están en la sala. Es una lista de relaciones usuario-sala (clase Member).

### Member

Representa la relación entre un usuario y una sala.

*   **ID**
*   **user:** Usuario que participa en la relación (clase User).
*   **room:** Sala en la que el usuario se encuentra (clase Room).
*   **joinData:** Fecha de entrada en la sala.
*   **balance:** Cantidad de dinero que el usuario acumula en esa sala.
*   **positionList:** Lista de transacciones que el usuario ha hecho en esa sala (clase Position).

### Position

Representa cada transacción que hace un usuario en una sala.

*   **ID**
*   **member:** Indica qué usuario dentro de qué sala ha realizado la transacción (clase Member).
*   **purchaseDate:** Indica la fecha en la que se realizó la transacción.
*   **isActive:** Indica si la transacción se encuentra en un estado activo o no.
*   **quantity:** Indica el número de acciones que se han adquirido.
*   **indexName:** Nombre de la empresa a la que pertenecen las acciones.
*   **price:** Precio medio de la transacción en función de lo que ha valido y lo que vale ahora. El precio real se calcula en base al precio de las acciones en el momento (usando la API).

### Conversation

Representa una conversación entre dos usuarios.

*   **ID**
*   **user1 y user2:** Usuarios dentro de la conversación (clase User).
*   **messageList:** Lista de mensajes dentro de la conversación (clase Message).

### Message

Representa un mensaje enviado desde un usuario a otro dentro de una conversación.

*   **ID**
*   **conver:** Conversación a la que pertenece el mensaje (clase Conversation).
*   **sender:** Usuario que envía el mensaje (sólo puede ser conversation.user1 o conversation.user2; clase User).
*   **text:** Texto enviado.
*   **date:** Fecha en la que el mensaje ha sido enviado.