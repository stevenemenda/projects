desc vins
 Name					   Null?    Type
 ----------------------------------------- -------- ----------------------------
 NV					   NOT NULL NUMBER(5)
 CRU						    VARCHAR2(40)
 MILL						    NUMBER(4)
 DEGRE						    NUMBER(4,2)

 desc recoltes
  Name					   Null?    Type
  ----------------------------------------- -------- ----------------------------
  NP					   NOT NULL NUMBER(5)
  NV					   NOT NULL NUMBER(5)
  QTE						    NUMBER(5)

  desc BUVEURS
 Name					   Null?    Type
 ----------------------------------------- -------- ----------------------------
 NB					   NOT NULL NUMBER(5)
 NOM						    VARCHAR2(40)
 PRENOM 					    VARCHAR2(40)
 TYPE						    VARCHAR2(8)

 desc PRODUCTEURS
  Name					   Null?    Type
  ----------------------------------------- -------- ----------------------------
  NP					   NOT NULL NUMBER(5)
  NOM						    VARCHAR2(40)
  PRENOM 					    VARCHAR2(40)
  REGION 					    VARCHAR2(30)

desc achats
 Name					   Null?    Type
 ----------------------------------------- -------- ----------------------------
 NV					   NOT NULL NUMBER(5)
 NB					   NOT NULL NUMBER(5)
 DATES					   NOT NULL DATE
 LIEU						    VARCHAR2(30)
 QTE						    NUMBER(4)



