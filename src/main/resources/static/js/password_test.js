var numeros = "0123456789";
var letras = "abcdefghyjklmnñopqrstuvwxyz";
var letras_mayusculas = "ABCDEFGHYJKLMNÑOPQRSTUVWXYZ";

function tiene_numeros(texto) {
    for (i = 0; i < texto.length; i++) {
        if (numeros.indexOf(texto.charAt(i), 0) != -1) {
            return 1;
        }
    }
    return 0;
}

function tiene_letras(texto) {
    texto = texto.toLowerCase();
    for (i = 0; i < texto.length; i++) {
        if (letras.indexOf(texto.charAt(i), 0) != -1) {
            return 1;
        }
    }
    return 0;
}

function tiene_minusculas(texto) {
    for (i = 0; i < texto.length; i++) {
        if (letras.indexOf(texto.charAt(i), 0) != -1) {
            return 1;
        }
    }
    return 0;
}

function tiene_mayusculas(texto) {
    for (i = 0; i < texto.length; i++) {
        if (letras_mayusculas.indexOf(texto.charAt(i), 0) != -1) {
            return 1;
        }
    }
    return 0;
}

function seguridad_clave(clave) {
    var seguridad = 0;
    if (clave.length != 0) {
        if (tiene_numeros(clave) && tiene_letras(clave)) {
            seguridad += 30;
        }
        if (tiene_minusculas(clave) && tiene_mayusculas(clave)) {
            seguridad += 30;
        }
        if (clave.length >= 4 && clave.length <= 5) {
            seguridad += 10;
        } else {
            if (clave.length >= 6 && clave.length <= 8) {
                seguridad += 30;
            } else {
                if (clave.length > 8) {
                    seguridad += 40;
                }
            }
        }
    }
    return seguridad
}

function modificar(seguridad) {
    document.getElementById("porcentajeBarra").style.width = seguridad + "%";
    // Rojo
    if (seguridad <= 30) document.getElementById("porcentajeBarra").style.backgroundColor = "#ff0000";
    // Amarillo
    else if (seguridad <= 60) document.getElementById("porcentajeBarra").style.backgroundColor = "#ffff00";
    // Verde
    else document.getElementById("porcentajeBarra").style.backgroundColor = "#00ff00";
}

function muestra_seguridad_clave(clave, formulario) {
    seguridad = seguridad_clave(clave);
    formulario.seguridad.value = seguridad + "%";
    modificar(seguridad);
}