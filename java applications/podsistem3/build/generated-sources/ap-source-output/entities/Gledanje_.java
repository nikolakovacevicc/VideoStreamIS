package entities;

import entities.Korisnik;
import entities.Videosnimak;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-09T18:00:05")
@StaticMetamodel(Gledanje.class)
public class Gledanje_ { 

    public static volatile SingularAttribute<Gledanje, Videosnimak> vid;
    public static volatile SingularAttribute<Gledanje, Integer> idgled;
    public static volatile SingularAttribute<Gledanje, Integer> sekundPoc;
    public static volatile SingularAttribute<Gledanje, Date> datumIVreme;
    public static volatile SingularAttribute<Gledanje, Integer> sekundStigao;
    public static volatile SingularAttribute<Gledanje, Korisnik> kor;

}