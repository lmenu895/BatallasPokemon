package ar.edu.unlam.tallerweb1.servicios;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPlanImpl;

import java.util.ArrayList;
import java.util.List;

public class RepositorioPlanTest {

    private RepositorioPlanImpl repositorioPlan;
    private SessionFactory sessionFactoryMock;
    private Session sessionMock;
    private Criteria criteriaMock;

    @Before
    public void setUp() {
        
        sessionFactoryMock = Mockito.mock(SessionFactory.class);
        sessionMock = Mockito.mock(Session.class);

   
        criteriaMock = Mockito.mock(Criteria.class);

        
        Mockito.when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);

       
        repositorioPlan = new RepositorioPlanImpl(sessionFactoryMock);
    }

    @Test
    public void testListaDePlanes() {
       
        List<Plan> planes = new ArrayList<>();
        planes.add(new Plan());
        planes.add(new Plan());

        
        Mockito.when(sessionMock.createCriteria(Plan.class)).thenReturn(criteriaMock);
        Mockito.when(criteriaMock.list()).thenReturn(planes);

       
        List<Plan> resultado = repositorioPlan.listaDePlanes();

        
        Assert.assertEquals(planes, resultado);
    }
}