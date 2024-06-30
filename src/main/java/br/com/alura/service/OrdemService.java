package br.com.alura.service;

import br.com.alura.dto.OrdemRequestDTO;
import br.com.alura.exception.ApplicationServiceException;
import br.com.alura.model.Ordem;
import br.com.alura.model.Usuario;
import br.com.alura.repository.OrdemRespository;
import br.com.alura.repository.UsuarioRepository;
import br.com.alura.util.message.MessageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.hibernate.exception.ConstraintViolationException;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class OrdemService {
    private static final Logger LOG = Logger.getLogger(UsuarioService.class.getName());
    @Inject
    OrdemRespository ordemRespository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Transactional
    public void create(OrdemRequestDTO ordemDto) throws ApplicationServiceException {
        Usuario usuario = usuarioRepository.findById(ordemDto.userId());
        if ( usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.!");
        }
        Ordem ordem = new Ordem();
        ordem.setPreco(ordemDto.preco());
        ordem.setTipo(ordemDto.tipo());
        ordem.setData(LocalDate.now());
        ordem.setStatus("ENVIADA");
        ordem.setUserId(usuario);
        System.out.println(usuario);

        try {
            ordemRespository.persist(ordem);
        }catch (ConstraintViolationException e){
            throw new ApplicationServiceException(e.getConstraintName());
        } catch (jakarta.validation.ConstraintViolationException cve){
            throw new ApplicationServiceException("message.parametrosnaoinformados",
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    new MessageService(cve).getErrors());
        }catch (Exception e){
            System.out.println(e.getMessage());
            LOG.log(Level.SEVERE, "Erro na execucao do OrdemService: incluir", e);
            throw new ApplicationServiceException("ordem.erro", new String[]{"incluir"});
        }
    }
}
