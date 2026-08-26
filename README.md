# Instant Furnace Mod

Um mod simples para Minecraft Java 1.17.1 (Fabric) que torna todas as fornalhas instantâneas!

## Funcionalidades

- **Fornalha Instantânea**: Todos os itens são cozidos imediatamente ao serem colocados na fornalha
- Funciona com Fornalha Normal, Fornalha de Fumaça e Alto-Forno
- Compatível com Fabric API

## Requisitos

- Minecraft 1.17.1
- Fabric Loader 0.15.7 ou superior
- Fabric API
- Java 16 ou superior

## Instalação

1. Baixe o arquivo `.jar` do mod da seção de Releases ou compile você mesmo
2. Coloque o arquivo na pasta `mods` do seu diretório do Minecraft
3. Inicie o jogo com Fabric Loader

## Como Compilar

### Usando GitHub Actions

O projeto já inclui um workflow do GitHub Actions que compila automaticamente o mod quando você faz push para as branches `main` ou `master`.

1. Faça push do código para o GitHub
2. Vá até a aba "Actions" no repositório
3. O build será executado automaticamente
4. Baixe o artefato `.jar` gerado

### Compilação Local

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/instant-furnace.git
cd instant-furnace

# Compile o mod
./gradlew build

# O arquivo .jar estará em build/libs/
```

## Estrutura do Projeto

```
instant-furnace/
├── .github/workflows/    # GitHub Actions workflows
├── gradle/               # Gradle wrapper
├── src/main/
│   ├── java/             # Código fonte Java
│   └── resources/        # Recursos do mod (fabric.mod.json, mixins, etc.)
├── build.gradle          # Configuração do Gradle
├── gradle.properties     # Propriedades do projeto
└── settings.gradle       # Configurações do projeto
```

## Licença

Este projeto está licenciado sob a MIT License - veja o arquivo [LICENSE](LICENSE) para detalhes.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.
