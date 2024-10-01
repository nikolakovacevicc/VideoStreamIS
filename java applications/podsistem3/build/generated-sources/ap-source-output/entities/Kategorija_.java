package entities;

import entities.Videosnimak;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-09T18:00:05")
@StaticMetamodel(Kategorija.class)
public class Kategorija_ { 

    public static volatile SingularAttribute<Kategorija, Integer> idkat;
    public static volatile SingularAttribute<Kategorija, String> naziv;
    public static volatile ListAttribute<Kategorija, Videosnimak> videosnimakList;

}