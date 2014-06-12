/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     22/10/2013 04:31:25 p.m.                     */
/*==============================================================*/


/*==============================================================*/
/* Table: asignacion_conductor                                  */
/*==============================================================*/
create table asignacion_conductor (
   id_asignacion_conductor serial               not null,
   id_vehiculo          int4                 not null,
   cedula_empleado      varchar(10)          not null,
   fecha_inicial        date                 not null,
   fecha_final          date                 null,
   motivo               varchar(255)         not null,
   status               char(1)              not null,
   constraint pk_asignacion_conductor primary key (id_vehiculo, cedula_empleado, id_asignacion_conductor)
);

/*==============================================================*/
/* Table: asignacion_ruta_vehiculo                              */
/*==============================================================*/
create table asignacion_ruta_vehiculo (
   id_ruta              int4                 not null,
   id_vehiculo          int4                 not null
      constraint ckc_id_vehiculo_asignaci check (id_vehiculo between 1 and 9999999999),
   fecha_inicial        date                 not null,
   fecha_final          date                 null,
   motivo               varchar(255)         not null,
   status               char(1)              not null,
   constraint pk_asignacion_ruta_vehiculo primary key (id_ruta, id_vehiculo)
);

/*==============================================================*/
/* Table: cargo                                                 */
/*==============================================================*/
create table cargo (
   id_cargo             serial               not null,
   nombre               varchar(64)          not null,
   descripcion          varchar(255)         null,
   id_empresa           int4                 not null,
   status               char(1)              not null,
   constraint pk_cargo primary key (id_cargo)
);

/*==============================================================*/
/* Table: causa_operacion                                       */
/*==============================================================*/
create table causa_operacion (
   id_causa_operacion   serial               not null,
   tipo_operacion_neumatico char(1)              not null,
   nombre               varchar(60)          not null,
   descripcion          varchar(255)         null,
   status               char(1)              not null,
   constraint pk_causa_operacion primary key (id_causa_operacion)
);

/*==============================================================*/
/* Table: diseno                                                */
/*==============================================================*/
create table diseno (
   id_diseno            serial               not null,
   id_marca_neumatico   int4                 not null,
   nombre               varchar(60)          not null,
   descripcion          varchar(255)         null,
   imagen               bytea                not null,
   status               char(1)              not null,
   constraint pk_diseno primary key (id_diseno)
);

/*==============================================================*/
/* Table: diseno_medida                                         */
/*==============================================================*/
create table diseno_medida (
   id_medida            int4                 not null,
   id_diseno            int4                 not null,
   status               char(1)              not null,
   constraint pk_diseno_medida primary key (id_medida, id_diseno)
);

/*==============================================================*/
/* Table: empleado                                              */
/*==============================================================*/
create table empleado (
   cedula_empleado      varchar(10)          not null,
   nombre               varchar(60)          not null,
   apellido             varchar(60)          not null,
   direccion            varchar(150)         not null,
   telefono             varchar(15)          null,
   celular              varchar(15)          not null,
   correo               varchar(30)          null,
   id_empresa           int4                 not null,
   id_cargo             int4                 not null,
   status               char(1)              not null,
   constraint pk_empleado primary key (cedula_empleado)
);

/*==============================================================*/
/* Table: empresa                                               */
/*==============================================================*/
create table empresa (
   id_empresa           serial               not null,
   rif                  varchar(20)          not null,
   nombre               varchar(60)          not null,
   direccion            varchar(255)         not null,
   telefono             varchar(15)          not null,
   celular              varchar(15)          null,
   fax                  varchar(15)          null,
   correo               varchar(30)          null,
   remanente_retiro     float8               not null,
   status               char(1)              not null,
   constraint pk_empresa primary key (id_empresa)
);

