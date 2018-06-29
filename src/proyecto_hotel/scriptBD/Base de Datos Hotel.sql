drop database Hotel;
create database Hotel;
use Hotel;


create table Usuario (
    Nombre_usuario varchar(30) primary key not null,
    Contrasena varchar(30) not null,
    Tipo_cuenta int,
    Estado int,
    Eliminado int,
    Id_cliente int,
    Imagen varchar(500)
);


create table Cliente
(
    Id_cliente int primary key not null auto_increment,	
    Primer_nombre varchar(30) not null,
    Segundo_nombre varchar(30),	
    Primer_apellido varchar(30) not null,
    Segundo_apellido varchar(30),	
    Identificacion varchar(30) not null,
    Tipo_identificacion varchar(30) not null,
    Pais_origen varchar(30) not null,	
    Numero_reserva int not null,
    Numero_estancia int not null,
    GPA double,	
    Fecha_inscripcion date,	
    Fecha_nacimiento date,
    Telefono varchar(15),
    Correo varchar(30),
    Eliminado int,
    Imagen varchar(500)
);


create table Reserva
(
	Id_reserva int primary key not null auto_increment,	
	Id_cliente int,
	Fecha_inicio datetime,
	Fecha_final	datetime,
	Estado varchar(30),
	Costo_total	double,
	Id_habitacion int,
	Fecha_reserva datetime

);


create table Estancia
(
    Id_estancia int primary key not null auto_increment,	
    Id_cliente int,
    Id_habitacion int,
    Fecha_inicio datetime,
    Fecha_final	datetime,
    Costo_total	double,
    Descripcion	varchar(100),
    Estado varchar(100),
    Id_reserva int

);


create table Acciones
(
    Id_accion int primary key not null auto_increment,	
    Nombre varchar(30),
    -- Ese nombre puede confundirse con el nombre del cliente
    Descripcion	varchar(200),
    Fecha_hora datetime,
    Tipo varchar(30),
    Id_cliente int

);


create table Habitacion(
    Id_habitacion int primary key not null auto_increment,
    Nombre varchar(30),	
    Tipo varchar(15),
    Tarifa double,
    Telefono varchar(20),
    Estado int,
    Descripcion varchar(100),
    Eliminado int,
    Imagen varchar(500)
);


create table Servicio_al_cuarto(
	Id_servicio_cuarto int primary key not null auto_increment,
	Id_estancia int,
	Fecha_hora datetime,	
	Costo_total double
);


create table Producto(
	Id_producto int primary key not null auto_increment,
	Nombre varchar(30),
	Precio double,
	Tipo_producto varchar(30),
	Cantidad int,
	Estado int,
    Eliminado int,
	Imagen varchar(500)
);


create table Detalle_servicio_al_cuarto(
	Id_detalle_servicio_al_cuarto int primary key not null auto_increment,	
	Id_servicio_cuarto int,	
	Id_producto	int,
	Precio	double,
	Cantidad int,	
	Sub_total double
);


create table Pago (id_pago int primary key auto_increment,
 monto  double, 
 concepto varchar(50),
 id_origen int,
 fecha date,
 id_cliente int
 , tipo_pago varchar (30),
 Tarjeta varchar(40)    
 );
 
 /*Si es en Efectivo es null*/
 /*Tipo pago es Tarjeta de credito y Efectivo*/
 create table Certificados(
 Id_Certificado varchar(30) primary key,
 Id_Origen int,
 Origen varchar(30),
 Cantidad int
 );
Create Table Tarjeta(Id_tarjeta int primary key auto_increment,
monto double);


alter table Usuario add foreign key (id_cliente) references Cliente(id_cliente);

alter table Pago add foreign key (id_cliente) references Cliente(id_cliente);
alter table Reserva add foreign key (Id_cliente) references Cliente(Id_cliente);
alter table Reserva add foreign key (Id_habitacion) references Habitacion(Id_habitacion);
alter table Estancia add foreign key (Id_cliente) references Cliente(Id_cliente);
alter table Estancia add foreign key (Id_habitacion) references Habitacion(Id_habitacion);
alter table Estancia add foreign key (Id_reserva) references Reserva(Id_reserva);
alter table Acciones add foreign key (Id_cliente) references Cliente(Id_cliente);
alter table Servicio_al_cuarto add foreign key (Id_estancia) references Estancia(Id_estancia);
alter table Detalle_servicio_al_cuarto add foreign key (Id_servicio_cuarto) references Servicio_al_cuarto(Id_servicio_cuarto);
alter table Detalle_servicio_al_cuarto  add foreign key (Id_producto) references Producto(Id_producto);

-----------------------------Triggers -------------------------

1. 

DELIMITER //
CREATE TRIGGER Registrar_Nueva_Reserva AFTER INSERT ON Reserva
		FOR EACH ROW
		BEGIN
set @cadena=concat('El cliente ha contratado la reserva (',new.Id_reserva,')  en el hotel del ',cast(new.Fecha_inicio as date),' al ',cast(new.Fecha_final as date));
insert Acciones(Nombre,Descripcion,Fecha_hora,Tipo,Id_cliente) 
values('Contrato de Reserva',@cadena,
 now() ,'Protocolar',new.Id_cliente);
 
		END;//
DELIMITER ;
2. 
DELIMITER //
CREATE TRIGGER Registrar_Nueva_Estancia AFTER INSERT ON Estancia
		FOR EACH ROW
		BEGIN
set @cadena=concat('El cliente ha contratado la estancia (',new.Id_estancia,')  en el hotel del ',cast(new.Fecha_inicio as date),' al ',cast(new.Fecha_final as date));
insert Acciones(Nombre,Descripcion,Fecha_hora,Tipo,Id_cliente) 
values('Contrato de Estancia',@cadena,
 now() ,'Protocolar',new.Id_cliente);
 
		END;//
