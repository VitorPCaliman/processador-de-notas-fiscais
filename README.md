# Processador de Notas Fiscais

Um serviço de processamento de notas fiscais utilizando Spring Batch. Este serviço lê um arquivo CSV contendo notas fiscais, valida os dados (CNPJ e valor) e separa as notas válidas e inválidas em arquivos distintos.

---

## 🛠️ Tecnologias Utilizadas

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)  ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)  ![Spring Batch](https://img.shields.io/badge/Spring%20Batch-6DB33F?style=for-the-badge&logo=spring&logoColor=white)  ![H2 Database](https://img.shields.io/badge/H2%20Database-4479A1?style=for-the-badge&logo=h2&logoColor=white)

---

## 🔧 Funcionalidades

- **Validação de Notas Fiscais**:
  - Verifica se o CNPJ é válido.
  - Verifica se o valor é maior que zero.
- **Processamento de Arquivos**:
  - Leitura de notas fiscais a partir de um arquivo CSV.
  - Separação de notas fiscais válidas e inválidas.
  - Geração de dois arquivos CSV de saída: um para válidas e outro para inválidas.

---

## 📁 Arquitetura e Estrutura de Pacotes

```plaintext
src/main/java/com/app/nfprocessor
├── batch
│   ├── NotaFiscalProcessor.java                 // Classe responsável por processar e validar notas fiscais
│   ├── NotaFiscalItemWriter.java                // Escritor para salvar os dados em arquivos CSV
├── config
│   ├── BatchConfig.java                         // Configuração do Spring Batch
├── listener
│   ├── JobCompletionListener.java               // Listener para controle de execução do Job                        
├── model
│   ├── NotaFiscal.java                          // Modelo de dados de Nota Fiscal
├── runner
│   ├── NotaFiscalCommandLineRunner.java         // Configuração para processar as notas fiscais
├── service
│   ├── NotaFiscalService.java                   // Service responsável por controlar as regras de negócio e validar o processamento das notas ficais
├── util
│   ├── CNPJUtils.java                           // Validação de CNPJ
├── Application.java                             // Classe principal da aplicação

src/main/java/com/app/resources
├── input
│   ├── notas_fiscais.java                       // arquivo de input para ser lido e processado contendo as notas fiscais
├── output                                       // Pasta onde serão gerados os arquivos notas_validas.csv e notas_invalidas.csv
```

---

## 🗂️ Modelo de Dados

A entidade **NotaFiscal** é definida da seguinte forma:

```java
@Data
public class NotaFiscal {

    private Long id;
    private String cnpj;
    private Double valor;

    public NotaFiscal(Long id, String cnpj, Double valor) {
        this.id = id;
        this.cnpj = cnpj;
        this.valor = valor;
    }

    public NotaFiscal() {
    }

  //Getters e Setters
}
```

---

## 🛠️ Configuração do Banco de Dados H2

O banco de dados H2 é configurado no arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

Para acessar o console do H2, vá até: `http://localhost:8080/h2-console`  
Use as credenciais acima.

---

## 🚀 Como Instalar e Rodar a Aplicação

1. **Clone o Repositório**:
   ```bash
   git clone https://github.com/VitorPCaliman/processador-de-notas-fiscais.git
   cd nfprocessor
   ```

2. **Configure o Ambiente**:
   Certifique-se de ter Java 11 ou superior e Maven instalados.

3. **Compile e Rode a Aplicação**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Adicione o Arquivo CSV de Entrada**:
   Coloque o arquivo `notas_fiscais.csv` no diretório `src/main/resources/input`.

---

## 📜 Documentação de Uso

A aplicação lê o arquivo `notas_fiscais.csv` e processa as notas fiscais, gerando dois arquivos na pasta `output`:

1. **CSV de Entrada (`notas_fiscais.csv`)**:
   ```csv
   id,cnpj,valor
   1,12345678000195,1500.50
   2,98765432000199,200.75
   3,11111111000100,-50.00
   4,22222222000188,500.00
   5,33333333000177,0.00
   6,44444444000166,250.25
   7,55555555000155,1800.99
   8,66666666000144,-100.00
   9,77777777000133,1200.00
   10,88888888000122,300.00
   11,8888888888881,300.00
   12,23,-568.05
   ```

2. **Arquivos Gerados**:
   - **notas_validas.csv**: Contém as notas fiscais válidas.
   - **notas_invalidas.csv**: Contém as notas fiscais inválidas.

---

## 🧪 Testando o Serviço

### Testes Unitários

```java
    @Test
    void deveValidarNotaFiscalComSucesso() throws Exception {
        NotaFiscal notaValida = processor.process(new NotaFiscal(1L, "12345678000195", 1000.0));
        assertNotNull(notaValida);
        assertEquals(1L, notaValida.getId());
        assertEquals("12345678000195", notaValida.getCnpj());
        assertEquals(1000.0, notaValida.getValor());
    }

    @Test
    void deveRetornarNuloParaCnpjInvalido() throws Exception {
        NotaFiscal notaCnpjInvalido = processor.process(new NotaFiscal(2L, "1234567800019", 1000.0));
        assertNull(notaCnpjInvalido);
    }

    @Test
    void deveRetornarNuloParaValorNegativo() throws Exception {
        NotaFiscal notaValorNegativo = processor.process(new NotaFiscal(3L, "12345678000195", -500.0));
        assertNull(notaValorNegativo);
    }

    @Test
    void deveRetornarNuloParaValorZero() throws Exception {
        NotaFiscal notaValorZero = processor.process(new NotaFiscal(4L, "12345678000195", 0.0));
        assertNull(notaValorZero);
    }
```
---

## 📂 Modelos de Teste de Notas Fiscais

Arquivo de entrada para testes (`notas_fiscais.csv`):

```csv
id,cnpj,valor
1,12345678000195,1500.50
2,98765432000199,200.75
3,11111111000100,-50.00
4,22222222000188,500.00
5,33333333000177,0.00
6,44444444000166,250.25
7,55555555000155,1800.99
8,66666666000144,-100.00
9,77777777000133,1200.00
10,88888888000122,300.00
```

---

## 📈 Melhorias Planejadas

- [ ] Implementar integração com banco de dados para persistir os resultados.
- [ ] Adicionar testes automatizados de integração.
- [ ] Adicionar suporte para outros formatos de arquivo (ex.: JSON, XML).
- [ ] Criar uma interface gráfica para upload de arquivos e exibição de resultados.
- [ ] Complementar validações de CNPJ para cobrir uma gama maior de variações.


🚀 Serviço simples desenvolvida como parte de um desafio técnico e para processar notas fiscais.

Caso tenha sugestões ou melhorias, contribua no repositório! 😊

