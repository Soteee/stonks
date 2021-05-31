# stonks

_A trading community experience_

Proyecto para la asignatura de Ingeniería Web (optativa general para distintos grados de la [Facultad de Informática
de la UCM](https://informatica.ucm.es/)).

## Autores

Víctor Fresco Perales, Alberto Redondo Gil, Javier Ignacio Sotelino Barriga, Juan Tecedor Roa

## Introducción

¿Te gustaría invertir en bolsa pero no tienes ni para un tercio? ¿Quieres demostrarle a tus amigos quién es el verdadero
Lobo de Wall Street? En Stonks te ofrecemos una oportunidad de simular la verdadera experiencia Wall Street con tus
amigos a través de un divertido juego social.

_Buy, sell and have fun with your friends to get STONKS!_

## Tecnologías

Durante el desarrollo de la asignatura, hemos aprendido los conceptos básicos sobre distintas tecnologías web, y hemos
aplicado todas ellas en nuestro proyecto:

*   HTML
*   CSS
*   Spring MVC, Spring Security
*   Thymeleaf
*   JavaScript
*   JPA
*   XML
*   JSON
*   AJAX
*   Karate
*   H2
*   D3

Además, hemos usado otras tecnologías que no hemos visto en clase:

*   Bulma

## Uso

Se necesitan las siguientes dependencias:

*   JDK >= 8
*   Maven

Clona el repositorio y accede al directorio:

    git clone https://github.com/Soteee/stonks.git
    cd stonks

Y lanza el servidor usando Maven, que se encargará de resolver las dependencias de Java:

    mvn spring-boot:run

El servidor estará disponible en el puerto 8080.

## Vistas

*   [index.html](/src/main/resources/templates/index.html)

Página de inicio. Aparece cuando se accede a la raíz del servidor y también después de iniciar sesión con un usuario
que no sea el administrador.

A la izquierda, una lista de los mejores usuarios y otra de las mejores salas;
en el resto de la página, una gráfica que muestra el histórico de los valores de las acciones.

*   [admin.html](/src/main/resources/templates/admin.html)

Panel de administración del administrador.

Aquí podemos encontrar dos formas de búsqueda, una por usuario y otra por salas. Tras pulsar el botón de búsqueda aparecen los resultados.

*   [rooms.html](/src/main/resources/templates/rooms.html)

Página principal de acceso a las distintas salas.

Muestra un top 10 de salas según la cantidad de dinero que se tienen los jugadores, las salas a las que pertenece el usuario y un buscador de salas.

*   [r.html](/src/main/resources/templates/r.html)

Página individual para cada sala.

Si el usuario no pertenece a la sala, solo se muestra las demás salas del usuario a la izquierda, el gráfico de acciones que se pueden comprar y vender en la sala, sus valores y un botón para unirse. Si el usuario pertenece a la sala, además de lo anteriormente mencionado, se muestra un panel donde se pueden vender y comprar acciones, un chat y los usuarios de la sala ordenados por la cantidad de dinero que tienen.

*   [users.html](/src/main/resources/templates/users.html)

Página para buscar usuarios.

En la parte superior aparece el menú y, en el resto de la página, una lista de los 5 mejores usuarios y un buscador de usuarios.

*   [u.html](/src/main/resources/templates/u.html)

Página de perfil del usuario. Cada usuario tiene la suya.

En la parte superior aparece el menú y, en el resto de la página, los datos del usuario, las salas en las que está y una
lista de partidas ganadas. También muestra un botón para poder seguir al usuario.

*   [social.html](/src/main/resources/templates/social.html)

Muestra una lista de seguidos y seguidores para acceder rápidamente a su perfil.

*   [createRoom.html](/src/main/resources/templates/createRoom.html)

En esta vista podemos crear una sala.

Tenemos apartados para ponerle nombre, ajustes de privacidad, numero de jugadores, cantidad de dinero inicial, cantidad de dinero extra, fecha límite, dinero para ganar y los símbolos de la sala.

*   [login.html](/src/main/resources/templates/login.html)

Aquí podemos introducir nuestro nombre de usuario y contraseña y pulsar "iniciar sesión". Si no tenemos cuenta, podemos pulsar "Regístrate".

*   [logout.html](/src/main/resources/templates/logout.html)

No tiene vista, es un botón que redirecciona.

*   [register.html](/src/main/resources/templates/register.html)

En esta vista podemos crear una nueva cuenta.

Encontramos campos como nombre usuario, correo electrónico, nombre, primer apellido, segundo apellido y contraseña, así como lo buena que es la contraseña y, finalmente, el botón de crear cuenta.

### Fragmentos

Fragmentos de HTML que insertamos en varias páginas.

*   [head.html](/src/main/resources/templates/fragments/head.html)

Inicializa scripts y hojas de estilo.

*   [left_navbar.html](/src/main/resources/templates/fragments/left_navbar.html)

Columna izquierda donde se muestran las salas a las que pertenece el usuario.

*   [menu.html](/src/main/resources/templates/fragments/menu.html)

Menu principal de la aplicación.

Muestra Iniciar Sesión y Registrarse a un usuario que no haya iniciado sesión y Salas, Usuarios, Seguidores y Cerrar Sesión a uno que sí lo haya hecho. Si el usuario es un administrador, también muestra Administrador.

## Casos de uso

En general, la mayoría de casos de uso se han acabado adaptando a AJAX, especialmente mensajería y búsqueda.
Todo el tema de actualización de símbolos para el debug está cargado desde local para test (con la finalidad de evitar quedarnos sin querys a la api), pero se utiliza una API alojada en [rapidapi](https://rapidapi.com/sparior/api/yahoo-finance15) dónde conseguimos traernos una lista de símbolos y su precio real cada 24 horas. 

Contamos con las siguientes 
funcionalidades:

*   *Registro* Con un Script para calcular la calidad de la contraseña.

*   *Inicio de sesión*

*   *Creación de sala* Se creará una sala de la que serás dueño y la gente podrá unirse, buscarla y jugar en ella.

*   *Búsqueda de salas* Ordenadas por simulitud a la hora de la consulta

*   *Unirse a una sala* Si no pertenece a una sala en el momento de clicquear en ella sale un botón grande para unirse

*   *Compra y venta de acciones en tiempo real de una sala* (Todo con ajax) 

*   *Ganar en una sala* (Recomendamos usar "Partida para acabar y vender un par de acciones con la cuenta admin (Usuario admin, password aa)) También puede crearse una cuenta y unirse de 0. La victoria es en tiempo real y notifica a todos los usuarios de si han perdido o ganado con un gif. La sala se mantiene con estado "Acabada" y se puede revisitar para ver los balances finales. Por otro lado, si la sala no tiene una cantidad de dinero fija para acabar, cada vex que alguien inicia sesión, se comprueba si la sala ha caducado. Si es el caso, el ganador es el usuario con más dinero.

*  *Consultar gráficas* Pasando el ratón por encima

*   *Seguir, dejar de seguir, ser seguido (podemos probarlo con otra cuenta desde otro navegador) y comprobarlo en [social.html](/src/main/resources/template/social.html)*

*   *Consultar el perfil de usuario, seguirle, y comprobar sus partidas ganadas*. Para esto, podemos utilizar la sala "Partida para acabar", ganarla con la cuenta de admin (Usuario:admin, password:aa) y luego ir a su perfil y consultar que la partida sale ganada.

* *Desactivar a un usuario como admin* Si eres administrador, te sale un botón más en el menú dónde puedes buscar usuarios y desactivarlos. Los usuarios desactivados no pueden logear, y si intentan meterse a su perfil saldrá "No disponible*

* *Borrar mensajes de una sala de chat* (Hecho con AJAX) Como admin, entrar en una sala y bajar a la sala de chat nos mostrará un botón eliminar en todos los mensajes.

## Usuarios de prueba

| Usuario | Contraseña | Rol |
| -- | -- | -- |
| javi | aa | administrador |
| admin | aa | administrador |
| juan | aa | usuario normal |

## Soluciones del feedback 


### Conseguir precio en tiempo real de un símbolo para actualización de API

```java
private static double getSymbol(String name) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(headerkey, headerkeyp2);
        headers.put(authname1, authname2);
        headers.put("useQueryString", "true");

        JSONObject json = Unirest.get(url + name + "/financial-data")
                                .headers(headers)
                                .asJson()
                                .getBody()
                                .getObject();

        if (json == null) {
            return -1.f;
        }
        
        return Double.parseDouble(json.getJSONObject("financialData").getJSONObject("currentPrice").getString("fmt").replaceAll(",", ""));
    }

```

Dado un nombre de símbolo, se pide a la API sus datos de precio en tiempo real. Recordar que estos precios no son los que se consultan cuando se está usando la aplicación, solo cuando se pide un refresco de precios cada 24h. Después de obtenerlos se cargan en la BBDD en el siguiente apartado.

### Actualización de la API 



```java

 public static void refreshStockValues(EntityManager em) {
        List<Symbol> symbolList = em
								.createNamedQuery("Symbol.lasts", Symbol.class)
								.getResultList();

        // Use the same value for evert stock so we can extract them all at once easily from DB
        LocalDateTime now = LocalDateTime.now();

		for (Symbol o : symbolList) {
			// If more than 24 hours have passed
			if(o.getUpdatedOn() == null ||
				Duration.between(o.getUpdatedOn(), now).compareTo(Duration.ofHours(24)) > 0){
                
                try {
                    Symbol newSymbol = new Symbol();

                    newSymbol.setName(o.getName());
                    newSymbol.setUpdatedOn(now);
                    newSymbol.setValue(getSymbol(o.getName()));
                    List<Room> newList = new ArrayList<>();
                    newList.addAll(o.getRooms());
                    newSymbol.setRooms(newList);

                    em.persist(newSymbol);
                } catch (Exception e) {
                    e.printStackTrace();
                }
			}
		}
    }
```

Con esta línea cada vez que alguien logea se comprobará si han pasado 24 horas desde la última actualización, si han pasado, se manda una petición a la API dónde se cargaría todos los símbolos pedidos. Se pisan los anteriores.





## Recursos externos

Hemos usado algunos recursos externos como ayuda para desarrollar ciertas partes concretas de nuestro proyecto.

### Bulma

[Bulma](https://bulma.io/) es Framework de CSS.

Lo hemos utilizado para darle un estilo a la aplicación en general: colores, elementos, componentes, formularios, layouts...

Hemos incluido elementos como botones, contenidos, tablas..; componentes como menús y navbars...

Para conformar los layouts hemos usado columnas, dividiendo así verticalmente el espacio de cada página.

### D3

D3 es una librería de JavaScript que sirve para visualizar datos usando estándares web.

Lo hemos usado para mostrar el histórico de valores de los índices.

Para facilitar su uso hemos creado [graphics.js](/src/main/resources/static/js/graphics.js), que con tan solo añadirlo al HTML funciona. De esta forma, evitamos repetir scripts en varios archivos.

## Modelado

Todas las entidades estan etiquetadas con @Entity y sus IDs son únicas generadas secuencialmente.

### User

Representa un usuario.

*   **id**
*   **password:** contraseña.
*   **roles:** roles que desempeña el usuario (usuario, admin...).
*   **enabled:** booleano que indica si el usuario está activo o no.
*   **username:** nombre de usuario (no puede ser nulo).
*   **firstName:** nombre real del usuario.
*   **lastName:** apellido del usuario.
*   **mail:** correo electrónico del usuario.
*   **wonRooms:** lista de salas en las que el usuario ha ganado partidas.
*   **sent:** lista de mensajes enviados.
*   **memberList:** Indica las salas en las que el usuario participa. Es una lista de relaciones usuario-sala (clase Member).
*   **following:** lista de usuarios seguidos.

### Room

Representa una sala en la que los usuarios pueden jugar una partida.

*   **id**
*   **name:** nombre de la sala (no puede ser nulo).
*   **finished:** indica si la partida que se juega en la sala ha terminado ya o no.
*   **weeklyCash:** indica la cantidad de dinero que se entrega a los usuarios de la sala al final de la semana. Esto les permite seguir jugando aunque pierdan todo su dinero. Puede ser 0.
*   **maxUsers:** máximo número de participantes en la sala.
*   **startBalance:** cantidad inicial de dinero en la sala.
*   **cash2win:** cantidad de dinero necesaria para que un usuario gane la partida de la sala. Puede ser 0, lo que indicaría que la partida se terminará cuando expire (en ese caso, expirationDate no podría ser null).
*   **received:** lista de mensajes que se han enviado en la sala.
*   **creationDate:** fecha de creación de la sala.
*   **expirationDate:** fecha de expiracion de la sala. Cuando llega esa fecha, el usuario con más dinero gana la partida (si nadie ha alcanzado ya la canitdad cash2win). Puede ser null, lo que indicaría que la partida terminará cuando alguien alcance la cantidad cash2win (en ese caso, cash2win no puede ser 0).
*   **admin:** usuario con el rol de administrador en la sala (clase User).
*   **memberList:** lista con los usuarios que están en la sala. Es una lista de relaciones usuario-sala (clase Member).
*   **winner:** usuario que ha ganado la partida que se juega en la sala, en caso de que se haya terminado (en caso contrario, será null).

### Membership

Representa la relación entre un usuario y una sala.

*   **id**
*   **user:** usuario que participa en la relación (clase User).
*   **room:** sala en la que el usuario se encuentra (clase Room).
*   **joinDate:** fecha de entrada en la sala.
*   **balance:** cantidad de dinero que el usuario acumula en esa sala.
*   **positionList:** lista de transacciones que el usuario ha hecho en esa sala (clase Position).

### Position

Representa cada transacción que hace un usuario en una sala.

*   **id**
*   **member:** indica qué usuario dentro de qué sala ha realizado la transacción (clase Member).
*   **purchaseDate:** indica la fecha en la que se realizó la transacción.
*   **quantity:** indica el número de acciones que se han adquirido.
*   **value:** precio de cada acción en el momento de la compra.
*   **side:** indica si la transacción es una compra o una venta.
*   **symbol:** símbolo para el que se ha hecho la transacción.

### Message

Representa un mensaje enviado por un usuario en una sala.

*   **id**
*   **user:** usuario que envía el mensaje.
*   **room:** sala a la que se ha enviado el mensaje.
*   **text:** contenido del mensaje.
*   **dateSent:** fecha en la que el mensaje ha sido enviado.

#### Transfer

Ademaś, la clase Message tiene una clase Transfer definida dentro, que sirve para persistir a/de JSON.

*   **id:** ID del mensaje.
*   **from:** nombre de usuario del que envía el mensaje.
*   **sent:** fecha en la que el mensaje ha sido enviado.
*   **text:** contenido del mensaje.

#### Symbol

Representa un símbolo.

*   **id**
*   **name:** nombre del símbolo (normalmente, algunas letras del nombre de la empresa en mayúsculas).
*   **value:** precio de cada acción en el momento acutal.
*   **updatedOn:** fecha en la que se ha actualizado el precio de la acción.
*   **rooms:** lista de salas que utilizan el símbolo.

## Licencias

*   Logo: [https://www.freelogodesign.org/preview?lang=es&name=Stonks%20EST%202012&logo=e6fe77e5-b974-4cd8-87eb-8983d0e6de16](https://www.freelogodesign.org/preview?lang=es&name=Stonks%20EST%202012&logo=e6fe77e5-b974-4cd8-87eb-8983d0e6de16)  
    Licencia descrita aquí: [https://es.freelogodesign.org/terms-of-use](https://es.freelogodesign.org/terms-of-use)

*   Imagen de muestra de bolsa: [https://media.warriortrading.com/2019/08/shutterstock_775889491.jpg](https://media.warriortrading.com/2019/08/shutterstock_775889491.jpg)  
    Licencia Creative Commons
