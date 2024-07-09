package br.com.alura.service;

import br.com.alura.exception.ApplicationServiceException;
import br.com.alura.model.Usuario;
import br.com.alura.repository.UsuarioRepository;
import br.com.alura.util.message.MessageBundle;
import br.com.alura.util.message.MessageService;
import br.com.alura.util.message.MessageServiceError;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public List<Usuario> listar(Page page, Sort sort) throws ApplicationServiceException {
        try {
            return usuarioRepository.findAll(sort).page(page).list();
        }catch (Exception e){
            LOG.log(Level.SEVERE,"Erro na execucao do UsuarioService: listar", e);
            throw new ApplicationServiceException("usuario.erro",
                    new String[] { "listar" }, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
    }

    public Long obterQuantidade() throws ApplicationServiceException {
        try{
            return usuarioRepository.count();
        }catch (Exception e){
            throw new ApplicationServiceException("usuario.erro", new String[] { "obterQuantidade" },
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
    }

    @Transactional
    public void alterar(Usuario data) throws ApplicationServiceException {
        try{
//            usuarioRepository.validAndMerge(usuario);
            Usuario usuario = usuarioRepository.findById(data.getId());

            System.out.println("data: " + data);
            System.out.println("usuario: " + usuario);

            if (usuario == null) {
                throw new ApplicationServiceException("usuario.naocadastrado",
                        Response.Status.BAD_REQUEST.getStatusCode());
            }

            // Atualizações dos campos
            if (!Objects.equals(usuario.getNome(), data.getNome())) {
                usuario.setNome(data.getNome());
            }
            if (!Objects.equals(usuario.getCpf(), data.getCpf())) {
                usuario.setCpf(data.getCpf());
            }
            if (!Objects.equals(usuario.getUsername(), data.getUsername())) {
                usuario.setUsername(data.getUsername());
            }

            if (data.getPassword() != null && !data.getPassword().isEmpty() &&
                    !Objects.equals(usuario.getPassword(), Usuario.passwordCripto(data.getPassword()))) {
                usuario.setPassword(Usuario.passwordCripto(data.getPassword()));
            }

            System.out.println(usuario);
            // Persiste as alterações
            usuarioRepository.persist(usuario);

        }catch (ConstraintViolationException hcve){
            throw new ApplicationServiceException("usuario" + hcve.getConstraintName());
        }catch (jakarta.validation.ConstraintViolationException cve) {
            throw new ApplicationServiceException("message.parametrosnaoinformados",
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    new MessageService(cve).getErrors());
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Erro na execucao do UsuarioService: alterar", e);
            throw new ApplicationServiceException("usuario.erro", new String[]{"alterar"});
        }
    }

    @Transactional
    public void excluir(Long idUsuario) throws ApplicationServiceException {
        try{
            if (idUsuario == null){
                List<MessageServiceError> listErros = new ArrayList<>(0);
                listErros.add(new MessageServiceError(MessageBundle.getMessage("usuario.id.naoinformado"), "idUsuario"));
                throw new ApplicationServiceException("message.parametrosnaoinformados",
                        Response.Status.BAD_REQUEST.getStatusCode(), listErros);
            }

            Usuario usuario = usuarioRepository.findById(idUsuario);

            // Verifica existencia do Usuário
            if (usuario == null){
                LOG.fine("Debug na execucao do UsuarioService: excluir = não existe - idUsuario="+idUsuario);
                throw new ApplicationServiceException("usuario.naocadastrado", Response.Status.BAD_REQUEST.getStatusCode());
            }

            if (!usuario.getOrdens().isEmpty()){
                LOG.fine("Debug na execução do UsuarioService: excluir = possui ordens - idUsuario=" + idUsuario);
                throw new ApplicationServiceException("usuario.possuiordens", Response.Status.BAD_REQUEST.getStatusCode());
            }

            usuarioRepository.deleteById(idUsuario);

        } catch (ApplicationServiceException ase) {
            throw ase;
        }catch (Exception e){
            LOG.log(Level.SEVERE,"Erro na execucao do UsuarioService: excluir", e);
            throw new ApplicationServiceException("usuario.erro", new String[] { "excluir" },
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
    }
}
