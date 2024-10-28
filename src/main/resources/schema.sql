-- View para ordenar as empresas por nomeFantasia
CREATE OR REPLACE VIEW vw_empresas_nomefantasia AS
SELECT *
FROM empresa
ORDER BY nome_fantasia ASC;

-- View para listar os ramos distintos
CREATE OR REPLACE VIEW vw_empresas_ramos_distintos AS
SELECT DISTINCT ramo
FROM empresa;

-- View para buscar empresas com nomeFantasia iniciando com uma letra específica
CREATE OR REPLACE VIEW vw_empresas_por_nomefantasia AS
SELECT *
FROM empresa
WHERE LOWER(nome_fantasia) LIKE LOWER(CONCAT(:nomeFantasia, '%'))
ORDER BY nome_fantasia ASC;

-- View para buscar empresas com razaoSocial iniciando com uma letra específica
CREATE OR REPLACE VIEW vw_empresas_por_razaosocial AS
SELECT *
FROM empresa
WHERE LOWER(razao_social) LIKE LOWER(CONCAT(:razaoSocial, '%'))
ORDER BY razao_social ASC;

-- View para buscar empresas com ramo iniciando com uma letra específica
CREATE OR REPLACE VIEW vw_empresas_por_ramo AS
SELECT *
FROM empresa
WHERE LOWER(ramo) LIKE LOWER(CONCAT(:ramo, '%'))
ORDER BY nome_fantasia ASC;

-- View para buscar empresa por CNPJ
CREATE OR REPLACE VIEW vw_empresa_por_cnpj AS
SELECT *
FROM empresa
WHERE cnpj = :cnpj;
