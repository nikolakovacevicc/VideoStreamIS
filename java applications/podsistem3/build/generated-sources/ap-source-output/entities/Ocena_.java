package entities;

import entities.Korisnik;
import entities.Videosnimak;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-09T18:00:05")
@StaticMetamodel(Ocena.class)
public class Ocena_ { 

    public static volatile SingularAttribute<Ocena, Videosnimak> idvid;
    public static volatile SingularAttribute<Ocena, Integer> idocena;
    public static volatile SingularAttribute<Ocena, Korisnik> idkor;
    public static volatile SingularAttribute<Ocena, Date> datumIVreme;
    public static volatile SingularAttribute<Ocena, Integer> ocena;

}