/*==============================================================*/
/* Table: esquema_eje                                           */
/*==============================================================*/
create table esquema_eje (
   id_esquema_eje       serial               not null,
   id_empresa           int4                 not null,
   nombre_esquema       varchar(60)          not null,
   descripcion          varchar(255)         null,
   neumatico1           char(1)              null,
   neumatico2           char(1)              null,
   neumatico3           char(1)              null,
   neumatico4           char(1)              null,
   neumatico5           char(1)              null,
   neumatico6           char(1)              null,
   neumatico7           char(1)              null,
   neumatico8           char(1)              null,
   neumatico9           char(1)              null,
   neumatico10          char(1)              null,
   neumatico11          char(1)              null,
   neumatico12          char(1)              null,
   neumatico13          char(1)              null,
   neumatico14          char(1)              null,
   neumatico15          char(1)              null,
   neumatico16          char(1)              null,
   neumatico17          char(1)              null,
   neumatico18          char(1)              null,
   neumatico19          char(1)              null,
   neumatico20          char(1)              null,
   eje1                 char(1)              null,
   eje2                 char(1)              null,
   eje3                 char(1)              null,
   eje4                 char(1)              null,
   eje5                 char(1)              null,
   tipo                 varchar(60)          not null,
   status               char(1)              not null,
   constraint pk_esquema_eje primary key (id_esquema_eje)
);

/*==============================================================*/
/* Table: factura                                               */
/*==============================================================*/
create table factura (
   id_proveedor         int4                 not null,
   nro_factura          varchar(60)          not null,
   id_empresa           int4                 not null
      constraint ckc_id_empresa_factura check (id_empresa between 1 and 99999999999),
   costo                float8               not null,
   fecha                date                 not null,
   status               char(1)              not null,
   constraint pk_factura primary key (id_proveedor, nro_factura)
);

/*==============================================================*/
/* Table: marca_neumatico                                       */
/*==============================================================*/
create table marca_neumatico (
   id_marca_neumatico   serial               not null,
   id_empresa           int4                 not null,
   nombre               varchar(60)          not null,
   descripcion          varchar(255)         null,
   status               char(1)              not null,
   constraint pk_marca_neumatico primary key (id_marca_neumatico)
);

/*==============================================================*/
/* Table: marca_vehiculo                                        */
/*==============================================================*/
create table marca_vehiculo (
   id_marca_vehiculo    serial               not null,
   id_empresa           int4                 not null,
   nombre               varchar(60)          not null,
   descripcion          varchar(255)         null,
   status               char(1)              not null,
   constraint pk_marca_vehiculo primary key (id_marca_vehiculo)
);

/*==============================================================*/
/* Table: medida                                                */
/*==============================================================*/
create table medida (
   id_medida            serial               not null,
   id_empresa           int4                 not null
      constraint ckc_id_empresa_medida check (id_empresa between 1 and 99999999999),
   nombre               varchar(60)          not null,
   descripcion          varchar(255)         null,
   presion_recomendada  float8               not null,
   status               char(1)              not null,
   constraint pk_medida primary key (id_medida)
);

/*==============================================================*/
/* Table: modelo_vehiculo                                       */
/*==============================================================*/
create table modelo_vehiculo (
   id_modelo_vehiculo   serial               not null,
   id_marca_vehiculo    int4                 not null,
   id_tipo_vehiculo     int4                 not null,
   nombre               varchar(60)          not null,
   descripcion          varchar(255)         null,
   status               char(1)              not null,
   constraint pk_modelo_vehiculo primary key (id_modelo_vehiculo)
);

