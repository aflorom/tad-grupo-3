-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 11-05-2017 a las 15:13:48
-- Versión del servidor: 10.1.21-MariaDB
-- Versión de PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `filmupo`
--
CREATE DATABASE IF NOT EXISTS `filmupo` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `filmupo`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `actor`
--

CREATE TABLE `actor` (
  `idActor` int(11) UNSIGNED NOT NULL,
  `nombre` varchar(60) COLLATE utf8_spanish_ci NOT NULL,
  `apellidos` varchar(120) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `actor`
--

INSERT INTO `actor` (`idActor`, `nombre`, `apellidos`) VALUES
(1, 'Marlon', 'Brando'),
(2, 'Al', 'Pacino'),
(3, 'Robert', 'Duvall'),
(4, 'Robert', 'De Niro'),
(5, 'Antonio', 'De La Torre'),
(6, 'Roberto', 'Alamo'),
(7, 'Andrew', 'Garfield'),
(8, 'Emma', 'Stone'),
(11, 'Jamie', 'Foxx'),
(12, 'Oscar', 'Martinez'),
(13, 'Amy', 'Adams'),
(14, 'Jeremy', 'Renner'),
(15, 'Forest', 'Whitaker'),
(16, 'Matthew', 'McConaughway'),
(17, 'Anne', 'Hattaway'),
(18, 'Ellen', 'Burstyn'),
(19, 'Ryan', 'Gosslying'),
(20, 'Mahershala', 'Ali'),
(21, 'Sharrif', 'Earp'),
(22, 'Brad', 'Pitt'),
(23, 'Johny', 'Deep'),
(24, 'Angelina', 'Jolie'),
(25, 'Will', 'Smith'),
(26, 'Tom', 'Cruise'),
(27, 'Tom', 'Hanks'),
(28, 'Bruce', 'Willis'),
(29, 'George', 'Clooney'),
(30, 'Matt', 'Damon'),
(31, 'Harrison', 'Ford'),
(32, 'Nicolas', 'Cage'),
(33, 'Sylvester', 'Stallone'),
(34, 'Ben', 'Affleck'),
(35, 'Christian', 'Bale'),
(36, 'Kevin', 'Spacey'),
(37, 'Mel', 'Gibson'),
(38, 'Cameron', 'Diaz'),
(39, 'Julia', 'Roberts'),
(40, 'Penelope', 'Cruz'),
(41, 'Javier', 'Bardem'),
(42, 'Jared', 'Leto');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `actorpelicula`
--

CREATE TABLE `actorpelicula` (
  `idActorPelicula` int(11) UNSIGNED NOT NULL,
  `idActor` int(11) UNSIGNED NOT NULL,
  `idPelicula` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `actorpelicula`
--

INSERT INTO `actorpelicula` (`idActorPelicula`, `idActor`, `idPelicula`) VALUES
(3, 1, 2),
(4, 2, 2),
(9, 3, 2),
(10, 4, 2),
(12, 5, 9),
(14, 7, 7),
(15, 7, 8),
(26, 11, 8),
(29, 8, 13),
(30, 8, 7),
(31, 8, 8),
(32, 12, 10),
(33, 13, 15),
(34, 14, 15),
(35, 15, 15),
(36, 16, 19),
(37, 17, 19),
(38, 18, 19),
(39, 20, 17),
(40, 21, 17),
(41, 19, 13);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `director`
--

CREATE TABLE `director` (
  `idDirector` int(11) UNSIGNED NOT NULL,
  `nombre` varchar(60) COLLATE utf8_spanish_ci NOT NULL,
  `apellidos` varchar(120) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `director`
--

INSERT INTO `director` (`idDirector`, `nombre`, `apellidos`) VALUES
(1, 'Francis', 'Ford Coppola'),
(2, 'Marc', 'Webb'),
(3, 'Raul', 'Arevalo Zorra'),
(5, 'Gaston', 'Duprat'),
(6, 'Damien', 'Chazelle'),
(7, 'Dennis', 'Villeneuve'),
(8, 'Barry', 'Jenkins'),
(9, 'Christopher', 'Nolan'),
(10, 'Byron', 'Howard'),
(11, 'Quentin', 'Tarantino'),
(12, 'Martin', 'Scorsese'),
(13, 'Steven', 'Spielberg'),
(14, 'Ridley', 'Scott'),
(15, 'David', 'Lynch'),
(16, 'Wooddy', 'Allen'),
(17, 'David', 'Fincher'),
(18, 'Roman', 'Polanski'),
(19, 'James', 'Cameron'),
(20, 'Tim', 'Burton'),
(21, 'Pedro', 'Almodovar'),
(22, 'Guillermo', 'Del Toro'),
(23, 'George', 'Lucas'),
(24, 'Alejandro', 'Amenabar'),
(25, 'Fernando', 'Trueba'),
(26, 'Juan Antonio', 'Bayona');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pelicula`
--

CREATE TABLE `pelicula` (
  `idPelicula` int(11) UNSIGNED NOT NULL,
  `idDirector` int(11) UNSIGNED NOT NULL,
  `titulo` varchar(250) COLLATE utf8_spanish_ci NOT NULL,
  `anio` int(4) NOT NULL,
  `pais` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `genero` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `sinopsis` varchar(1000) COLLATE utf8_spanish_ci NOT NULL,
  `duracion` int(3) NOT NULL,
  `imagen` varchar(250) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `pelicula`
--

INSERT INTO `pelicula` (`idPelicula`, `idDirector`, `titulo`, `anio`, `pais`, `genero`, `sinopsis`, `duracion`, `imagen`) VALUES
(2, 1, 'The Godfather', 1972, 'US', 'Crimen', 'América, años 40. Don Vito Corleone (Marlon Brando) es el respetado y temido jefe de una de las cinco familias de la mafia de Nueva York. Tiene cuatro hijos: Connie (Talia Shire), el impulsivo Sonny (James Caan), el pusilánime Fredo (John Cazale) y Michael (Al Pacino), que no quiere saber nada de los negocios de su padre. Cuando Corleone, en contra de los consejos de \'Il consigliere\' Tom Hagen (Robert Duvall), se niega a participar en el negocio de las drogas, el jefe de otra banda ordena su asesinato. Empieza entonces una violenta y cruenta guerra entre las familias mafiosas.\nREPARTO', 175, 'https://img.tviso.com/ES/poster/w430/03/2f/032f31c69a25fa42549853d624ac4d06.jpg'),
(3, 1, 'The Godfather: Part II', 1974, 'US', 'Crimen', 'Continuación de la saga de los Corleone con dos historias paralelas: la elección de Michael Corleone como jefe de los negocios familiares y los orígenes del patriarca, el ya fallecido Don Vito, primero en Sicilia y luego en Estados Unidos, donde, empezando desde abajo, llegó a ser un poderosísimo jefe de la mafia de Nueva York.', 202, 'https://img.tviso.com/ES/poster/w430/ff/7a/ff7a991089ea56c1e8f705fb7309e9da.jpg'),
(6, 1, 'The Godfather: Part III', 1990, 'US', 'Crimen', 'Michael Corleone, heredero del imperio de don Vito Corleone, intenta rehabilitarse socialmente y legitimar todas las posesiones de la familia negociando con el Vaticano. Después de luchar toda su vida se encuentra cansado y centra todas sus esperanzas en encontrar un sucesor que se haga cargo de los negocios. Vincent, el hijo ilegítimo de su hermano Sonny, parece ser el elegido.', 162, 'https://img.tviso.com/ES/poster/w430/31/6f/316f9df7c7d548985f35363495f8740d.jpg'),
(7, 2, 'The Amazing Spider-Man', 2012, 'US', 'Accion', 'Un estudiante de secundaria que fue abandonado por sus padres cuando era niño, dejándolo a cargo de su tío Ben (Martin Sheen) y su tía May (Sally Field). Como la mayoría de los adolescentes de su edad, Peter trata de averiguar quién es y qué quiere llegar a ser. Peter también está encontrando su camino con su primer amor de secundaria, Gwen Stacy (Emma Stone), y juntos luchan por su amor con compromiso. Cuando Peter descubre un misterioso maletín que perteneció a su padre, comienza la búsqueda para entender la desaparición de sus padres, una búsqueda que le lleva directamente a Oscorp, el laboratorio del Dr Curt Connors (Rhys Ifans), ex-compañero de trabajo de su padre. Mientras Spider-Man se encuentra en plena colisión con el alter-ego de Connors, el Lagarto, Peter hará elecciones que alterarán sus opciones para usar sus poderes y darán forma a un destino héroico.', 136, 'https://img.tviso.com/ES/poster/w430/c0/e5/c0e536dde920ac75c015fcd865873f4d.jpg'),
(8, 2, 'The Amazing Spider-Man 2', 2014, 'US', 'Accion', 'Peter Parker lleva una vida muy ocupada, compaginando su tiempo entre su papel como Spider-Man, acabando con los malos, y en el instituto con la persona a la que quiere, Gwen. Peter no ve el momento de graduarse. No ha olvidado la promesa que le hizo al padre de Gwen de protegerla, manteniéndose lejos de ella, pero es una promesa que simplemente no puede cumplir. Las cosas cambiarán para Peter cuando aparece un nuevo villano, Electro, y un viejo amigo, Harry Osborn, regresa, al tiempo que descubre nuevas pistas sobre su pasado.', 142, 'https://img.tviso.com/ES/poster/w430/9a/10/9a106c4230d47c915c139ee472486618.jpg'),
(9, 3, 'Tarde para la Ira', 2016, 'ES', 'Drama', 'Madrid, agosto de 2007. Curro entra en prisión tras participar en el atraco a una joyería. Ocho años después sale de la cárcel con ganas de emprender una nueva vida junto a su novia Ana y su hijo, pero se encontrará con una situación inesperada y a un desconocido, José.', 194, 'https://img.tviso.com/ES/poster/w430/7e/84/7e84856535de5bb1f380335da26743cb.jpg'),
(10, 5, 'El ciudadano ilustre', 2016, 'AR', 'Drama / Suspense', 'El ciudadano ilustre cuenta la historia de un argentino Premio Nobel de Literatura que abandonó su pueblo para vivir en Europa. Triunfó escribiendo sobre todo lo que sucedía en su localidad, y cuando el intendente del pueblo lo invita para nombrarlo Ciudadano Ilustre, él decide cancelar todos sus planes y volar de nuevo a su pueblo. Tras años fuera, su llegada desencadena una serie de situaciones entre su figura y las personas del lugar.', 118, 'https://img.tviso.com/ES/poster/w430/82/25/82256e727db5e25d68701127a03326aa.jpg'),
(13, 6, 'La La Land', 2016, 'US', 'Musical', 'Mientras Mia, una aspirate a actriz, sirve cafés a estrellas de cine, Sebastian un Músico de jazz, se gana la vida tocando en sucios bares. Tras algunos encuentros inesperados, las chispas entre ellos estallan. Pero la lucha por conseguir lo que quieren amenaza con separarlos.', 126, 'https://img.tviso.com/ES/poster/w430/18/20/1820a5f72e14f43362644e690fe99bf4.jpg'),
(15, 7, 'La Llegada', 2016, 'US', 'Drama', 'Cuando misteriosas naves espaciales aterrizan en todo el mundo, un equipo de élite (Jeremy Renner y Forest Whitaker) liderado por la lingüista Louise Banks (Amy Adams) intentan descifrar el motivo de su visita. A medida que la humanidad se tambalea al borde de una guerra, Louise y su equipo luchan contra el tiempo llegando a poner en peligro su vida y, muy posiblemente, la del resto de la humanidad.', 116, 'https://img.tviso.com/ES/poster/w430/3f/d9/3fd9b25b9928b779b0fd452aaefc4f6c.jpg'),
(17, 8, 'Moonlight', 2016, 'US', 'Drama', 'Chiron es un joven afroamericano con una difícil infancia, adolescencia y madurez que crece en una zona conflictiva de Miami. A medida que pasan los años, el joven se descubre a sí mismo y encuentra el amor en lugares inesperados. Al mismo tiempo, tiene que hacer frente a la incomprensión de su familia y a la violencia de los chicos del barrio.', 111, 'https://img.tviso.com/ES/poster/w430/29/61/2961156d6efb79d7c877fa8f3b5df22e.jpg'),
(19, 9, 'Interstellar', 2014, 'US', 'Aventura', 'Narra las aventuras de un grupo de exploradores que hacen uso de un agujero de gusano recientemente descubierto para superar las limitaciones de los viajes espaciales tripulados y vencer las inmensas distancias que tiene un viaje interestelar.', 169, 'https://img.tviso.com/ES/poster/w430/52/b2/52b27c3a04145b955469dfd5093ef4df.jpg'),
(20, 10, 'Zootropolis', 2016, 'US', 'Animacion', 'La moderna metrópoli mamífera de Zootrópolis es una ciudad absolutamente única. Está compuesta de barrios con diferentes hábitats como la lujosa Sahara Square y la gélida Tundratown. Es un crisol donde los animales de cada entorno conviven, un lugar donde no importa lo que seas. De hecho puedes ser cualquier cosa, desde un elefante enorme hasta la musaraña más diminuta. Pero cuando llega la optimista agente Judy Hopps, descubre que ser la primera conejita de un cuerpo policial compuesto de animales duros y enormes no es nada fácil. Pero está decidida a demostrar su valía y se mete de cabeza en un caso, a pesar de que eso significa trabajar con Nick Wilde, un zorro parlanchín y estafador, para resolver el misterio.', 108, 'https://img.tviso.com/ES/poster/w430/92/94/929444c8ae6b79314a377278c3703870.jpg');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `actor`
--
ALTER TABLE `actor`
  ADD PRIMARY KEY (`idActor`),
  ADD KEY `idActor` (`idActor`);

--
-- Indices de la tabla `actorpelicula`
--
ALTER TABLE `actorpelicula`
  ADD PRIMARY KEY (`idActorPelicula`),
  ADD KEY `idActor` (`idActor`),
  ADD KEY `idActorPelicula` (`idActorPelicula`),
  ADD KEY `idPelicula` (`idPelicula`);

--
-- Indices de la tabla `director`
--
ALTER TABLE `director`
  ADD PRIMARY KEY (`idDirector`);

--
-- Indices de la tabla `pelicula`
--
ALTER TABLE `pelicula`
  ADD PRIMARY KEY (`idPelicula`),
  ADD KEY `idDirector` (`idDirector`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `actor`
--
ALTER TABLE `actor`
  MODIFY `idActor` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;
--
-- AUTO_INCREMENT de la tabla `actorpelicula`
--
ALTER TABLE `actorpelicula`
  MODIFY `idActorPelicula` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;
--
-- AUTO_INCREMENT de la tabla `director`
--
ALTER TABLE `director`
  MODIFY `idDirector` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT de la tabla `pelicula`
--
ALTER TABLE `pelicula`
  MODIFY `idPelicula` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `actorpelicula`
--
ALTER TABLE `actorpelicula`
  ADD CONSTRAINT `actor-actorPelicula` FOREIGN KEY (`idActor`) REFERENCES `actor` (`idActor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `pelicula-actorPelicula` FOREIGN KEY (`idPelicula`) REFERENCES `pelicula` (`idPelicula`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pelicula`
--
ALTER TABLE `pelicula`
  ADD CONSTRAINT `pelicula_ibfk_1` FOREIGN KEY (`idDirector`) REFERENCES `director` (`idDirector`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
