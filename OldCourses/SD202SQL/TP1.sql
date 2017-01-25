select distinct type from buveurs;#1

select * from vins
order by cru ASC,mill DESC;#2

select * from cat;
sqlplus bda64/bda@//oratp.enst.fr:1521/bda.enst.fr


select distinct p.region,V.CRU from p,r,v
where p.np=r.np and v.nv=r.nv and (V.CRU='Pommard' OR V.CRU='Brouilly')
ORDER BY v.CRU asc,P.REGION asc;#3

select distinct p.region from p,r,v
where p.np=r.np and v.nv=r.nv and v.cru='Pommard'
intersect
select distinct p.region from p,r,v
where p.np=r.np and v.nv=r.nv and v.cru='Brouilly';#4_1

select distinct p1.region from p p1,p p2,r r1,r r2,v v1,v v3
where p1.np=r1.np and p2.np=r2.np and p1.region=p2.region and v3.nv=r2.nv and v3.cru='Brouilly'and v1.nv=r1.nv and v1.cru='Pommard'
ORDER BY p1.REGION asc;#4_2

select v.CRU,v.MILL,sum(a.QTE) from v,a
where v.nv=a.nv
group by v.cru, v.mill;#5

select v.nv from v,p,r
where v.nv=r.nv and p.np=r.np
group by v.nv
having count(p.np)>3;#6

select p.nom,p.prenom from p,r
where p.np not in r.np;#7SEL


CREATE TABLE RBB AS SELECT * FROM BONS_BUVEURS;

UPDATE BUVEURS
  SET TYPE='gros'
  WHERE (NB) IN (SELECT NB
  FROM ACHATS
  GROUP BY NB
  HAVING SUM(QTE)>100);

SELECT * FROM B
WHERE ((B.NB) IN (SELECT NB
  FROM ACHATS
  GROUP BY NB
  HAVING SUM(QTE)>100));

SELECT * FROM BONS_BUVEURS
WHERE (NB) IN (SELECT NB
  FROM ACHATS
  GROUP BY NB
  HAVING SUM(QTE)>100);

DROP TABLE RBB;

SELECT RBB.NB, RBB.TYPE, BONS_BUVEURS.TYPE
FROM RBB, BONS_BUVEURS
WHERE (RBB.NB=BONS_BUVEURS.NB) AND ((RBB.NB) IN (SELECT NB
  FROM ACHATS
  GROUP BY NB
  HAVING SUM(QTE)>100));

SELECT DISTINCT RBB.NB, RBB.TYPE, BONS_BUVEURS.TYPE
FROM RBB, BONS_BUVEURS
WHERE (RBB.TYPE!=BONS_BUVEURS.TYPE);

SELECT NB, TYPE FROM BUVEURS
WHERE ((BUVEURS.NB) IN (SELECT NB
  FROM ACHATS
  GROUP BY NB
  HAVING SUM(QTE)>100));

create view bons_buveurs
as select * from buveurs
where type = 'gros' or type = 'moyen';

create table rbb as select * from bons_buveurs;

insert into bons_buveurs (nb, nom, prenom,type)
values (1+(select distinct max(nb) from buveurs),'guo','lieqiang','petit');

select distinct max(nb) from buveurs;

select * from bons_buveurs
where prenom= 'lieqiang';

create view bons_buveurs as select * from buveurs
where type in ('moyen', 'gros')
with check option;

drop view buveurs_achats; buveurs_achats2;

create view buveurs_asec as
              select * from buveurs
              where nb not in (select nb from Achats) ;

create view buveurs_Achats as
             select * from buveurs
             where  nb in (select nb from Achats)
             with check option;

create view buveurs_Achats2 as
              select * from buveurs
              where nb not in (select nb from buveurs_asec)
              with check option;

create view q83pl(lieu, cru, qte_bue) as
              select Achats.lieu, vins.cru, sum(Achats.qte)
              from Achats, vins
              where (Achats.nv=vins.nv)
                and   (Achats.dates > '31-DEC-82')
                and   (Achats.dates < '01-JAN-84')
              group by Achats.lieu, vins.cru;

insert into buveurs_achats(nb, nom, prenom,type)
values (1+(select distinct max(nb) from buveurs),'guo','lieqiang','petit');

