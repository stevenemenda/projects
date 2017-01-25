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
  	   dbms_output.put_line('--   '||to_char(v_region.region)|| '( ' ||to_char(v_region.n_p) || ' producteurs )');
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
          for v_nbuv in c_nbuv loop
            if v_nbuv.lieu = vlieu then
              nbuveurs:= v_nbuv.qte;
              exit;
            end if;
          end loop;
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
EXECUTE VSTAT(99);
