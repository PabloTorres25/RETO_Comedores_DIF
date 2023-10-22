# RETO_Comedores_DIF
Primera Actualización: 2023-10-01 03:53pm
- Se movieron lso botones Fab de "Main Activity" a "fragment_asistencia", para facilitar que no se vean en otros fragmentos
- Se puso funcionalidad al Fab de reporte para que lleve a su fragmento
- Se corrigio un error en el "mobile_navigation", que te llevaba al archivo Kotlin de familiaresRegistrados y no a su layout

Segunda Actualización: 02-10-2023 01:37pm
- Se cambió la interfaz de app_bar_main.xml donde se ajustó el toolbar 

Tercera Actualización: 02-10-2023 18:34pm
- Se colocó la pantalla introductoria con el logo antes de mostrar el main activity

Cuarta Actualización: 02-10-2023 20:39pm
- Se agregó que al darle clic al boton de reporte se cierre sesión. Luego se cambiará

Quinta Actualización: 2023-10-03 04:28am
- Se terminaron los spinners de nuevo registro
- Se regreso el AndroidManifest a una version anterior, debido a problemas con el "SplashActivity"

Sexta Actualización: 2023-10-03 06:14am
- Se creo el Bottom Sheet, y el Chip Group del nuevo registro

Septima Actualización: 2023-10-03 12:12pm
- Se cambio el tipo de style de el boton "condiciones" en Nuevo registro

Octava  Actualización: 2023-10-04 02:51pm
- Alfin quedo que los chips seleccionados dentro del BottomSheet se muestren en el TextView de Nuego Registro
- Y que se queden seleccionados antes y despues de que se abra el BottomSheet 

Novena Actualización: 08/10/2023 12:22pm
- Se logró hacer trabajar la barra de busqueda

Decima Actualización: 2023-10-15 10:55am
InicioSesionFragment: Se pusieron varios Toast
- "No hay conexión"
- "Usuario o Contraseña Incorrectos"
- "Datos incompletos"

Voluntario: Se puso un Toast
- "Datos incompletos"

NuevoRegistroFragment
- XML
- - Se cambiaron los EditText
- - Se sustituyo el spinner "Tipo Asistencia" por un EditText "Direccion"
- - Se cambio el Boton por "Crear Registro"
- - Se definio 2 como el maximo de caracteres de Edad
- Kotlin
- Se configuro que mande un Toast si falta nombre o CURP

Decimo primera Actualización (Ultima): 2023-10-21 07:49pm
- Base de Datos en MySQL
- Datos.zip: Archivos csv con datos de BD
- Reto App.zip:
- - Código API
- - HTML, CSS y JS de página web
- - Node modules

## Para Guardar el proyecto
Presionar la flecha verde en la parte superior derecha de la sección "Git" o CTRL + K
Dar clic en el boton "Commit and push".

si se puede anotar una descripción (De preferencia la fecha en formato aa-mm-dd hh:mmam/pm) 
y despues un breve resumen de que se hizo

## Para Actualizar el proyecto
Siempre al abrir el proyecto dar clic en la flecha azul en la sección "Git" o CTRL + T