DELIMITER ;
3. 
DELIMITER //
CREATE TRIGGER Registrar_Update_Estancia AFTER UPDATE ON Estancia
		FOR EACH ROW
		BEGIN

		IF lower(New.Estado)=lower('Cancelado') or lower(New.Estado)=lower('Cancelada') then
         set @cadena=concat('El cliente ha Pagado la estancia (',new.Id_estancia,')  en el hotel del ',cast(new.Fecha_inicio as date),' al ',cast(new.Fecha_final as date),' Con un monto de C$',new.Costo_total);
          insert Acciones(Nombre,Descripcion,Fecha_hora,Tipo,Id_cliente) 
          values('Cancelacion de Estancia',@cadena, now() ,'Protocolar',new.Id_cliente);
         end if;
         IF lower(New.Estado)=lower('Inactivo') or lower(New.Estado)=lower('Inactiva') then
         set @cadena=concat('El cliente ha Eliminado ó no la Pago la estancia(',new.Id_estancia,')  en el hotel del ',cast(new.Fecha_inicio as date),' al ',cast(new.Fecha_final as date));
        insert Acciones(Nombre,Descripcion,Fecha_hora,Tipo,Id_cliente) 
          values('Eliminar Estancia',@cadena, now() ,'Aviso',new.Id_cliente);
        end if;

 
		END;//
DELIMITER ;
4. 
DELIMITER //
CREATE TRIGGER Registrar_Update_Reserva AFTER UPDATE ON Reserva
		FOR EACH ROW
		BEGIN

		IF lower(New.Estado)=lower('Cancelado') or lower(New.Estado)=lower('Cancelada') then
		 set @aux=Round(new.Costo_total*0.3,2);
         set @cadena=concat('El cliente ha Concretado la reserva (',new.Id_reserva,') del ',cast(new.Fecha_inicio as date),' al ',cast(new.Fecha_final as date),'. Con adelanto C$',@aux);
          insert Acciones(Nombre,Descripcion,Fecha_hora,Tipo,Id_cliente) 
          values('Confirmacion de Reserva',@cadena, now() ,'Protocolar',new.Id_cliente);
         end if;
         IF lower(New.Estado)=lower('Perdido') or lower(New.Estado)=lower('Perdida') then
         set @cadena=concat('El cliente ha Eliminado ó no la Pago la reserva (',new.Id_reserva,')  en el hotel del ',cast(new.Fecha_inicio as date),' al ',cast(new.Fecha_final as date));
        insert Acciones(Nombre,Descripcion,Fecha_hora,Tipo,Id_cliente) 
          values('Eliminar Reserva',@cadena, now() ,'Aviso',new.Id_cliente);
        end if;

 
		END;//
DELIMITER ;

5.
 
DELIMITER //
CREATE TRIGGER Registrar_Nuevo_Cliente AFTER INSERT ON Cliente
		FOR EACH ROW
		BEGIN
         set @cadena=concat('Se ha incrito el cliente ',new.Primer_nombre,' ',new.Primer_apellido,' con  ID: ',new.Id_cliente);
		 insert Acciones(Nombre,Descripcion,Fecha_hora,Tipo,Id_cliente) 
		 values('Nuevo Cliente',@cadena, now() ,'Protocolar',new.Id_cliente);
		END;//
DELIMITER ;

6. 
DELIMITER //
CREATE TRIGGER Registrar_Eliminar_Cliente after delete ON Cliente
		FOR EACH ROW
		BEGIN
         set @cadena=concat('Se ha Eliminado el cliente ',old.Primer_nombre,' ',old.Primer_apellido,' con  ID: ',old.Id_cliente);
		 insert Acciones(Nombre,Descripcion,Fecha_hora,Tipo,Id_cliente) 
		 values('Eliminar Cliente',@cadena, now() ,'Aviso',old.Id_cliente);
		END;//
DELIMITER ;


7. 

DELIMITER //
CREATE TRIGGER Registrar_Nuevo_Pago after insert ON Pago
		FOR EACH ROW
		BEGIN
         set @cadena=concat('El cliente ha realizado : ',new.concepto,' en ',new.tipo_pago,' con un importe de C$ ',new.monto, '. Pago(',new.Id_pago,')');
		 insert Acciones(Nombre,Descripcion,Fecha_hora,Tipo,Id_cliente) 
		 values('Pago de Cliente',@cadena, now() ,'Protocolar',new.Id_cliente);
		END;//
DELIMITER ;
8. 
DELIMITER //
CREATE TRIGGER Registrar_Eliminar_Pago after delete ON Pago
		FOR EACH ROW
		BEGIN
         set @cadena=concat('Se ha eliminado : ',old.concepto,' en ',old.tipo_pago,' con un importe de C$ ',old.monto, '. Pago(',old.Id_pago,')');
		 insert Acciones(Nombre,Descripcion,Fecha_hora,Tipo,Id_cliente) 
		 values('Eliminar pago de cliente',@cadena, now() ,'Aviso',old.Id_cliente);
		END;//
DELIMITER ;

9. 
DELIMITER //
CREATE TRIGGER Registrar_Modificacion_Cliente after update ON Cliente
		FOR EACH ROW
		BEGIN
		set @cadena=concat('Se ha modificado datos cliente : ',old.Id_cliente,' (',old.Primer_nombre, ',',old.Primer_apellido,',',old.Tipo_identificacion,',',old.Identificacion,',',old.Pais_origen,',',old.Imagen,')');
        if old.Imagen=new.Imagen then
         set @cadena=concat('Se ha modificado datos cliente : ',old.Id_cliente,' (',old.Primer_nombre,',',old.Segundo_nombre, ',',old.Primer_apellido,',',old.Segundo_apellido,',',old.Tipo_identificacion,',',old.Identificacion,',',old.Pais_origen,')');
        end if;
		 insert Acciones(Nombre,Descripcion,Fecha_hora,Tipo,Id_cliente) 
		 values('Modificar Cliente',@cadena, now() ,'Protocolar',old.Id_cliente);
		END;//
DELIMITER ;

10. 

DELIMITER //

CREATE FUNcTION Id_cliente_para_Servicio( id int) RETURNS VARCHAR(20)
BEGIN
    RETURN  (select Id_cliente from Estancia where Id_estancia=id);
END ;//

DELIMITER //
CREATE TRIGGER Registrar_Nuevo_Servicio after insert ON servicio_al_cuarto
		FOR EACH ROW
		BEGIN
        set @Id_cliente=Id_cliente_para_Servicio(new.Id_estancia);
		set @cadena=concat('Se ha registrado un  servivio al cuarto : ID(',New.Id_servicio_cuarto,') con un monto de C$ ',new.Costo_total);
		 insert Acciones(Nombre,Descripcion,Fecha_hora,Tipo,Id_cliente) 
		 values('Servicio al Cuarto',@cadena, now() ,'Protocolar',@Id_cliente);
		END;//