/*==============================================================*/
/* Table: movimiento                                            */
/*==============================================================*/
create table movimiento (
   id_vehiculo          int4                 not null,
   id_neumatico         int4                 not null,
   tipo_movimiento      char(1)              not null,
   fecha                date                 not null,
   recorrido_acumulado  float8               not null,
   remanente_superior_a float8               not null,
   remanente_superior_b float8               not null,
   remanente_superior_c float8               not null,
   remanente_izquierdo_a float8               null,
   remanente_izquierdo_b float8               null,
   remanente_izquierdo_c float8               null,
   remanente_izquierdo_d float8               null,
   remanente_derecho_a  float8               null,
   remanente_derecho_b  float8               null,
   remanente_derecho_c  float8               null,
   remanente_derecho_d  float8               null,
   remanente_diagonal_a float8               null,
   remanente_diagonal_b float8               null,
   remanente_diagonal_c float8               null,
   remanente_diagonal_d float8               null,
   remanente_superior_d float8               not null,
   remanente_movimiento float8               not null,
   posicion_inicial     int4                 null,
   posicion_final       int4                 null,
   kilometraje          float8               not null,
   presion              float8               null,
   id_tipo_desgaste     int4                 null,
   observaciones        varchar(150)         null,
   status               char(1)              not null,
   constraint pk_movimiento primary key (id_vehiculo, id_neumatico, tipo_movimiento, fecha)
);

/*==============================================================*/
/* Table: neumatico                                             */
/*==============================================================*/
create table neumatico (
   id_neumatico         serial               not null,
   cod_interno          varchar(255)         not null,
   unidireccional       char(1)              not null,
   condicion            char(1)              not null,
   tipo_neumatico       char(1)              not null,
   remanente_ingreso    float8               not null,
   remanente_actual     float8               null,
   id_diseno_original   int4                 not null,
   id_diseno_actual     int4                 null,
   recorrido_acumulado  float8               null,
   presion_actual       float8               not null,
   id_proveedor         int4                 not null,
   nro_factura          varchar(60)          not null,
   estado               char(1)              not null,
   id_empresa           int4                 not null,
   id_medida            int4                 not null,
   id_diseno            int4                 not null,
   status               char(1)              not null,
   constraint pk_neumatico primary key (id_neumatico)
);

/*==============================================================*/
/* Table: operacion_neumatico                                   */
/*==============================================================*/
create table operacion_neumatico (
   id_neumatico         serial               not null,
   fecha_operacion      date                 not null,
   id_causa_operacion   int4                 null,
   observacion          varchar(255)         null,
   status               char(1)              not null,
   constraint pk_operacion_neumatico primary key (id_neumatico, fecha_operacion)
);

/*==============================================================*/
/* Table: proveedor                                             */
/*==============================================================*/
create table proveedor (
   id_proveedor         serial               not null,
   id_empresa           int4                 not null
      constraint ckc_id_empresa_proveedo check (id_empresa between 1 and 99999999999),
   nombre               varchar(60)          not null,
   direccion            varchar(255)         not null,
   rif                  varchar(15)          not null,
   telefono             varchar(15)          null,
   celular              varchar(15)          not null,
   correo               varchar(30)          null,
   status               char(1)              not null,
   constraint pk_proveedor primary key (id_proveedor)
);

/*==============================================================*/
/* Table: ruta                                                  */
/*==============================================================*/
create table ruta (
   id_ruta              serial               not null,
   nombre               varchar(60)          not null,
   descripcion          varchar(150)         null,
   id_tipo_carretera    int4                 not null,
   id_empresa           int4                 not null,
   status               char(1)              not null,
   constraint pk_ruta primary key (id_ruta)
);

/*==============================================================*/
/* Table: ruta_vehiculo                                         */
/*==============================================================*/
create table ruta_vehiculo (
   id_ruta              int4                 not null,
   id_vehiculo          int4                 not null,
   ruta_actual          char(1)              not null,
   status               char(1)              not null,
   constraint pk_ruta_vehiculo primary key (id_ruta, id_vehiculo)
);

/*==============================================================*/
/* Table: tipo_carretera                                        */
/*==============================================================*/
create table tipo_carretera (
   id_tipo_carretera    int4                 not null,
   nombre               varchar(60)          not null,
   descripcion          varchar(150)         null,
   status               char(1)              not null,
   constraint pk_tipo_carretera primary key (id_tipo_carretera)
);

