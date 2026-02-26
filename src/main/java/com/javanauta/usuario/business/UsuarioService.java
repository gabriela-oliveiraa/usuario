package com.javanauta.usuario.business;

import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.business.converter.UsuarioConverter;
import com.javanauta.usuario.infrastucture.entity.Endereco;
import com.javanauta.usuario.infrastucture.entity.Telefone;
import com.javanauta.usuario.infrastucture.entity.Usuario;
import com.javanauta.usuario.infrastucture.exceptions.ConflictException;
import com.javanauta.usuario.infrastucture.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infrastucture.repository.EnderecoRepository;
import com.javanauta.usuario.infrastucture.repository.TelefoneRepository;
import com.javanauta.usuario.infrastucture.repository.UsuarioRepository;
import com.javanauta.usuario.infrastucture.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository  enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email){
        try{
            boolean existe = verificarEmailExistente(email);
            if (existe){
                throw new ConflictException("Email já cadastrado: " + email);
            }
        } catch (ConflictException e){
            throw new ConflictException("Email já cadastrado " + e.getCause());
        }
    }

    public boolean verificarEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }


    public UsuarioDTO buscarUsuarioPorEmail(String email){
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email).orElseThrow(
                            () -> new ResourceNotFoundException("Email não encontrado " + email)));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }


    public UsuarioDTO atualizarDadosUsuario(String token, UsuarioDTO usuarioDTO){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado " + email));

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        Usuario usuario  = usuarioConverter.updateDeUsuario(usuarioDTO, usuarioEntity);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("Id do Endereço " + idEndereco + " não encontrado"));
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("Id do Telefone " + idTelefone + " não encontrado"));
        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, entity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