DELIMITER ;


//11. 
DELIMITER //
CREATE TRIGGER Registrar_Eliminar_Servicio after delete ON servicio_al_cuarto
		FOR EACH ROW
		BEGIN
        set @Id_cliente=Id_cliente_para_Servicio(old.Id_estancia);
         set @cadena=concat('Se ha eliminado Servicio : ID(',old.Id_servicio_cuarto,') con un importe de C$ ',old.Costo_total, '. Estancia(',old.Id_estancia,')');
		 insert Acciones(Nombre,Descripcion,Fecha_hora,Tipo,Id_cliente) 
		 values('Eliminar Servicio al cuarto',@cadena, now() ,'Aviso', @Id_cliente);
		END;//
DELIMITER ;

/*Trigger para reservas*/
12.
DELIMITER //
CREATE TRIGGER Conteo_Reservas after insert ON Reserva
	FOR EACH ROW
	BEGIN
		
        set @conteoReservas = (select count(Id_reserva) from Reserva where Id_cliente = new.Id_cliente);
        update Cliente set Numero_reserva = @conteoReservas where Id_cliente = new.Id_cliente;

	END;//
DELIMITER ;

/* */
13. 
DELIMITER //
CREATE TRIGGER Conteo_Reservas2 after delete ON Reserva
	FOR EACH ROW
	BEGIN
		
        set @conteoReservas = (select count(Id_reserva) from Reserva where Id_cliente = old.Id_cliente);
        update Cliente set Numero_reserva = @conteoReservas where Id_cliente = old.Id_cliente;

	END;//
DELIMITER ;

/*Trigger para estancia*/
14. 
DELIMITER //
CREATE TRIGGER Conteo_Estancia after insert ON Estancia
	FOR EACH ROW
	BEGIN
		
        set @conteoEstancia = (select count(Id_estancia) from Estancia where Id_cliente = new.Id_cliente);
        update Cliente set Numero_estancia = @conteoEstancia where Id_cliente = new.Id_cliente;

	END;//
DELIMITER ;

/* */
15. 
DELIMITER //
CREATE TRIGGER Conteo_Estancia2 after delete ON Estancia
	FOR EACH ROW
	BEGIN
		
        set @conteoEstancia = (select count(Id_estancia) from Estancia where Id_cliente = old.Id_cliente);
        update Cliente set Numero_estancia = @conteoEstancia where Id_cliente = old.Id_cliente;

	END;//
DELIMITER ;

16.
DELIMITER //
CREATE TRIGGER Habitacion_ocupada after insert ON Estancia
	FOR EACH ROW
	BEGIN
		
       update Habitacion set Estado =2 where Id_habitacion =new.Id_habitacion;

	END;//
DELIMITER ;
17.
DELIMITER //
CREATE TRIGGER Habitacion_disponible after delete ON Estancia
	FOR EACH ROW
	BEGIN
		
       update Habitacion set Estado =1 where Id_habitacion =old.Id_habitacion;

	END;//
DELIMITER ;





-----------------------------------Insert------------------------------------------

insert into Tarjeta(monto) values(20000);
insert into Producto (Nombre, Precio, Tipo_producto, Cantidad, Estado,Imagen) values ('Fresco de cacao', 30, 'Bebida', 25, 1,'fresco de cacao.jpg');
insert into Producto (Nombre, Precio, Tipo_producto, Cantidad, Estado,Imagen) values ('Vodka', 75, 'Bebida', 16, 2,'vodka.jpg');
insert into Producto (Nombre, Precio, Tipo_producto, Cantidad, Estado,Imagen) values ('Tres leches', 20, 'Postre', 22, 1, 'tres leches.jpg');
insert into Producto (Nombre, Precio, Tipo_producto, Cantidad, Estado,Imagen) values ('Pina colada', 62, 'Bebida', 26, 1, 'pina colada.jpg');
insert into Producto (Nombre, Precio, Tipo_producto, Cantidad, Estado,Imagen) values ('Heineken Beer', 125, 'Bebida', 17, 2, 'heineken.jpg');
insert into Producto (Nombre, Precio, Tipo_producto, Cantidad, Estado,Imagen) values ('French Brownie', 200, 'Postre', 20, 1, 'french brownie.jpg');
insert into Producto (Nombre, Precio, Tipo_producto, Cantidad, Estado,Imagen) values ('Snicker Barra', 60, 'Dulce', 150, 1, 'snicker.jpg');
SET SQL_SAFE_UPDATES = 0;


update Producto set Eliminado = 0;

insert into Habitacion (Nombre, Tipo, Tarifa, Telefono, Estado, Descripcion,Imagen)
values ('Brownie Room','Deluxe',45.06,'+505 22459851',1,'La mejor de todas','cuarto 1.jpg');

insert into Habitacion (Nombre, Tipo, Tarifa, Telefono, Estado, Descripcion,Imagen)
values ('Candy Room','Familiar',90.50,'+505 22548216',2,'La mejor para la familia','cuarto 2.jpg');

insert into Habitacion (Nombre, Tipo, Tarifa, Telefono, Estado, Descripcion,Imagen)
values ('Sweet Dreams Room','Doble',120.55,'+ 505 22786591',1,'La mejor para disfrutar en pareja','cuarto 3.jpg');

insert into Habitacion (Nombre, Tipo, Tarifa, Telefono, Estado, Descripcion,Imagen)
values ('Ocean Room','Familiar',250.00,'+ 505 22786591',2,'La mejor para disfrutar en pareja','cuarto 4.jpg');

insert into Habitacion (Nombre, Tipo, Tarifa, Telefono, Estado, Descripcion,Imagen)
values ('Couple Room','Doble',180.00,'+ 505 22786591',1,'La mejor para disfrutar en pareja','cuarto 5.jpg');

insert into Habitacion (Nombre, Tipo, Tarifa, Telefono, Estado, Descripcion,Imagen)
values ('Super Party Room','Deluxe',330.00,'+ 505 22786591',2,'La mejor para disfrutar en pareja','cuarto 6.jpg');

update Habitacion set Eliminado = 0;

