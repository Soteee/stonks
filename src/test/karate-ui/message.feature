Feature: login, acceso a perfil, ver mensajes + enviar y recibir un mensaje via WS

Background:
  # para escribir tus propias pruebas, lee https://github.com/intuit/karate/tree/master/karate-core
  # driver: chromium bajo linux; si usas google-chrome, puedes quitar executable (que es lo que usaría por defecto)
  * configure driver = { type: 'chrome', executable: '/usr/bin/chromium', showDriverLog: true }
    
Scenario: login using chrome

  # Ir a register
  Given driver 'http://localhost:8080/register'
  # Rellenar formulario
  * input('#username', 'pepito')
  * input('#mail', 'pepito@pepito.com')
  * input('#name', 'Pepito')
  * input('#firstName', 'Pérez')
  * input('#lastName', 'Rodríguez')
  * input('#password', 'abcdef')
  * input('#repitedPassword', 'abcdef')
  # Hacer click en el botón de submit
  * submit().click("input[type=submit]")
  # Delay de 500 ms para que el siguiente input se haga bien
  * delay(500)

  # Rellenar formulario
  * input('#username', 'pepito')
  * input('#password', 'abcdef')
  # Hacer click en el botón de submit
  * submit().click("input[type=submit]")
  # Delay de 500 ms para que la página se cargue
  * delay(500)
  # Comprobar que el título de la página a la que se ha llegado es el de la principal
  * match html('title') contains 'Stonks'
  # Hacer click en el botón de Salas
  * submit().click("button[id=rooms]")

  # Hacer click en el botón de Crear sala
  * submit().click("button[id=createRoom]")
  # Delay de 500 ms para que el siguiente input se haga bien
  * delay(500)

  # Rellenar formulario
  * input('#name', 'La Salita')
  * select('#isPublic', '1')
  * input('#maxUsers', '5')
  * input('#startBalance', '500')
  * input('#weeklyCash', '1000')
  * input('#expirationDate', '22/07/2021')
  * input('#cash2Win', '5000')
  # Hacer click en el botón de submit
  * submit().click("input[type=submit]")

  # Hacer click en el nombre de la sala
  * click("a[id='La Salita']")

  * delay(500)

  # Comprar acciones
  * input('#amount', '1000')
  # Hacer click en el botón de Comprar
  * submit().click("input[type=submit]")

  * delay(500)

  # Comprobar que se han comprado bien las acciones TODO: mirar por atributos
  * match html('td') contains 'TSLA'
  #* match html('td') contains '1000'
