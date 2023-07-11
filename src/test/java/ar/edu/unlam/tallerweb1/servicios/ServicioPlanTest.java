package ar.edu.unlam.tallerweb1.servicios;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPlan;

import java.util.ArrayList;
import java.util.List;

public class ServicioPlanTest {

    private ServicioPlanImpl servicioPlan;
    private RepositorioPlan repositorioPlanMock;

    @Before
    public void setUp() {
        
        repositorioPlanMock = Mockito.mock(RepositorioPlan.class);

       
        servicioPlan = new ServicioPlanImpl(repositorioPlanMock);
    }

    @Test
    public void testObtenerPlanes() {
       
        List<Plan> planesSimulados = new ArrayList<>();
        planesSimulados.add(new Plan());
        planesSimulados.add(new Plan());

       
        Mockito.when(repositorioPlanMock.listaDePlanes()).thenReturn(planesSimulados);

       
        List<Plan> resultado = servicioPlan.obtenerPlanes();

        
        Assert.assertEquals(planesSimulados, resultado);
    }
}