insert into Usuario (Nombre_usuario, Contrasena, Tipo_cuenta, Estado, Imagen) values ('David','Salguera',1,1,'jarry.png');
insert into Usuario (Nombre_usuario, Contrasena, Tipo_cuenta, Estado, Imagen) values ('Manuel','Lopez',2,1,'bob.png');

insert into Cliente 
(Primer_nombre,Segundo_nombre,Primer_apellido,Segundo_apellido,Identificacion,Tipo_identificacion,Pais_origen,Numero_reserva,Numero_estancia,GPA,
 Fecha_inscripcion,Fecha_nacimiento,Telefono,Correo,Imagen) values
('Donald','Jack','Trump','Musk','001-230999-1023A','Cedula','Estados Unidos',0,0,15000000.541,'2015-12-17','2015-12-17','+001 512 35612','donald@gmail.com','donald.jpg');

insert into Cliente
(Primer_nombre,Segundo_nombre,Primer_apellido,Segundo_apellido,Identificacion,Tipo_identificacion,Pais_origen,Numero_reserva,Numero_estancia,GPA,
 Fecha_inscripcion,Fecha_nacimiento,Telefono,Correo,Imagen) values
('Manuel','Enrique','Oviedo','Cardenal','001-270898-1084C','Cedula','Nicaragua',0,0,230000.541,'2018-05-21','2018-05-17','+001 512 35612','manuel_enrique@gmail.com','obama.jpg');

update Cliente set Eliminado = 0;

insert into Estancia(Id_cliente,Id_habitacion,Fecha_inicio,Fecha_final,Costo_total,Descripcion,Estado,Id_reserva)
values (1,1,'2018-06-10','2018-06-16',1222.23,'Todo bien','Activo',null);

-- Estado = 1 ; Activo

insert into servicio_al_cuarto (Id_estancia,Fecha_hora,Costo_total)
values (1,now(),200);

insert into Servicio_al_Cuarto(Id_estancia,Fecha_hora,Costo_total) values(1,now(),300);
insert into Servicio_al_Cuarto(Id_estancia,Fecha_hora,Costo_total) values(1,now(),45);
insert into Servicio_al_Cuarto(Id_estancia,Fecha_hora,Costo_total) values(1,now(),4);
 

/*---------------------------Procedimientos----------------------------------------------*/
//Procedimiento para que agreges una reserva
//1.

DELIMITER //

CREATE FUNcTION get_Verificar_Reserva(DId_habitacion int,DFecha_inicio date,DFecha_Final date) RETURNS int
BEGIN
    RETURN  (select count(*) from Reserva where Id_habitacion=DId_habitacion and upper(Estado)=upper("Espera") and Fecha_final>=DFecha_inicio and Fecha_inicio<=DFecha_Final);
END ;//


DELIMITER //

CREATE FUNcTION get_Ultima_Reserva() RETURNS int
BEGIN
    RETURN  (select Id_reserva from Reserva order by Id_reserva desc limit 1);
END ;//

DELIMITER //

CREATE FUNcTION get_Tarifa(DId_habitacion int) RETURNS int
BEGIN
    RETURN  (select Tarifa from Habitacion where Id_habitacion=DId_habitacion);
END ;//


DROP PROCEDURE IF EXISTS setReserva;

DELIMITER $$
 
CREATE PROCEDURE setReserva(DId_cliente int,DId_habitacion int,DFecha_inicio date,DFecha_Final date, DTipo_pago varchar (30),DTarjeta varchar(40))
BEGIN
set @dias=  datediff(DFecha_Final,DFecha_inicio)+1;
 set @errores=0;
  iF @dias<=0 or datediff(DFecha_inicio,cast( now() as date))<5 then
  set @errores=1;
  Select "Verificar las fechas, puede que una o ambas sean invalidas."as mensaje;
  end if;
  set @Reservas_conflictivas=get_Verificar_Reserva(DId_habitacion ,DFecha_inicio,DFecha_Final);
  if @Reservas_conflictivas>0 then
  set @errores=1;
  Select "Seleccione Otra habitacion o modifique las fechas, existe conflicto con las reservas."as mensaje;
  end if;
  
  if @errores=0 then
    
set @tarifa=get_Tarifa(DId_habitacion);
set @costo=@tarifa*@dias;
insert Reserva(Id_cliente,Id_habitacion,Fecha_inicio,Fecha_final,Fecha_reserva,Estado,Costo_total)
values(DId_cliente,DId_habitacion,DFecha_inicio,DFecha_Final,cast( now() as date),'Espera',@costo);
insert into Pago(monto,concepto,fecha,id_origen,id_cliente,tipo_pago,Tarjeta) 
values(@costo*0.1,"Pago de Reserva",now(),get_Ultima_Reserva(),DId_cliente,DTipo_pago,DTarjeta);
select "La Reserva fue contratada con exito." as mensaje;
  end if;

END $$
DELIMITER ;


call setReserva(1,5,"2018-06-2","2018-07-2","Efectivo",null);



2.
 
 
DELIMITER //

CREATE FUNcTION get_ID_Certificado(DId_reserva int) RETURNS varchar(30)
BEGIN
    RETURN  (select concat(Reserva.Id_reserva,Cliente.Id_cliente,Reserva.Id_habitacion,Pago.Id_pago) from Reserva
inner join Habitacion on Habitacion.Id_habitacion=Reserva.Id_habitacion
inner join Cliente on Cliente.Id_cliente=Reserva.Id_cliente 
inner join Pago on Pago.Id_origen=Reserva.Id_reserva and upper(Pago.concepto)=upper("Pago de Reserva")
 where Reserva.Id_reserva=DId_reserva );
END ;//


DELIMITER //

CREATE FUNcTION get_Cantidad_Certificado(DId_Certificado varchar(30)) RETURNS int
BEGIN

    RETURN  (select Cantidad from Certificados where Certificados.Id_Certificado=DId_Certificado);
END ;//


DROP PROCEDURE IF EXISTS getCertificado_Reserva; 
DELIMITER $$
CREATE PROCEDURE getCertificado_Reserva(DId_reserva int,Tipo_User varchar (40),permiso int)
BEGIN
set @documento="Oficial";
SET lc_time_names = 'es_ES';
if permiso=1 then 
if upper(Tipo_User)=upper("Administrador") then
set @max_cant=5;
elseif upper(Tipo_User)=upper("Secretario") then
set @max_cant=2;
elseif  upper(Tipo_User)=upper("Visitante") then
set @max_cant=1;
end if;
set @Id_certificado=get_ID_Certificado(DId_reserva);
if @Id_certificado is not null then
set @cantidad=get_Cantidad_Certificado(@Id_certificado);
 if @cantidad is null then
