Feature: comprar y vender acciones

Background:
  # para escribir tus propias pruebas, lee https://github.com/intuit/karate/tree/master/karate-core
  # driver: chromium bajo linux; si usas google-chrome, puedes quitar executable (que es lo que usaría por defecto)
  * configure driver = { type: 'chrome', executable: '/usr/bin/chromium', showDriverLog: true }
    
Scenario: buy and sell actions

  # Ir a login
  Given driver 'http://localhost:8080/login'

  # Rellenar formulario
  * input('#username', 'admin')
  * input('#password', 'aa')
  # Hacer click en el botón de submit
  * submit().click("input[type=submit]")
  # Delay de 500 ms para que la página se cargue
  * delay(500)
  # Comprobar que el título de la página a la que se ha llegado es el de la principal
  * match html('title') contains 'Panel de administración'
  # Hacer click en el botón de Salas
  * submit().click("[id=rooms]")

  # Hacer click en el nombre de la sala
  * click("a[id='Andorra']")

  * delay(500)

  # Comprar acciones
  * input('#buyQuantity', '1')
  # Hacer click en el botón de Comprar
  * submit().click("button[id=buy]")

  * delay(500)

   # TODO quitar
#  * delay(50000)

  # Comprobar que se han comprado bien las acciones TODO: mirar por atributos
  * def positions = locateAll('td')
#  * match locateAll('td') contains 'TSLA'
  #* match html('td') contains '1000'
