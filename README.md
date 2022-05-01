# restauranteRMI
Manejo y funcionamiento del programa

El programa está estructurado en dos partes principales que trabajan conjuntamente: cliente y servidor. Por una parte, el servidor ofrece varios servicios que son consultados por el cliente, estos servicios incluyen: consulta de plato, leer, bloquear, desbloquear y escribir ingredientes y terminar el servicio. Para el cliente las operaciones que realiza el servidor son transparentes, tan sólo se debe preocupar por la codificación de los mensajes. Los pasos de mensajes se realizan en cadenas de texto indicando la información necesaria: cantidad de ingrediente, ID del plato, ID del ingrediente… también se incluye un código de error para indicarle al cliente si la operación se ha realizado con éxito. Los códigos de error generalmente son un ‘0’ cuando hay error y un ‘1’ si no lo hay.

La gestión de clientes concurrentes la hemos realizado mediante el algoritmo de servidor central. Los clientes cuando modifican algún ingrediente deben solicitar al servidor un testigo. El servidor se lo concederá tan sólo si este se encuentra disponible, es decir, si no existe ningún otro cliente que se encuentre utilizándolo al mismo tiempo.

Por otro lado, nos encontramos el cliente que se conecta al servidor y le hace peticiones. El funcionamiento del cliente se basa en un sencillo menú con 6 opciones. Decidimos añadir la función consulta de menú en la que se muestran los platos disponibles con una pequeña descripción, aunque a lo largo de la ejecución el programa se le pregunta al usuario si desea visualizarlo de nuevo. Las opciones que tiene el usuario son las siguientes:

0.	Salir del restaurante
Se realiza un System-exit() y se envía un ‘0’ al servidor para que realice otro. Ambos programas finalizan y en el caso del servidor se guardan los datos en el fichero de ingredientes con las cantidades actualizadas.

1.	Consultar la disponibilidad del plato
Para consultar la disponibilidad de un plato primero se llama el servidor para saber la composición del plato, el servidor retorna la cantidad de ingrediente, el ID del mismo y un código de error. Si hay un error se avisa al usuario, por el contrario entramos en un bucle for en el cual se harán tantas llamadas como ingredientes haya. El servidor devuelve la cantidad de ingrediente disponible y esta se compara con la cantidad necesaria para saber si se puede hacer el plato. Se utiliza el contador ‘l’ para saber si el plato se puede cocinar. Si el contador ‘l’ es igual al número de ingredientes se muestra un aviso al usuario de que el plato se encuentra disponible.

2.	Solicitud de plato
El funcionamiento es similar al del apartado 1: consultar la disponibilidad del plato.  Primero, se hace una llamada al servidor para comprobar la disponibilidad del plato. Si el plato está disponible se procede a hacer tantas llamadas al servidor como ingredientes haya. El cliente llama a la función número cuatro del servidor que se encarga de escribir el ingrediente. Al hacer esta llamada al servidor se le pasa el id del ingrediente y la cantidad necesaria para su cocinado. El proceso servidor es el encargado de restar las cantidades disponibles del stock y de actualizar el fichero ingredientes en el cual se almacenan las cantidades de ingredientes disponibles. El proceso servidor devuelve un código de error que es gestionado con el contador ‘b’. Si el plato se encuentra disponible y hay suficientes ingredientes para confeccionar el plato se muestra un mensaje de éxito al usuario. Antes de finalizar esta función se almacena el ID del plato en una variable llamada pedidosActivos para posteriormente poder cancelar esos pedidos.
 
3.	Anulación del plato
En la variable pedidosActivos se almacenan todos los identificadores de platos de pedidos que han sido realizados durante la ejecución del programa. El programa comprueba que existen pedidos activos de lo contrario se muestra un mensaje de error y no se podrá cancelar ningún pedido ya que no existe ninguno en el sistema. De existir pedidos activos, estos se muestran al usuario por pantalla y éste introduce el identificador del plato que quiere anular.
Cuando el usuario introduce el identificador del plato, se realiza el mismo proceso que en el apartado dos, solicitud de plato, con la diferencia de que se pasa la cantidad en negativo al servidor para que, de este modo, las cantidades se sumen al stock y no se resten.

4.	Actualizar la cantidad de ingredientes
Para actualizar un ingrediente se solicita en primer lugar el ID del ingrediente que se desea actualizar junto a la cantidad que se agregará posteriormente. Se actualiza el ingrediente. 

5.	Consultar el menú
Como se indicó anteriormente, se muestran los platos disponibles con sus identificadores y su descripción.


Otras consideraciones:
Existe una clase ‘ficheros’ encargada de gestionar todas las lecturas y escrituras sobre los ficheros de ingredientes y platos.

Cuando se quiere solicitar un plato, se le pregunta al usuario si desea ver antes los platos disponibles (opción 5 del menú: visualizar platos), en caso de querer verlos se debe introducir un ‘0’, en caso contrario, cualquier otro número entero. La lectura del ID de plato se hace como se muestra en pantalla: 00 o 01 o 02 o 03.


A continuación, se muestra una tabla resumen con los identificadores de los platos e ingredientes:

Platos:

Plato	Identificador	Ingredientes (cantidades)
Kebab	00	Pan (1), carne (150), lechuga (30), tomate (20) y salsa (25).
Durum	01	Tortilla de pan (1), carne (100), lechuga (20), tomate (20) y salsa (20).
Falafel	02	Garbanzos (100), cebolla (40), ajo (10), pimiento (30).
Pizza turca (lahmacun)	03	Base de pizza (1), pimiento (30), tomate (50) y cebolla (20).


Ingredientes:

Ingrediente	Identificador
Pan	10
Carne	11
Lechuga	12
Tomate	13
Salsa	14
Cebolla	15
Garbanzos	16
Base de Pizza	17
Pimientos	18
Ajo	19
Tortilla de pan	20


Gestión de errores:

Si se obtiene el siguiente error al ejecutar el proceso servidor se debe comprobar que el fichero de ingredientes y de platos están como argumentos siendo el fichero de ingredientes el primero y el de platos el segundo. Quedaría así: ingredientes.dat platos.dat

 

Para facilitar la gestión de ficheros se incluyen directamente los archivos “ingredientes.dat” y “platos.dat” dentro del servidor. Para configurar la entrada en args sería de la siguiente manera 

