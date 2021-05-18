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
#  * delay(500)
  # Comprobar que el título de la página a la que se ha llegado es el de la principal
#  * match html('title') contains 'Panel de administración'

  # Comprobar que se ha llegado a la página de administrador
  * waitForUrl("/admin")

  # Hacer click en el botón de Salas
  * submit().click("[id=rooms]")

  # Hacer click en el nombre de la sala
  * click("a[id='Andorra']")

  # Comprobar que se ha llegado a la página de la sala
  * waitForUrl("/r/1")

#  * delay(500)

  * match text("span[id=balance]") == '10000.0'
#  * match text("p[id=positions]") == 'Todavía no tienes posiciones'

  # Comprar acciones
  * input('#buyQuantity', '2')
  # Hacer click en el botón de Comprar
  * submit().click("button[id=buy]")

  * delay(500)


  # TODO falta comprobar que las posiciones son correctas (no tenemos span)
  # Comprobar poniendo span en los campos

  * match text("span[id=balance]") == '8717.0'

  # Comprobar que se han comprado bien las acciones TODO: mirar por atributos
#  * def filter = function(x) { x.attribute('data-label').startsWith('TSLA') }
#  * def list = locateAll('td', filter)
#  * def el = list[0]
#  * print el
#
#  * delay(50000)
#  * match locateAll('td') contains 'TSLA'
  #* match html('td') contains '1000'

  # Vender acciones
  * input('#sellQuantity', '1')
  # Hacer click en el botón de Vender
  * submit().click("button[id=sell]")

  * match text("span[id=balance]") == '9358.5'