/*==============================================================*/
/* Table: tipo_desgaste                                         */
/*==============================================================*/
create table tipo_desgaste (
   id_tipo_desgaste     serial               not null,
   nombre               varchar(60)          not null,
   descripcion          varchar(255)         null,
   causa                varchar(255)         not null,
   acciones_correctivas varchar(255)         null,
   imagen               bytea                null,
   status               char(1)              not null,
   constraint pk_tipo_desgaste primary key (id_tipo_desgaste)
);

/*==============================================================*/
/* Table: tipo_vehiculo                                         */
/*==============================================================*/
create table tipo_vehiculo (
   id_tipo_vehiculo     int4                 not null,
   nombre               varchar(60)          not null,
   descripcion          varchar(150)         null,
   dentro_carretera     char(1)              not null,
   status               char(1)              not null,
   constraint pk_tipo_vehiculo primary key (id_tipo_vehiculo)
);

/*==============================================================*/
/* Table: vehiculo                                              */
/*==============================================================*/
create table vehiculo (
   id_vehiculo          serial               not null,
   placa                varchar(10)          not null,
   ano_fabricacion      varchar(5)           null,
   kilometraje          float8               not null,
   id_empresa           int4                 not null,
   id_esquema_ejes      int4                 not null,
   id_marca_vehiculo    int4                 null,
   status               char(1)              not null,
   constraint pk_vehiculo primary key (id_vehiculo)
);

/*==============================================================*/
/* Table: vehiculo_empleado                                     */
/*==============================================================*/
create table vehiculo_empleado (
   id_vehiculo          int4                 not null,
   cedula_empleado      varchar(10)          not null,
   conductor_principal  char(1)              not null,
   status               char(1)              not null,
   constraint pk_vehiculo_empleado primary key (id_vehiculo, cedula_empleado)
);

alter table asignacion_conductor
   add constraint fk_asignaci_reference_vehiculo foreign key (id_vehiculo, cedula_empleado)
      references vehiculo_empleado (id_vehiculo, cedula_empleado)
      on delete restrict on update restrict;

alter table asignacion_ruta_vehiculo
   add constraint fk_asignaci_reference_ruta_veh foreign key (id_ruta, id_vehiculo)
      references ruta_vehiculo (id_ruta, id_vehiculo)
      on delete restrict on update restrict;

alter table cargo
   add constraint fk_cargo_reference_empresa foreign key (id_empresa)
      references empresa (id_empresa)
      on delete restrict on update restrict;

alter table diseno
   add constraint fk_diseno_reference_marca_ne foreign key (id_marca_neumatico)
      references marca_neumatico (id_marca_neumatico)
      on delete restrict on update restrict;

alter table diseno_medida
   add constraint fk_diseno_m_reference_medida foreign key (id_medida)
      references medida (id_medida)
      on delete restrict on update restrict;

alter table diseno_medida
   add constraint fk_diseno_m_reference_diseno foreign key (id_diseno)
      references diseno (id_diseno)
      on delete restrict on update restrict;

alter table empleado
   add constraint fk_empleado_reference_cargo foreign key (id_cargo)
      references cargo (id_cargo)
      on delete restrict on update restrict;

alter table empleado
   add constraint fk_empleado_relations_empresa foreign key (id_empresa)
      references empresa (id_empresa)
      on delete restrict on update restrict;

alter table esquema_eje
   add constraint fk_esquema__reference_empresa foreign key (id_empresa)
      references empresa (id_empresa)
      on delete restrict on update restrict;

alter table factura
   add constraint fk_factura_reference_empresa foreign key (id_empresa)
      references empresa (id_empresa)
      on delete restrict on update restrict;

alter table factura
   add constraint fk_factura_relations_proveedo foreign key (id_proveedor)
      references proveedor (id_proveedor)
      on delete restrict on update restrict;

alter table marca_neumatico
   add constraint fk_marca_ne_reference_empresa foreign key (id_empresa)
      references empresa (id_empresa)
      on delete restrict on update restrict;

alter table marca_vehiculo
   add constraint fk_marca_ve_reference_empresa foreign key (id_empresa)
      references empresa (id_empresa)
      on delete restrict on update restrict;

