package entities;

import entities.Korisnik;
import entities.Paket;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-09T18:00:05")
@StaticMetamodel(Pretplata.class)
public class Pretplata_ { 

    public static volatile SingularAttribute<Pretplata, Date> datumIVreme;
    public static volatile SingularAttribute<Pretplata, Integer> cena;
    public static volatile SingularAttribute<Pretplata, Integer> idpret;
    public static volatile SingularAttribute<Pretplata, Paket> paket;
    public static volatile SingularAttribute<Pretplata, Korisnik> korisnik;

}