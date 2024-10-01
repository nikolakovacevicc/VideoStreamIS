package entities;

import entities.Kategorija;
import entities.Korisnik;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-09T18:00:00")
@StaticMetamodel(Videosnimak.class)
public class Videosnimak_ { 

    public static volatile SingularAttribute<Videosnimak, Integer> idvid;
    public static volatile ListAttribute<Videosnimak, Kategorija> kategorijaList;
    public static volatile SingularAttribute<Videosnimak, Integer> trajanje;
    public static volatile SingularAttribute<Videosnimak, String> naziv;
    public static volatile SingularAttribute<Videosnimak, Date> datumIVreme;
    public static volatile SingularAttribute<Videosnimak, Korisnik> vlasnik;

}