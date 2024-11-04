package com.ambientese.grupo5.Services.EmpresaService;

import com.ambientese.grupo5.DTO.EmpresaRequest;
import com.ambientese.grupo5.Exception.ValidacaoException;
import com.ambientese.grupo5.Model.EnderecoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidacaoCamposObrigatoriosService {

    @Autowired
    private ValidacaoCNPJService validacaoCNPJService;

    @Autowired
    private ValidacaoTelefoneService validacaoTelefoneService;

    @Autowired
    private ValidacaoCEPService validacaoCEPService;

    @Autowired
    private ValidacaoEmailService validacaoEmailService;

    public void validarCamposObrigatorios(EmpresaRequest empresaRequest) {
        validarEmpresaRequest(empresaRequest);
        validarFormatos(empresaRequest);
    }

    private void validarEmpresaRequest(EmpresaRequest empresaRequest) {
        if (empresaRequest == null || empresaRequestCamposVazios(empresaRequest)) {
            throw new ValidacaoException("Os dados da empresa não podem estar em branco");
        }
        validarEndereco(empresaRequest.getEndereco());
    }

    private boolean empresaRequestCamposVazios(EmpresaRequest empresaRequest) {
        return empresaRequest.getNomeFantasia() == null || empresaRequest.getNomeFantasia().trim().isEmpty() ||
                empresaRequest.getNomeSolicitante() == null || empresaRequest.getNomeSolicitante().trim().isEmpty() ||
                empresaRequest.getTelefoneSolicitante() == null || empresaRequest.getTelefoneSolicitante().trim().isEmpty() ||
                empresaRequest.getRazaoSocial() == null || empresaRequest.getRazaoSocial().trim().isEmpty() ||
                empresaRequest.getCnpj() == null || empresaRequest.getCnpj().trim().isEmpty() ||
                empresaRequest.getTelefoneEmpresas() == null || empresaRequest.getTelefoneEmpresas().trim().isEmpty() ||
                empresaRequest.getRamo() == null || empresaRequest.getRamo().trim().isEmpty() ||
                empresaRequest.getPorteEmpresas() == null || empresaRequest.getPorteEmpresas().toString().trim().isEmpty() ||
                empresaRequest.getEmail() == null || empresaRequest.getEmail().trim().isEmpty();
    }

    private void validarEndereco(EnderecoModel endereco) {
        if (endereco == null || enderecoCamposVazios(endereco)) {
            throw new ValidacaoException("O endereço não pode estar em branco");
        }
    }

    private boolean enderecoCamposVazios(EnderecoModel endereco) {
        return endereco.getCep() == null || endereco.getCep().trim().isEmpty() ||
                endereco.getNumero() == null ||
                endereco.getLogradouro() == null || endereco.getLogradouro().trim().isEmpty() ||
                endereco.getCidade() == null || endereco.getCidade().trim().isEmpty() ||
                endereco.getBairro() == null || endereco.getBairro().trim().isEmpty() ||
                endereco.getUF() == null || endereco.getUF().trim().isEmpty();
    }

    private void validarFormatos(EmpresaRequest empresaRequest) {
        validarEmail(empresaRequest.getEmail());
        validarCEP(empresaRequest.getEndereco().getCep());
        validarTelefone(empresaRequest.getTelefoneEmpresas());
        validarTelefone(empresaRequest.getTelefoneSolicitante());
        validarCNPJ(empresaRequest.getCnpj());
    }

    private void validarEmail(String email) {
        if (!validacaoEmailService.isValidEmail(email)) {
            throw new ValidacaoException("O e-mail inserido não é válido");
        }
    }

    private void validarCEP(String cep) {
        if (!validacaoCEPService.isValidCep(cep)) {
            throw new ValidacaoException("O CEP inserido não é válido");
        }
    }

    private void validarTelefone(String telefone) {
        if (!validacaoTelefoneService.isValidTelefone(telefone)) {
            throw new ValidacaoException("O telefone inserido não é válido");
        }
    }

    private void validarCNPJ(String cnpj) {
        if (!validacaoCNPJService.isValidCnpj(cnpj)) {
            throw new ValidacaoException("O CNPJ inserido não é válido");
        }
    }
}