insert into buveurs_achats2(nb, nom, prenom,type)
values (1+(select distinct max(nb) from buveurs),'guo','lieqiang','petit');

insert into q83pl(lieu, cru, qte_bue)
values ('PARIS','Cotes du Jura', 300);

select nb, nom
       from b
       where nb not in ( select nb
                         from b, v
                         where mill = '1980'
                         and   (nb, nv) not in ( select nb, nv from a ) ) ;

select * from  q83pl
               where qte_bue>100;

create table t_achats as select * from achats;

DECLARE
    x  NUMBER := 1;
BEGIN
    WHILE x < 100 LOOP
          DELETE FROM t_achats WHERE nv=x;
	  x := x + 2;
    END LOOP;
END;
/   --this '/' is very important, this is the end of PL/SQL code

SELECT distinct t_achats.nv,achats.nv, achats.nb
FROM achats, t_achats
WHERE (achats.qte = t_achats.qte)
and (achats.nv != t_achats.nv)
and (achats.nb = t_achats.nb)
and (achats.dates = t_achats.dates)
and (achats.lieu = t_achats.lieu);

select count(nv) from t_achats;

rollback; -- rollback to the last state.

BEGIN
    FOR i IN 1..100 LOOP
	IF MOD(i,2) = 0 THEN     -- i est pair
	    UPDATE t_achats SET QTE=QTE*2 WHERE nv=i;
	ELSE
	    UPDATE t_achats SET QTE=QTE/2 WHERE nv=i;
	END IF;
    END LOOP;
END;
/

SELECT distinct t_achats.qte,achats.qte, achats.nv
FROM achats, t_achats
WHERE (achats.qte != t_achats.qte)
and (achats.nv = t_achats.nv)
and (achats.nb = t_achats.nb)
and (achats.dates = t_achats.dates)
and (achats.lieu = t_achats.lieu);

CREATE TABLE TEMP (Vin NUMBER(3), description VARCHAR2(30),
	           commentaire VARCHAR2(30));


DECLARE
   cursor V_CUR is select * from vins
              where nv in (select nv from recoltes, producteurs
                           where region='Alsace' and producteurs.np=recoltes.np)
              order by nv;
   V_LIGNE VINS%ROWTYPE;
BEGIN
   open V_CUR;
   loop
       fetch V_CUR into V_Ligne;
       exit when V_CUR%NOTFOUND;
       IF V_Ligne.mill in (1976, 1978, 1983) THEN
	  INSERT INTO TEMP
		VALUES (V_Ligne.nv, V_Ligne.cru || '(' || to_char(V_Ligne.mill) || ')', 'Récolte exeptionelle !');
       ELSE
          INSERT INTO TEMP
		VALUES (V_Ligne.nv, V_Ligne.cru || '(' || to_char(V_Ligne.mill) || ')', 'RAS !');
       END IF;
   end loop;
   close V_CUR;
END;
/

select * from temp
where commentaire='Récolte exeptionelle !';

DECLARE
   cursor V_CUR is select * from vins
              where nv in (select nv from recoltes, producteurs
                           where region='Alsace' and producteurs.np=recoltes.np)
              order by nv;
BEGIN
   for V_Ligne in V_CUR loop
       IF V_Ligne.mill in (1976, 1978, 1983) THEN
	  INSERT INTO TEMP
		VALUES (V_Ligne.nv, V_Ligne.cru || '(' || to_char(V_Ligne.mill) || ')', 'Récolte exeptionelle !');
       ELSE
          INSERT INTO TEMP
		VALUES (V_Ligne.nv, V_Ligne.cru || '(' || to_char(V_Ligne.mill) || ')', 'RAS !');
       END IF;
   end loop;
END;
/

CREATE OR REPLACE PROCEDURE choix_experts AS
--DECLARE
   cursor V_CUR is select * from vins
              where nv in (select nv from recoltes, producteurs
                           where region='Alsace' and producteurs.np=recoltes.np)
              order by nv;
BEGIN
   for V_Ligne in V_CUR loop
       IF V_Ligne.mill in (1976, 1978, 1983) THEN
	  INSERT INTO TEMP
		VALUES (V_Ligne.nv, V_Ligne.cru || '(' || to_char(V_Ligne.mill) || ')', 'Récolte exeptionelle !');
       ELSE
          INSERT INTO TEMP
		VALUES (V_Ligne.nv, V_Ligne.cru || '(' || to_char(V_Ligne.mill) || ')', 'RAS !');
       END IF;
   end loop;
