# Simulación de Sistemas
Trabajo Práctico Nro. 3: Dinámica Molecular Dirigida por Eventos

## Integrantes
* [Paula Andrea Domingues](https://github.com/pdomins), 60148
* [Maximiliano Lombardia](https://github.com/mlombardia), 56276
* [Mariano Leonel Perez Mosquera](https://github.com/marianopm), 56285

## Ejecución
Ubicandose en el directorio ss-tp2, ejecutar
```sh
mvn package
```
o también
```sh
mvn install
```
Una vez ejecutado este comando, se creará un archivo `ss-tp2-1.0-SNAPSHOT-jar-with-dependencies.jar`.
Moverse al directorio en el cual se encuentre ubicado el mismo y ejecutar
```sh
java -jar ss-tp2-1.0-SNAPSHOT-jar-with-dependencies.jar <parametros>
```
Dentro de los parametros se puede especificar:
* -N el numero de particulas de gas que desea que tenga la grilla
* -D el tamaño que desea que mida la apertura entre recintos
* -V el modulo de la velocidad de las particulas
* -T para especificar el delta del tiempo entre las particulas
* -help para obtener mas informacion sobre estos comandos.

Si los mismos no se especificaran, se tomara por default N = 200, D = 0.02, T = 0.1 y V = 0.01.

