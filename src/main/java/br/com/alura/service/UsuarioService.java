package br.com.alura.service;

import br.com.alura.exception.ApplicationServiceException;
import br.com.alura.model.Usuario;
import br.com.alura.repository.UsuarioRepository;
import br.com.alura.util.message.MessageService;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.hibernate.exception.ConstraintViolationException;

import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class UsuarioService {

    private static final Logger LOG = Logger.getLogger(UsuarioService.class.getName());

    @Inject
    UsuarioRepository usuarioRepository;

    @Transactional
    public void create(Usuario data) throws ApplicationServiceException {

        Usuario usuario = new Usuario();
        usuario.setNome(data.getNome());
        usuario.setCpf(data.getCpf());
        usuario.setUsername(data.getUsername());
        usuario.setRole(Usuario.validaUsername(data.getUsername()));
        usuario.setPassword(Usuario.passwordCripto(data.getPassword()));

        try {
            usuarioRepository.persistAndFlush(usuario);
        }catch (ConstraintViolationException e){
            throw new ApplicationServiceException(e.getConstraintName());
        } catch (jakarta.validation.ConstraintViolationException cve){
            throw new ApplicationServiceException("message.parametrosnaoinformados",
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    new MessageService(cve).getErrors());
        }catch (Exception e){
            LOG.log(Level.SEVERE, "Erro na execucao do UsuarioService: incluir", e);
            throw new ApplicationServiceException("usuario.erro", new String[]{"incluir"});
        }
    }
}