insert into Certificados(Id_Certificado,Id_Origen,Origen,Cantidad)
values(@Id_certificado,DId_reserva,"Certificado de Reserva",1);
 select *,@documento,concat(day(Reserva.Fecha_inicio)," de ",monthname(Reserva.Fecha_inicio)," del ",year( Reserva.Fecha_inicio)) as fecha_incio,
concat(day(Reserva.Fecha_final)," de ",monthname(Reserva.Fecha_final)," del ",year( Reserva.Fecha_final)) as fecha_finall,
concat(day(now())," de ",monthname(now())," del ",year( now())) as fecha_hoy, DATE_FORMAT(NOW( ), "%H:%I:%S" ) as hora_hoy from Reserva
inner join Habitacion on Habitacion.Id_habitacion=Reserva.Id_habitacion
inner join Cliente on Cliente.Id_cliente=Reserva.Id_cliente
inner join Pago on Pago.Id_origen=Reserva.Id_reserva and upper(Pago.concepto)=upper("Pago de Reserva")
inner join Certificados on Certificados.Id_Origen= Reserva.Id_reserva and upper(Certificados.Origen)=upper("Certificado de Reserva")
 where Reserva.Id_reserva=DId_reserva  ;
elseif  @cantidad<@max_cant  then
 update Certificados set Cantidad=Cantidad+1 where Id_Certificado=@Id_certificado;
 select *,@documento,concat(day(Reserva.Fecha_inicio)," de ",monthname(Reserva.Fecha_inicio)," del ",year( Reserva.Fecha_inicio)) as fecha_incio,
concat(day(Reserva.Fecha_final)," de ",monthname(Reserva.Fecha_final)," del ",year( Reserva.Fecha_final)) as fecha_finall,
concat(day(now())," de ",monthname(now())," del ",year( now())) as fecha_hoy, DATE_FORMAT(NOW( ), "%H:%I:%S" ) as hora_hoy from Reserva
 inner join Habitacion on Habitacion.Id_habitacion=Reserva.Id_habitacion
 inner join Cliente on Cliente.Id_cliente=Reserva.Id_cliente
 inner join Pago on Pago.Id_origen=Reserva.Id_reserva and upper(Pago.concepto)=upper("Pago de Reserva")
 inner join Certificados on Certificados.Id_Origen= Reserva.Id_reserva and upper(Certificados.Origen)=upper("Certificado de Reserva")
 where Reserva.Id_reserva=DId_reserva  ;
 else 
 select concat( "Se ha excedido el numero de replica permitidos para ",Tipo_User) as mensaje;
 end if;
   else
   select "La Reserva no existe o el pago no se registro." as mensaje;
end if;
else
set @documento="Copia";
select *,@documento,concat(day(Reserva.Fecha_inicio)," de ",monthname(Reserva.Fecha_inicio)," del ",year( Reserva.Fecha_inicio)) as fecha_incio,
concat(day(Reserva.Fecha_final)," de ",monthname(Reserva.Fecha_final)," del ",year( Reserva.Fecha_final)) as fecha_finall,
concat(day(now())," de ",monthname(now())," del ",year( now())) as fecha_hoy, DATE_FORMAT(NOW( ), "%H:%I:%S" ) as hora_hoy from Reserva
inner join Habitacion on Habitacion.Id_habitacion=Reserva.Id_habitacion
inner join Cliente on Cliente.Id_cliente=Reserva.Id_cliente
inner join Pago on Pago.Id_origen=Reserva.Id_reserva and upper(Pago.concepto)=upper("Pago de Reserva")
inner join Certificados on Certificados.Id_Origen= Reserva.Id_reserva and upper(Certificados.Origen)=upper("Certificado de Reserva")
 where Reserva.Id_reserva=DId_reserva  ;

end if;

END $$
DELIMITER ;

call getCertificado_Reserva(1,"Administrador",1);
call getCertificado_Reserva(2,"Secretario",1);
call getCertificado_Reserva(1,"Visitante",0);




3.

DROP PROCEDURE IF EXISTS getVerificar; 
DELIMITER $$
CREATE PROCEDURE  getVerificar(fecha_inicio date,fecha_final date)
BEGIN

SET lc_time_names = 'es_ES';
select datediff(fecha_final,fecha_inicio) as diferencia_rango, datediff(fecha_inicio ,cast( now() as date)) as diferencia_actual, concat(day(ADDDATE(now(), INTERVAL 5 DAY))," de ",monthname(ADDDATE(now(), INTERVAL 5 DAY))," del ",year( ADDDATE(now(), INTERVAL 5 DAY))) as fecha_permitida;



END $$
DELIMITER ;




4.
DROP PROCEDURE IF EXISTS getHabitaciones_disponibles; 
DELIMITER $$
CREATE PROCEDURE   getHabitaciones_disponibles(DFecha_inicio date,DFecha_Final  date)
BEGIN
select * from Habitacion where Eliminado=0 and get_Verificar_Reserva(Id_habitacion,DFecha_inicio,DFecha_Final)=0;

END $$
DELIMITER ;

5.
DROP PROCEDURE IF EXISTS getCosto_total; 
DELIMITER $$
CREATE PROCEDURE  getCosto_total(DFecha_inicio date,DFecha_Final  date,DId_habitacion int)
BEGIN
set @dias=  datediff(DFecha_Final,DFecha_inicio)+1;
select @dias*Tarifa as Costo_total,@dias as dias from Habitacion where Id_habitacion=DId_habitacion;
END $$
DELIMITER ;



call getCosto_total('2018-03-2','2018-04-4',1)



6.
DROP PROCEDURE IF EXISTS getHabitaciones_disponibles_buscar; 
DELIMITER $$
CREATE PROCEDURE   getHabitaciones_disponibles_buscar(DFecha_inicio date,DFecha_Final  date, filtro varchar(30),buscar varchar(50))
BEGIN
if filtro='Nombre' then
select * from Habitacion where Eliminado=0 and get_Verificar_Reserva(Id_habitacion,DFecha_inicio,DFecha_Final)=0 and Nombre like concat('%',buscar,'%');

