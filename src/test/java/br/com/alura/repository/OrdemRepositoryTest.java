package br.com.alura.repository;

import br.com.alura.model.Ordem;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;


@QuarkusTest
public class OrdemRepositoryTest {

    @InjectMock
    OrdemRespository ordemRespository;

    @Test
    public void testarSeListAllRetornaOrdensCorretas(){
        Ordem o1 = new Ordem();
        Ordem o2 = new Ordem();

        List<Ordem> ordemLists = new ArrayList<Ordem>();
        ordemLists.add(o1);
        ordemLists.add(o2);

        Mockito.when(ordemRespository.listAll()).thenReturn(ordemLists);

        Assertions.assertSame(o2, ordemRespository.listAll().get(1));
    }
}
