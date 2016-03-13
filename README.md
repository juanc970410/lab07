###Escuela Colombiana de Ingeniería
###Procesos de Desarrollo de Software - PDSW
##Laboratorio - Patrón DAO, JDBC, y pruebas.

###Parte 1. JDBC (Para terminar en clase).

En un motor de base de datos MySQL Se tiene un esquema con el siguiente modelo de base de datos (un registro de pedidos de productos):

![](img/ex1model.png)


1. Clone el proyecto disponible en https://github.com/PDSW-ECI/JDBC_Intro.git .
3. Ajuste los parámetros de conexión del programa:

	```java
String url="jdbc:mysql://desarrollo.is.escuelaing.edu.co:3306/bdprueba";
String driver="com.mysql.jdbc.Driver";
String user="bdprueba";
String pwd="bdprueba";
```

2. Revise la documentación de ‘PreparedStatement’, del API JDBC:
[http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html](http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html). Teniendo en cuenta esto, implemente las operaciones faltantes (la operación que hace el cálculo del valor de un pedido debe hacerlo mediante SQL). Para las operaciones c y d use su código de estudiante, de manera que no haya conflicto con sus compañeros (todos están usando la misma base de datos).

	* nombresProductosPedido
	* valorTotalPedido
	* cambiarNombreProducto
	* registrarNuevoProducto

5. Ejecute las operaciones y rectifique los resultados. Operaciones a y b por pantalla, operaciones c y d consultando en la base de datos con un cliente MySQL.

###Parte 2. Patrón DAO (Avance antes de terminar la clase y para entregar terminado el Martes).

####Nota: este ejercicio se debe realizar colaborativamente haciendo uso de un repositorio git centralizado tal como github o gitlab.

Para este ejercicio, va a implementar la capa de persistencia para el ejercicio anterior, en el cual se desarrolló una aplicación Web para gestionar las consultas médicas realizadas por los pacientes de un centro médico. El siguiente, es el modelo de base de datos en el que se va a hacer persistente la información registrada a través de la aplicación:

![](img/PACIENTES_CONSULTAS.png)

El siguiente, es el diagrama de clases de la implementación del patrón DAO para este problema, en donde -por ahora- sólo se maneja el DAOPaciente:

![](img/basediagram.png)

1. Implemente una prueba para cada una de las siguientes clases de equivalencia:

	| #CE	| Método        | Clase de equivalencia           | Tipo  |
| ---	| ------------- |:-------------| -----:|
| 1| DAOPaciente.save()      | Paciente nuevo que se registra con más de una consulta | Correcta/Estándar |
|2	| DAOPaciente.save()      | Paciente nuevo que se registra sin consultas      |   Correcta/Frontera |
|3	| DAOPaciente.save()      | Paciente nuevo que se registra con sólo una consulta      |   Correcta/Frontera |
|4	| DAOPaciente.save()      | Paciente nuevo YA existente que se registra con más de una consulta     |   Incorrecta/Estándar |

2. A partir de lo revisado en el punto 1, implemente SÓLO las operaciones save() y load() de JDBCDaoPaciente. La siguiente, es la consulta SQL sugerida para obtener todos los pacientes con sus respectivas consultas:

	```sql
select pac.nombre, pac.fecha_nacimiento, con.idCONSULTAS, con.fecha_y_hora, con.resumen from PACIENTES as pac inner join CONSULTAS as con on con.PACIENTES_id=pac.id and con.PACIENTES_tipo_id=pac.tipo_id where pac.id=1 and pac.tipo_id='cc'
```

3. Haciendo uso de las pruebas, rectifique que la implementación haya sido correcta (recuerde ejecutar las pruebas con 'mvn test'). Si la prueba de la clase de equivalencia #2 falla, revise el siguiente esquema, e identifique qué está mal con la consulta SQL:

	![](img/joins.png)


###Parte 3. Para el Jueves.

Se revisará el Martes en clase.