alter table medida
   add constraint fk_medida_reference_empresa foreign key (id_empresa)
      references empresa (id_empresa)
      on delete restrict on update restrict;

alter table modelo_vehiculo
   add constraint fk_modelo_v_reference_marca_ve foreign key (id_marca_vehiculo)
      references marca_vehiculo (id_marca_vehiculo)
      on delete restrict on update restrict;

alter table modelo_vehiculo
   add constraint fk_modelo_v_reference_tipo_veh foreign key (id_tipo_vehiculo)
      references tipo_vehiculo (id_tipo_vehiculo)
      on delete restrict on update restrict;

alter table movimiento
   add constraint fk_movimien_relations_neumatic foreign key (id_neumatico)
      references neumatico (id_neumatico)
      on delete restrict on update restrict;

alter table movimiento
   add constraint fk_movimien_relations_tipo_des foreign key (id_tipo_desgaste)
      references tipo_desgaste (id_tipo_desgaste)
      on delete restrict on update restrict;

alter table movimiento
   add constraint fk_movimien_relations_vehiculo foreign key (id_vehiculo)
      references vehiculo (id_vehiculo)
      on delete restrict on update restrict;

alter table neumatico
   add constraint fk_neumatic_reference_factura foreign key (id_proveedor, nro_factura)
      references factura (id_proveedor, nro_factura)
      on delete restrict on update restrict;

alter table neumatico
   add constraint fk_neumatic_reference_diseno_m foreign key (id_medida, id_diseno)
      references diseno_medida (id_medida, id_diseno)
      on delete restrict on update restrict;

alter table neumatico
   add constraint fk_neumatic_relations_empresa foreign key (id_empresa)
      references empresa (id_empresa)
      on delete restrict on update restrict;

alter table operacion_neumatico
   add constraint fk_operacio_reference_neumatic foreign key (id_neumatico)
      references neumatico (id_neumatico)
      on delete restrict on update restrict;

alter table operacion_neumatico
   add constraint fk_operacio_reference_causa_op foreign key (id_causa_operacion)
      references causa_operacion (id_causa_operacion)
      on delete restrict on update restrict;

alter table proveedor
   add constraint fk_proveedo_reference_empresa foreign key (id_empresa)
      references empresa (id_empresa)
      on delete restrict on update restrict;

alter table ruta
   add constraint fk_ruta_reference_empresa foreign key (id_empresa)
      references empresa (id_empresa)
      on delete restrict on update restrict;

alter table ruta
   add constraint fk_ruta_relations_tipo_car foreign key (id_tipo_carretera)
      references tipo_carretera (id_tipo_carretera)
      on delete restrict on update restrict;

alter table ruta_vehiculo
   add constraint fk_ruta_veh_relations_vehiculo foreign key (id_vehiculo)
      references vehiculo (id_vehiculo)
      on delete restrict on update restrict;

alter table ruta_vehiculo
   add constraint fk_ruta_veh_relations_ruta foreign key (id_ruta)
      references ruta (id_ruta)
      on delete restrict on update restrict;

alter table vehiculo
   add constraint fk_vehiculo_posee_esquema_ foreign key (id_esquema_ejes)
      references esquema_eje (id_esquema_eje)
      on delete restrict on update restrict;

alter table vehiculo
   add constraint fk_vehiculo_reference_marca_ve foreign key (id_marca_vehiculo)
      references marca_vehiculo (id_marca_vehiculo)
      on delete restrict on update restrict;

alter table vehiculo
   add constraint fk_vehiculo_relations_empresa foreign key (id_empresa)
      references empresa (id_empresa)
      on delete restrict on update restrict;

alter table vehiculo_empleado
   add constraint fk_vehiculo_relations_vehiculo foreign key (id_vehiculo)
      references vehiculo (id_vehiculo)
      on delete restrict on update restrict;

alter table vehiculo_empleado
   add constraint fk_vehiculo_relations_empleado foreign key (cedula_empleado)
      references empleado (cedula_empleado)
      on delete restrict on update restrict;