END;
/


CREATE OR REPLACE FUNCTION recherche_vins (r PRODUCTEURS.REGION%TYPE, annee VINS.MILL%TYPE) RETURN number AS
--DECLARE
--   r PRODUCTEURS.REGION%TYPE;
--   annee VINS.MILL%TYPE;
   cursor V_CUR is select * from vins
              where nv in (select nv from recoltes, producteurs
                           where region=r and producteurs.np=recoltes.np)
              order by nv;
   cpt number:=0;

BEGIN
   for V_Ligne in V_CUR loop
       IF V_Ligne.mill = annee THEN
	  INSERT INTO TEMP
		VALUES (V_Ligne.nv, V_Ligne.cru || '(' || to_char(V_Ligne.mill) || ')', 'Récolte exeptionelle !');
          cpt:=cpt+1;
       END IF;
   end loop;
   RETURN cpt;
END;
/

/* first create or replace a function with parameters
then create the entity of the function
  we need to begin .... end for the whole architecture
  between them, we use for / while / if else for some controlling things
 it's similar to python, but still has something special,
 so be careful when coding your programs */

create or replace PROCEDURE VSTAT(t_nv vins.nv%TYPE)  as
 cursor c_region is select distinct p.region, count(p.np) as n_p
    from producteurs p, recoltes r
    where p.np = r.np and r.nv = t_nv
    group by p.region
    order by count(p.np) desc;
  cursor c_ville is select distinct a.lieu, extract(year from a.dates) as years, sum(a.qte) as qte
      from achats a
      where a.nv= t_nv
      group by extract(year from a.dates), a.lieu
      order by extract(year from a.dates), sum(a.qte) desc;
  cursor c_nbuv is select distinct a.lieu, sum(a.qte) as qte
     from achats a
     where a.nv= t_nv
     group by a.lieu;
  nbuveurs number := 0;
  vlieu achats.lieu%type :='-1';
 begin
    dbms_output.put_line('******************************');
    dbms_output.put_line('Regions de production du vin numero ' || t_nv);

    for v_region in c_region loop
  	   dbms_output.put_line('--    '||to_char(v_region.region)|| '( ' ||to_char(v_region.n_p) || ' producteurs )');
	     dbms_output.new_line;
    end loop;
    dbms_output.put_line('******************************');

    dbms_output.put_line('Vente du vin numero ' || t_nv);
    for v_ville in c_ville loop
      if vlieu ='-1' then
        vlieu := v_ville.lieu;
        for v_nbuv in c_nbuv loop
          if v_nbuv.lieu = vlieu then
            nbuveurs:= v_nbuv.qte;
            exit;
          end if;
        end loop;
        dbms_output.put_line(vlieu||'( '|| to_char(nbuveurs) || ' )');
      else
        if vlieu != v_ville.lieu then
          vlieu:= v_ville.lieu;
          dbms_output.put_line('------------------------------');
          dbms_output.put_line(vlieu||'( ' || to_char(nbuveurs) || ' )');
        end if;
      end if;
      dbms_output.put_line('--   '|| v_ville.years||': '|| v_ville.qte);
      dbms_output.new_line;
    end loop;
    dbms_output.put_line('******************************');
end;
/

SET SERVEROUTPUT ON
EXECUTE dbms_output.enable(1000000);
EXECUTE VSTAT(41);

insert into a values(45, 12, sysdate, 'Paris', 20);
insert into a values(45, 12, sysdate, 'Paris', 33);
insert into a values(45, 12, to_date('13/02/2003', 'dd/mm/yyyy'), 'Paris', 10);
insert into a values(45, 12, to_date('13/02/2003', 'dd/mm/yyyy'), 'Paris', 11);
insert into a values(45, 12, sysdate, 'Lille', 33);
insert into a values(45, 12, sysdate, 'Lille', 13);
insert into a values(45, 12, to_date('13/02/2001', 'dd/mm/yyyy'), 'Lille', 15);
insert into a values(45, 12, to_date('13/02/2001', 'dd/mm/yyyy'), 'Lille', 16);