end if;
if filtro='Tipo' then
select * from Habitacion where Eliminado=0 and get_Verificar_Reserva(Id_habitacion,DFecha_inicio,DFecha_Final)=0 and Tipo like concat('%',buscar,'%');

end if;
if filtro='Tarifa <=' then
select * from Habitacion where Eliminado=0 and get_Verificar_Reserva(Id_habitacion,DFecha_inicio,DFecha_Final)=0 and tarifa <= CAST(buscar AS DECIMAL(65,30)) ;

end if;

END $$
DELIMITER ;

 call getHabitaciones_disponibles_buscar('2018-06-30','2018-06-30','Tarifa <=','180');


7. 
DELIMITER //

CREATE FUNcTION get_monto(DId_tarjeta int) RETURNS double
BEGIN

    RETURN  (Select monto from Tarjeta where Id_tarjeta=DId_Tarjeta);
END ;//

DROP PROCEDURE IF EXISTS SetPago; 
DELIMITER $$
CREATE PROCEDURE  SetPago(Dmonto double, DId_tarjeta int)
BEGIN
set @monto= get_monto( DId_tarjeta);

if  @monto is null then
select "No existe la tarjeta con ese numero." as mensaje;
else

if @monto<Dmonto then
select "Tarjeta sin fondo suficiente." as mensaje;
else
Update  Tarjeta set monto=monto-Dmonto where Id_tarjeta=DId_tarjeta;
select "perfecto" as mensaje;
end if;
end if;
END $$
DELIMITER ;

call setPago(200,1);

8.
DROP PROCEDURE IF EXISTS setUsuario;

DELIMITER $$

CREATE PROCEDURE setUsuario(

in pnombre varchar(50), 
in snombre varchar(50), 
in papellido varchar(50), 
in sapellido varchar(50), 
in ident varchar (50),
in tipoident varchar(50),
in pais varchar(50),
in nreserva int,
in nestancia int,
in fecha_inc date,
in fecha_nac date,
in telefono varchar(50),
in correo varchar(50),
in imagen varchar(100),
in eliminado int,

in nombre_user varchar(50),
in pass varchar(50),
in tipo int, 
in estado int,
in eliminaUser int)

BEGIN 
       insert into Cliente 
       (Primer_nombre,Segundo_nombre,Primer_apellido,Segundo_apellido,Identificacion,Tipo_identificacion,
       Pais_origen,Numero_reserva,Numero_estancia,
	   Fecha_inscripcion,Fecha_nacimiento,Telefono,Correo,Imagen,Eliminado) 
       values 
       (pnombre,snombre,papellido,sapellido,ident,tipoident,pais,nreserva,nestancia,fecha_nac,fecha_inc,telefono,correo,imagen,0);
      
       set @numero = (select count(Id_cliente) from Cliente);
       set @imagen2 = imagen;
      
	   insert into Usuario 
       (Nombre_usuario,Contrasena,Tipo_cuenta,Estado,Eliminado,Id_cliente,Imagen) values 
       (nombre_user,pass,3,estado,eliminaUser,null,@imagen2);
       
       update Usuario set Id_cliente = (@numero) where Nombre_usuario = nombre_user;
END $$
DELIMITER ;

/*call setUsuario();*/

9.
DROP PROCEDURE IF EXISTS get_Reserva_consulta; 
DELIMITER $$
CREATE PROCEDURE  get_Reserva_consulta()
BEGIN
select *,concat(Cliente.Primer_nombre,' ',Cliente.Segundo_nombre,' ',Cliente.Primer_apellido,' ',Cliente.Segundo_apellido) as nombre_cliente,
Habitacion.Nombre as nombre_habitacion, Cliente.Imagen as imgcliente, Habitacion.Imagen as imghabitacion from Reserva inner join Cliente on Cliente.Id_cliente=Reserva.Id_cliente
inner join  Habitacion on Habitacion.Id_habitacion=Reserva.Id_habitacion;

END $$
DELIMITER ;

call  get_Reserva_consulta() 
10.
DROP PROCEDURE IF EXISTS get_Reserva_consulta_buscar; 
DELIMITER $$
CREATE PROCEDURE  get_Reserva_consulta_buscar(busc varchar(50), item int)
BEGIN
if item=0 then
select *,concat(Cliente.Primer_nombre,' ',Cliente.Segundo_nombre,' ',Cliente.Primer_apellido,' ',Cliente.Segundo_apellido) as nombre_cliente,
Habitacion.Nombre as nombre_habitacion, Cliente.Imagen as imgcliente, Habitacion.Imagen as imghabitacion from Reserva inner join Cliente on Cliente.Id_cliente=Reserva.Id_cliente
inner join  Habitacion  on Habitacion.Id_habitacion=Reserva.Id_habitacion having nombre_cliente like concat('%',busc,'%');

elseif item=1 then

select *,concat(Cliente.Primer_nombre,' ',Cliente.Segundo_nombre,' ',Cliente.Primer_apellido,' ',Cliente.Segundo_apellido) as nombre_cliente,
Habitacion.Nombre as nombre_habitacion, Cliente.Imagen as imgcliente, Habitacion.Imagen as imghabitacion from Reserva inner join Cliente on Cliente.Id_cliente=Reserva.Id_cliente
inner join  Habitacion on Habitacion.Id_habitacion=Reserva.Id_habitacion having nombre_habitacion like concat('%',busc,'%');

else
select *,concat(Cliente.Primer_nombre,' ',Cliente.Segundo_nombre,' ',Cliente.Primer_apellido,' ',Cliente.Segundo_apellido) as nombre_cliente,
Habitacion.Nombre as nombre_habitacion, Cliente.Imagen as imgcliente, Habitacion.Imagen as imghabitacion from Reserva inner join Cliente on Cliente.Id_cliente=Reserva.Id_cliente
inner join  Habitacion on Habitacion.Id_habitacion=Reserva.Id_habitacion having Reserva.Estado like concat('%',busc,'%');

end if;
END $$
DELIMITER ;


call get_Reserva_consulta_buscar('karl',0)

