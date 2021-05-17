Feature: registro, login, crear sala y comprar acciones

Background:
  # para escribir tus propias pruebas, lee https://github.com/intuit/karate/tree/master/karate-core
  # driver: chromium bajo linux; si usas google-chrome, puedes quitar executable (que es lo que usaría por defecto)
  * configure driver = { type: 'chrome', executable: '/usr/bin/chromium', showDriverLog: true }
    
Scenario: register, login, create room, buy stocks

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

  # Ir a login
  Given driver 'http://localhost:8080/login'

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
  * submit().click("[id=rooms]")

  # Hacer click en el botón de Crear sala
  * submit().click("[id=createRoom]")
  # Delay de 500 ms para que el siguiente input se haga bien
  * delay(500)

  # Rellenar formulario
  * input('#name', 'La Salita')
  * select('#isPublic', '1')
  * input('#maxUsers', '5')
  * input('#startBalance', '50000')
  * input('#weeklyCash', '1000')
  * input('#expirationDate', '22/07/2021')
  * input('#cash2Win', '5000')
  * leftOf('{}TSLA').click()
  # Hacer click en el botón de submit
  * submit().click("input[type=submit]")

  # Hacer click en el nombre de la sala
  * click("a[id='La Salita']")

  * delay(500)

  # Comprar acciones
  * input('#buyQuantity', '50')
  # Hacer click en el botón de Comprar
  * submit().click("button[id=buy]")

  * delay(500)

  # Comprobar que se han comprado bien las acciones TODO: mirar por atributos
  * match html('td') contains 'TSLA'
  #* match html('td') contains '1000'
