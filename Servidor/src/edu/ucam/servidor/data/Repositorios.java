package edu.ucam.servidor.data;

import edu.ucam.domain.Titulacion;
import edu.ucam.domain.Asignatura;

public class Repositorios {

    public static DataRepository<Titulacion> repoTitulos = new DataRepository<>();
    public static DataRepository<Asignatura> repoAsignaturas = new DataRepository<>();
}