11.
DROP PROCEDURE IF EXISTS get_Reserva_consulta_id; 
DELIMITER $$
CREATE PROCEDURE  get_Reserva_consulta_id(id int)
BEGIN
select *,concat(Cliente.Primer_nombre,' ',Cliente.Segundo_nombre,' ',Cliente.Primer_apellido,' ',Cliente.Segundo_apellido) as nombre_cliente,
Habitacion.Nombre as nombre_habitacion, Cliente.Imagen as imgcliente, Habitacion.Imagen as imghabitacion from Reserva inner join Cliente on Cliente.Id_cliente=Reserva.Id_cliente
inner join  Habitacion on Habitacion.Id_habitacion=Reserva.Id_habitacion where Cliente.Id_cliente=id;

END $$
DELIMITER ;

call  get_Reserva_consulta_id(1) 
12.
DROP PROCEDURE IF EXISTS get_Reserva_consulta_buscar_id; 
DELIMITER $$
CREATE PROCEDURE  get_Reserva_consulta_buscar_id(busc varchar(50), item int, id int)
BEGIN
if item=0 then
select *,concat(Cliente.Primer_nombre,' ',Cliente.Segundo_nombre,' ',Cliente.Primer_apellido,' ',Cliente.Segundo_apellido) as nombre_cliente,
Habitacion.Nombre as nombre_habitacion, Cliente.Imagen as imgcliente, Habitacion.Imagen as imghabitacion from Reserva inner join Cliente on Cliente.Id_cliente=Reserva.Id_cliente
inner join  Habitacion on Habitacion.Id_habitacion=Reserva.Id_habitacion  where Cliente.Id_cliente=id having nombre_habitacion like concat('%',busc,'%');

elseif item=1 then
select *,concat(Cliente.Primer_nombre,' ',Cliente.Segundo_nombre,' ',Cliente.Primer_apellido,' ',Cliente.Segundo_apellido) as nombre_cliente,
Habitacion.Nombre as nombre_habitacion, Cliente.Imagen as imgcliente, Habitacion.Imagen as imghabitacion from Reserva inner join Cliente on Cliente.Id_cliente=Reserva.Id_cliente
inner join  Habitacion on Habitacion.Id_habitacion=Reserva.Id_habitacion  where Cliente.Id_cliente=id having Reserva.Estado like concat('%',busc,'%');


end if;
END $$
DELIMITER ;

call get_Reserva_consulta_buscar_id('karl',0,1)

13.
DROP PROCEDURE IF EXISTS getHabitaciones_disponibles_e; 
DELIMITER $$
CREATE PROCEDURE   getHabitaciones_disponibles_e(DFecha_inicio date,DFecha_Final  date)
BEGIN
select * from Habitacion where Eliminado=0 and get_Verificar_Reserva(Id_habitacion,DFecha_inicio,DFecha_Final)=0 and Estado=1  ;

END $$
DELIMITER ;

select * from Habitacion


14.
DROP PROCEDURE IF EXISTS getHabitaciones_disponibles_buscar_e; 
DELIMITER $$
CREATE PROCEDURE   getHabitaciones_disponibles_buscar_e(DFecha_inicio date,DFecha_Final  date, filtro varchar(30),buscar varchar(50))
BEGIN
if filtro='Nombre' then
select * from Habitacion where Eliminado=0 and get_Verificar_Reserva(Id_habitacion,DFecha_inicio,DFecha_Final)=0 and Nombre like concat('%',buscar,'%') and Estado=1;

end if;
if filtro='Tipo' then
select * from Habitacion where Eliminado=0 and get_Verificar_Reserva(Id_habitacion,DFecha_inicio,DFecha_Final)=0 and Tipo like concat('%',buscar,'%') and Estado=1;

end if;
if filtro='Tarifa <=' then
select * from Habitacion where Eliminado=0 and get_Verificar_Reserva(Id_habitacion,DFecha_inicio,DFecha_Final)=0 and tarifa <= CAST(buscar AS DECIMAL(65,30))  and Estado=1 ;

end if;

END $$
DELIMITER ;

15.
DROP PROCEDURE IF EXISTS setEstancia; 
DELIMITER $$
CREATE PROCEDURE   setEstancia(DId_cliente int,DId_habitacion int,DFecha_inicio date,DFecha_final date,DCosto_total double,DDescripcion varchar(100), DId_reserva int)
BEGIN
insert into Estancia(Id_cliente,Id_habitacion,Fecha_inicio,Fecha_final,Costo_total,Descripcion,Estado,Id_reserva)
values(DId_cliente,DId_habitacion,DFecha_inicio,DFecha_final,DCosto_total,DDescripcion,'Activo', DId_reserva);
Select "Se ha contratado una estancia" as mensaje;
END $$
DELIMITER ;


call setEstancia(1,1,'2018-06-28','2018-06-28',121,'todo', 2);


16.
DROP PROCEDURE IF EXISTS setEstancianull; 
DELIMITER $$
CREATE PROCEDURE   setEstancianull(DId_cliente int,DId_habitacion int,DFecha_inicio date,DFecha_final date,DCosto_total double,DDescripcion varchar(100))
BEGIN
insert into Estancia(Id_cliente,Id_habitacion,Fecha_inicio,Fecha_final,Costo_total,Descripcion,Estado,Id_reserva)
values(DId_cliente,DId_habitacion,DFecha_inicio,DFecha_final,DCosto_total,DDescripcion,'Activo', null);
Select "Se ha contratado una estancia" as mensaje;
END $$
DELIMITER ;


17.

DELIMITER //

CREATE FUNcTION get_Estado(id int) RETURNS int
BEGIN

    RETURN  (select count(*) from Estancia where estado='Activo' and Id_habitacion=id);
END ;//



DROP PROCEDURE IF EXISTS Audit_Habitacione; 
DELIMITER $$
CREATE PROCEDURE   Audit_Habitacione()
BEGIN
SET SQL_SAFE_UPDATES = 0;
update Reserva set Estado='Espera' where Estado is null;
update Reserva set Estado='Perdido' where  Estado='Espera' and Fecha_inicio<now();
update Estancia set estado='Inactivo' where Estado='Activo' and ADDDATE(Fecha_final,Interval 1 day)<now();
update Habitacion set Estado=1 where get_Estado(Id_habitacion)=0 and Estado=2;
update Habitacion set Estado=2 where get_Estado(Id_habitacion)>0 and Estado=1;
END $$
DELIMITER ;




18.
DROP PROCEDURE IF EXISTS get_Estancia_consulta; 
DELIMITER $$
CREATE PROCEDURE  get_Estancia_consulta()
BEGIN
select *,concat(Cliente.Primer_nombre,' ',Cliente.Segundo_nombre,' ',Cliente.Primer_apellido,' ',Cliente.Segundo_apellido) as nombre_cliente,
Habitacion.Nombre as nombre_habitacion, Cliente.Imagen as imgcliente, Habitacion.Imagen as imghabitacion from Estancia inner join Cliente on Cliente.Id_cliente=Estancia.Id_cliente
inner join  Habitacion on Habitacion.Id_habitacion=Estancia.Id_habitacion where Cliente.Eliminado=0 and Estancia.Estado='Activo';

END $$
DELIMITER ;

call  get_Estancia_consulta() 



19.
DROP PROCEDURE IF EXISTS get_Estancia_consulta_buscar; 
DELIMITER $$
CREATE PROCEDURE  get_Estancia_consulta_buscar(busc varchar(50), item int)
BEGIN
if item=0 then
select *,concat(Cliente.Primer_nombre,' ',Cliente.Segundo_nombre,' ',Cliente.Primer_apellido,' ',Cliente.Segundo_apellido) as nombre_cliente,
Habitacion.Nombre as nombre_habitacion, Cliente.Imagen as imgcliente, Habitacion.Imagen as imghabitacion from Estancia inner join Cliente on Cliente.Id_cliente=Estancia.Id_cliente
inner join  Habitacion  on Habitacion.Id_habitacion=Estancia.Id_habitacion  where Cliente.Eliminado=0 and Estancia.Estado='Activo' having nombre_cliente like concat('%',busc,'%') ;

elseif item=1 then

select *,concat(Cliente.Primer_nombre,' ',Cliente.Segundo_nombre,' ',Cliente.Primer_apellido,' ',Cliente.Segundo_apellido) as nombre_cliente,
Habitacion.Nombre as nombre_habitacion, Cliente.Imagen as imgcliente, Habitacion.Imagen as imghabitacion from Estancia inner join Cliente on Cliente.Id_cliente=Estancia.Id_cliente
inner join  Habitacion on Habitacion.Id_habitacion=Estancia.Id_habitacion  where Cliente.Eliminado=0 and Estancia.Estado='Activo' having nombre_habitacion like concat('%',busc,'%');

else
select *,concat(Cliente.Primer_nombre,' ',Cliente.Segundo_nombre,' ',Cliente.Primer_apellido,' ',Cliente.Segundo_apellido) as nombre_cliente,
Habitacion.Nombre as nombre_habitacion, Cliente.Imagen as imgcliente, Habitacion.Imagen as imghabitacion from Estancia inner join Cliente on Cliente.Id_cliente=Estancia.Id_cliente
inner join  Habitacion on Habitacion.Id_habitacion=Estancia.Id_habitacion  where Cliente.Eliminado=0 and Estancia.Estado='Activo' having Estancia.Estado like concat('%',busc,'%');

end if;
END $$
DELIMITER ;


call get_Estancia_consulta_buscar('karl',0)

20.
DROP PROCEDURE IF EXISTS get_Estancia_consulta_id; 
DELIMITER $$
CREATE PROCEDURE  get_Estancia_consulta_id(id int)
BEGIN
select *,concat(Cliente.Primer_nombre,' ',Cliente.Segundo_nombre,' ',Cliente.Primer_apellido,' ',Cliente.Segundo_apellido) as nombre_cliente,
Habitacion.Nombre as nombre_habitacion, Cliente.Imagen as imgcliente, Habitacion.Imagen as imghabitacion from Estancia inner join Cliente on Cliente.Id_cliente=Estancia.Id_cliente
inner join  Habitacion on Habitacion.Id_habitacion=Estancia.Id_habitacion where Cliente.Id_cliente=id  and Cliente.Eliminado=0 and Estancia.Estado='Activo';

END $$
DELIMITER ;

call  get_Estancia_consulta_id(1) 
21.
DROP PROCEDURE IF EXISTS get_Estancia_consulta_buscar_id; 
DELIMITER $$
CREATE PROCEDURE  get_Estancia_consulta_buscar_id(busc varchar(50), item int, id int)
BEGIN
if item=0 then
select *,concat(Cliente.Primer_nombre,' ',Cliente.Segundo_nombre,' ',Cliente.Primer_apellido,' ',Cliente.Segundo_apellido) as nombre_cliente,
Habitacion.Nombre as nombre_habitacion, Cliente.Imagen as imgcliente, Habitacion.Imagen as imghabitacion from Estancia inner join Cliente on Cliente.Id_cliente=Estancia.Id_cliente
inner join  Habitacion on Habitacion.Id_habitacion=Estancia.Id_habitacion  where Cliente.Id_cliente=id  and Cliente.Eliminado=0 and Estancia.Estado='Activo' having nombre_habitacion like concat('%',busc,'%');

elseif item=1 then
select *,concat(Cliente.Primer_nombre,' ',Cliente.Segundo_nombre,' ',Cliente.Primer_apellido,' ',Cliente.Segundo_apellido) as nombre_cliente,
Habitacion.Nombre as nombre_habitacion, Cliente.Imagen as imgcliente, Habitacion.Imagen as imghabitacion from Estancia inner join Cliente on Cliente.Id_cliente=Estancia.Id_cliente
inner join  Habitacion on Habitacion.Id_habitacion=Estancia.Id_habitacion  where Cliente.Id_cliente=id and Cliente.Eliminado=0 and Estancia.Estado='Activo' having Estancia.Estado like concat('%',busc,'%');


end if;
END $$
DELIMITER ;

call get_Estancia_consulta_buscar_id('karl',0,1)

22.
DROP PROCEDURE IF EXISTS get_Servicio_Cuarto; 
DELIMITER $$
CREATE PROCEDURE  get_Servicio_Cuarto( id int)
BEGIN
select concat('El servicio al cuarto ',Id_servicio_cuarto,' hecho el ',Fecha_hora,' \nregistro un importe de $',Costo_total) as mensaje,Costo_total  from servicio_al_cuarto 
where servicio_al_cuarto.Id_estancia =id;
END $$
DELIMITER ;

 call get_Servicio_Cuarto(1);
